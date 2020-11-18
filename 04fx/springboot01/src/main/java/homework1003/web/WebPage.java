package homework1003.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebPage {

    @RequestMapping("/test")
    @ResponseBody
    public String hello() {
        return "hello SpringBoot";
    }

    @RequestMapping("/myStarter")
    @ResponseBody
    public String xiyuan() {
        
        return "";
    }
}
