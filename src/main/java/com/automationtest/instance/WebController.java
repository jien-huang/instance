package com.automationtest.instance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

  @GetMapping("/home")
  public String home( Model model) {
    model.addAttribute("config", Config.getInstance().getAll());
    return "home";
  }

  @GetMapping("/results")
  public String results( Model model) {
    return "results";
  }

  @GetMapping("/scripts")
  public String scripts( Model model) {
    return "scripts";
  }

}
