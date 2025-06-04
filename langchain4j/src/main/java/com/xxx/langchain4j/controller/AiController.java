package com.xxx.langchain4j.controller;

import com.xxx.langchain4j.assistant.XXXAgent;
import com.xxx.langchain4j.bean.ChatForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: AiController    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 7:05 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController
{
    @Autowired
    private XXXAgent xxxAgent;

    @PostMapping("/xxx_agent")
    public String chat(@RequestBody ChatForm chatForm){
        return xxxAgent.chat(chatForm.getId(),chatForm.getMessage());
    }
}
