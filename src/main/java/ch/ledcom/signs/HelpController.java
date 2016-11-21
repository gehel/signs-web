package ch.ledcom.signs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/help")
public class HelpController {

    @RequestMapping(method = GET)
    public String help() {
        return "help";
    }

}
