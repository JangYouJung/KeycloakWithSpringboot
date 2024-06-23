package woongjin.bi.backend.api.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController()
@Slf4j
public class AuthController {
    @GetMapping("/main")
    public String mainPage(
            Principal principal, Model model
    ) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal; //jwt로 형변환
        log.info("toString : "+token.getTokenAttributes().toString());
        model.addAttribute("list", token.getTokenAttributes());
        return "main.html";
    }
    @GetMapping("/public")
    public String publicPage() {
        return "main.html";
    }
}
