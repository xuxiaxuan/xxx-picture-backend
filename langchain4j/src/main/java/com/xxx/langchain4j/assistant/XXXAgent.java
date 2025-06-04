package com.xxx.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: XXXAgent    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 6:56 下午   // 时间
 * @Version: 1.0     // 版本
 */
@AiService(
        chatMemoryProvider = "chatMemoryProviderXXX",
        tools = "ImageSearchTools"
)
public interface XXXAgent {
    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String message);
}
