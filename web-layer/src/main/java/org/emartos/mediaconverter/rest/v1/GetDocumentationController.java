package org.emartos.mediaconverter.rest.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/documentation")
public class GetDocumentationController {
    private static final Logger LOGGER = Logger.getLogger(GetDocumentationController.class.getName());

    @GetMapping
    String serveSwaggerDocumentation() {
        LOGGER.info("Serving documentation!");
        return "documentation";
    }
}
