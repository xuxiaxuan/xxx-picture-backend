package com.xxx.langchain4j.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: ChatForm    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 7:04 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatForm {
    private Long id;
    private String message;
}
