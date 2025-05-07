package com.xxx.xxxpicturebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.xxxpicturebackend.model.entity.domain.Picture;
import com.xxx.xxxpicturebackend.model.entity.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: PictureMapper    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 07/05/2025 6:52 PM   // 时间
 * @Version: 1.0     // 版本
 */
@Mapper
public interface PictureMapper extends BaseMapper<Picture> {
}
