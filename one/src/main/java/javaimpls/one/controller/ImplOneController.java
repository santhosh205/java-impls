package javaimpls.one.controller;

import javaimpls.one.models.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static javaimpls.one.utils.Constants.BASE_REQUEST_MAPPING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(BASE_REQUEST_MAPPING)
public class ImplOneController {

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public Greeting getGreeting() {
        return new Greeting(1L, "Hello World!");
    }
}
