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
@Schema(description = "账户登录输入数据")
public class AccountLoginInputDTO implements DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phoneNo;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String verificationCode;


}
