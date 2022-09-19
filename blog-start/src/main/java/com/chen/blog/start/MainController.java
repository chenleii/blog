package com.chen.blog.start;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根路径
 * 放置与业务无关的逻辑，例如：健康检查等。
 *
 * @author cl
 */
@Controller
public class MainController {

    /**
     * 健康检查
     */
    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "success";
    }


}
