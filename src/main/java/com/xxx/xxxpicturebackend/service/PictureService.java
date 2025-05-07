package com.xxx.xxxpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.xxxpicturebackend.model.dto.picture.PictureQueryRequest;
import com.xxx.xxxpicturebackend.model.dto.picture.PictureUploadRequest;
import com.xxx.xxxpicturebackend.model.entity.domain.Picture;
import com.xxx.xxxpicturebackend.model.entity.domain.User;
import com.xxx.xxxpicturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: PictureService    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 07/05/2025 6:42 PM   // 时间
 * @Version: 1.0     // 版本
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}
