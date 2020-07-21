package dev.minguinho.zeze.domain.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    /*
     * Redirects all routes to client except: '/', '/index.html', '/api', '/api/**'
     */
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return "forward:/";
    }
}
