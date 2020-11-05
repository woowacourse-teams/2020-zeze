package dev.minguinho.zeze.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public String apiDocumentation() {
        return "index";
    }
}
