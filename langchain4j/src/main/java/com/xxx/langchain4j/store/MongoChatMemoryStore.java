package com.xxx.langchain4j.store;

import com.xxx.langchain4j.entity.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: MongoChatMemoryStore    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 5:14 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query=new Query(criteria);
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        if(chatMessages==null){
            return new LinkedList<>();
        }
        String contentToJson = chatMessages.getContent();
        List<ChatMessage> chatMessageList = ChatMessageDeserializer.messagesFromJson(contentToJson);
        return chatMessageList;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query=new Query(criteria);
        Update update = new Update();
        String messages = ChatMessageSerializer.messagesToJson(list);
        update.set("content",messages);
        mongoTemplate.upsert(query,update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query=new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}
