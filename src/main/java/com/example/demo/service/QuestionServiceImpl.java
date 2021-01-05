package com.example.demo.service;
/*
 * Date: 12/28/20
 * Time: 9:24 AM
 * */

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    // DI
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question id not found: " + id));
    }
}