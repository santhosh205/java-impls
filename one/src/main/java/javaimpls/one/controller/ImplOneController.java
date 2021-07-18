package javaimpls.one.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static javaimpls.one.utils.Constants.*;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping(BASE_REQUEST_MAPPING)
public class ImplOneController {

    @GetMapping(path = JUST_SLASH, produces = TEXT_PLAIN_VALUE)
    public String getPrintText() {
        return IMPL_ONE_STATEMENT;
    }
}
