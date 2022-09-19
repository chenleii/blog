package com.chen.blog.interfaces.http.dto.output;

import com.chen.blog.interfaces.http.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 15:29
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Schema(description = "文章输出数据")
public class ArticleOutputDTO implements DTO {

    private static final long serialVersionUID = 1L;



}
