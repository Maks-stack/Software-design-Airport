package com.emse.Airport_System;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServiceController {
    @RequestMapping("/servicemanager")
    public String index(Model model) {
        model.addAttribute("message", "test");
        return "serviceManager";
    }
}
