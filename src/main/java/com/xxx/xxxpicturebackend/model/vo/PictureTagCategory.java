package com.xxx.xxxpicturebackend.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: PictureTagCategory    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 07/05/2025 7:15 PM   // 时间
 * @Version: 1.0     // 版本
 */
@Data
public class PictureTagCategory {

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 分类列表
     */
    private List<String> categoryList;
}
