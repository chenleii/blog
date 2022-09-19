package com.chen.blog.interfaces.http.dto.input;

import com.chen.blog.interfaces.http.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:18
 */
@Getter
@Setter
@ToString
@Schema(description = "账户更新输入数据")
public class AccountUpdateInputDTO implements DTO {

    private static final long serialVersionUID = 1L;


    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String introduction;

}
