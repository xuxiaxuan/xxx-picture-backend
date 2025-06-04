package com.xxx.xxxpicturebackend.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: LLMTest    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 03/06/2025 10:05 下午   // 时间
 * @Version: 1.0     // 版本
 */
@SpringBootTest
public class LLMTest {

    private static String apiKey="sk-wienjmvcnerfkhniooytrmpptajwccphdcbeabzwybjbeunu";

    @Test
    public void test(){
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://api.siliconflow.cn/v1")
                .apiKey(apiKey)
                .modelName("Qwen/QwQ-32B")
                .build();

        String answer = model.chat("你好啊");
        System.out.println(answer);
    }

}
