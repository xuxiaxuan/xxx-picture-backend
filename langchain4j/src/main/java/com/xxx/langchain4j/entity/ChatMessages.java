package com.xxx.langchain4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: ChatMessages    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 4:59 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class ChatMessages {
    @Id
    private ObjectId messageId;
    private String memoryId;
    private String content;
}
