package com.chen.blog.interfaces.http.dto.input;

import com.chen.blog.interfaces.http.dto.PageQueryInputDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @author cl
 * @version 1.0
 * @since 2021/6/30 16:18
 */
@Getter
@Setter
@ToString
@Schema(description = "文章分页查询输入数据")
@ParameterObject
public class ArticlePageQueryInputDTO extends PageQueryInputDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索关键字
     */
    @Schema(description = "搜索关键字")
    @Parameter(description = "搜索关键字")
    private String searchKeyword;
    /**
     * 账户ID
     */
    @Schema(description = "账户ID")
    @Parameter(description = "账户ID")
    private Long accountId;

    /**
     * 是否开启拼音搜索
     */
    @Schema(description = "是否开启拼音搜索")
    @Parameter(description = "是否开启拼音搜索")
    private Boolean isOpenPinyin;
    /**
     * 是否开启同义词搜索
     */
    @Schema(description = "是否开启同义词搜索")
    @Parameter(description = "是否开启同义词搜索")
    private Boolean isOpenSynonym;
    /**
     * 是否开启跨语言搜索
     */
    @Schema(description = "是否开启跨语言搜索")
    @Parameter(description = "是否开启跨语言搜索")
    private Boolean isOpenCrossLanguage;


}
