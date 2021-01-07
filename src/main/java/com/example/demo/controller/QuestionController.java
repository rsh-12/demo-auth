package com.example.demo.controller;
/*
 * Date: 1/7/21
 * Time: 8:15 AM
 * */

import com.example.demo.domain.Pager;
import com.example.demo.entity.Question;
import com.example.demo.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class.getName());

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}/all")
    public ModelAndView allQuestions(@RequestParam("page") Optional<Integer> page,
                                     @PathVariable("id") Long id) {

        ModelAndView questionPage = new ModelAndView("question/questions");

        int evalPageSize = INITIAL_PAGE_SIZE;
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Question> questions =
                questionService.findAllPageable(id, PageRequest.of(evalPage, evalPageSize));
        Pager pager = new Pager(questions.getTotalPages(), questions.getNumber(), BUTTONS_TO_SHOW);

        questionPage.addObject("questions", questions);
        questionPage.addObject("selectedPageSize", evalPageSize);
        questionPage.addObject("themeId", id);
        questionPage.addObject("pager", pager);

        return questionPage;
    }
}