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
@Schema(description = "账户发送登录验证码输入数据")
public class AccountSendLoginVerificationCodeInputDTO implements DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phoneNo;


}
