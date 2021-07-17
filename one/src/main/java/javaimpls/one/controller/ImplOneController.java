package javaimpls.one.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/api/v1/one")
public class ImplOneController {

    @GetMapping(path = "/", produces = TEXT_PLAIN_VALUE)
    public String getPrintText() {
        return "This is 1st implementation.";
    }
}
