package org.emartos.mediaconverter.rest.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documentation")
public class GetDocumentationController {

    @GetMapping
    String serveSwaggerDocumentation() {
        return "redirect:/swagger-ui.html";
    }
}
