package com.xxx.xxxpicturebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 许夏轩
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
