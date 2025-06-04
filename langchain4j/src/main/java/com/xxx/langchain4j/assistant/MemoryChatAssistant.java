package com.xxx.langchain4j.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: MemoryChatAssistant    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 1:56 下午   // 时间
 * @Version: 1.0     // 版本
 */
@AiService(
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

    @UserMessage("{{message}}")
    String chat(@V("message") String message);
}
