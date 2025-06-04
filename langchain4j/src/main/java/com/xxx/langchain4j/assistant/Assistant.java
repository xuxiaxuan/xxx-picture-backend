package com.xxx.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: Assistant    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 03/06/2025 10:59 下午   // 时间
 * @Version: 1.0     // 版本
 */
@AiService
public interface Assistant {
    String chat(String userMessage);
}
