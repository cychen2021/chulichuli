package xyz.cychen.chulichuli.browser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.cychen.chulichuli.browser.model.VideoEntry;

import java.util.Optional;

@Controller
public class PageController {
    @GetMapping("/")
    String homepage() {
        return "index";
    }

    @GetMapping("/display/{id}")
    String displayPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "display";
    }
}
