package com.xxx.xxxpicturebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.xxxpicturebackend.model.dto.space.SpaceAddRequest;
import com.xxx.xxxpicturebackend.model.entity.domain.Space;
import com.xxx.xxxpicturebackend.model.entity.domain.User;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: SpaceService    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 12/05/2025 10:36 PM   // 时间
 * @Version: 1.0     // 版本
 */
public interface SpaceService extends IService<Space> {
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    void validSpace(Space space, boolean add);

    void fillSpaceBySpaceLevel(Space space);
}
