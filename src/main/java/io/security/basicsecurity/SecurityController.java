package io.security.basicsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {


    @GetMapping(path = "/")
    public String index(){
        return "hello";
    }

    @GetMapping(path = "/loginPage")
    public String loginPage(){
        return "loginPage";
    }
}
