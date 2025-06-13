package com.in28minutes.springboot.firstrestapi.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
//Rest contorllor is a combinatuion of controllor and response body
public class HelloWorldResource {

    @RequestMapping("/hello-world")
    public String Helloworld(){
        return "Hello World";
    }

    @RequestMapping("/hello-world-bean")
    public hellowworldbean HelloworldBean(){
        return new hellowworldbean("Hello World bean");
    }

//    Path params or path variables
//    Rest APIs use very complex urls, user/ona/todos == path variable

    @RequestMapping("/hello-world-path-param/{name}")
    public hellowworldbean helloworldpathparam(@PathVariable String name){
        return new hellowworldbean("Hello " + name);
    }

    @RequestMapping("/hello-world-path-param/{name}/message/{message}")
    public hellowworldbean helloworldmultiplepathparam(@PathVariable String name, @PathVariable String message){
        return new hellowworldbean("Hello " + name + "," + message);
    }
}
