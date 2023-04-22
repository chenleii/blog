package com.chen.blog.interfaces.http.dto.input;

import com.chen.blog.core.notification.domain.model.NotificationStatus;
import com.chen.blog.interfaces.http.dto.PageQueryInputDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.core.annotations.ParameterObject;

import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 23:55
 */
@Getter
@Setter
@ToString
@Schema(description = "通知分页查询输入数据")
@ParameterObject
public class NotificationPageQueryInputDTO extends PageQueryInputDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @Parameter(description = "状态")
    private Set<NotificationStatus> statuses;


}
