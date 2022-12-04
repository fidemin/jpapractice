package org.yunhongmin.shop.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yunhongmin.shop.service.CircuitService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final CircuitService circuitService;

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }


    @RequestMapping("/circuit")
    public String circuit(Model model) {
        circuitService.circuit();
        return "home";
    }


}
