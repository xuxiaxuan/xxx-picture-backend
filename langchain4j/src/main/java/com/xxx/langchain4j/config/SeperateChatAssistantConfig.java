package com.xxx.langchain4j.config;

import com.xxx.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: SeperateChatAssistantConfig    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 2:14 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Configuration
public class SeperateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

//    @Bean
//    public ChatMemoryProvider chatMemoryProvider(){
//        return memory->MessageWindowChatMemory.builder()
//                .id(memory)
////                .chatMemoryStore(new InMemoryChatMemoryStore())
//                .chatMemoryStore(mongoChatMemoryStore)
//                .maxMessages(10)
//                .build();
//    }
}
