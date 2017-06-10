package com.vladkanash.controller;

import com.vladkanash.dto.ChartPoint;
import com.vladkanash.service.VisualisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vladk on 10.06.2017.
 */

@Controller
public class VisualisationController {

    final VisualisationService visualisationService;

    @Autowired
    public VisualisationController(VisualisationService visualisationService) {
        this.visualisationService = visualisationService;
    }

    @RequestMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("name", "Vlad");
        return "/index";
    }

    @RequestMapping(value = "/learningCurve", method = RequestMethod.GET)
    public @ResponseBody List<ChartPoint> getCostValues() {
        return visualisationService.getCostValidation();
    }

    @RequestMapping(value = "/train", method = RequestMethod.POST)
    public String train(@RequestParam double alpha, @RequestParam int count) {
        visualisationService.train(alpha, count);
        return "/index";
    }
}
