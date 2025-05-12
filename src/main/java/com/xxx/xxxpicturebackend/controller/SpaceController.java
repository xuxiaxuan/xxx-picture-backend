package com.xxx.xxxpicturebackend.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xxx.xxxpicturebackend.annotation.AuthCheck;
import com.xxx.xxxpicturebackend.common.BaseResponse;
import com.xxx.xxxpicturebackend.common.DeleteRequest;
import com.xxx.xxxpicturebackend.common.ResultUtils;
import com.xxx.xxxpicturebackend.constant.UserConstant;
import com.xxx.xxxpicturebackend.exception.BusinessException;
import com.xxx.xxxpicturebackend.exception.ErrorCode;
import com.xxx.xxxpicturebackend.exception.ThrowUtils;
import com.xxx.xxxpicturebackend.model.dto.picture.*;
import com.xxx.xxxpicturebackend.model.dto.space.SpaceUpdateRequest;
import com.xxx.xxxpicturebackend.model.entity.domain.Picture;
import com.xxx.xxxpicturebackend.model.entity.domain.Space;
import com.xxx.xxxpicturebackend.model.entity.domain.SpaceLevel;
import com.xxx.xxxpicturebackend.model.entity.domain.User;
import com.xxx.xxxpicturebackend.model.enums.PictureReviewStatusEnum;
import com.xxx.xxxpicturebackend.model.enums.SpaceLevelEnum;
import com.xxx.xxxpicturebackend.model.vo.PictureTagCategory;
import com.xxx.xxxpicturebackend.model.vo.PictureVO;
import com.xxx.xxxpicturebackend.service.PictureService;
import com.xxx.xxxpicturebackend.service.SpaceService;
import com.xxx.xxxpicturebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: PictureController    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 07/05/2025 6:42 PM   // 时间
 * @Version: 1.0     // 版本
 */
@Slf4j
@RequestMapping("/space")
@RestController
public class SpaceController {

    @Resource
    private SpaceService spaceService;

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断是否存在
        long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @GetMapping("/list/level")
    public BaseResponse<List<SpaceLevel>> listSpaceLevel() {
        List<SpaceLevel> spaceLevelList = Arrays.stream(SpaceLevelEnum.values()) // 获取所有枚举
                .map(spaceLevelEnum -> new SpaceLevel(
                        spaceLevelEnum.getValue(),
                        spaceLevelEnum.getText(),
                        spaceLevelEnum.getMaxCount(),
                        spaceLevelEnum.getMaxSize()))
                .collect(Collectors.toList());
        return ResultUtils.success(spaceLevelList);
    }

}
