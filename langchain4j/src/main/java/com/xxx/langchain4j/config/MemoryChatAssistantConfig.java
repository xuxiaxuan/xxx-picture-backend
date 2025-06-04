package com.xxx.langchain4j.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: MemoryChatAssistantConfig    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 1:57 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Configuration
public class MemoryChatAssistantConfig {

    @Bean
    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.withMaxMessages(10);
    }

}
