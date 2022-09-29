package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.UserResponseDto;

public interface QuizService {

  List<UserResponseDto> getAllQuizzes();

  UserResponseDto createQuiz(QuizRequestDto quizRequestDto);

  UserResponseDto deleteQuiz(Long id);

  UserResponseDto updateQuiz(Long id, String newName);

  QuestionResponseDto findRandomQuizQuestion(Long id);
  
  UserResponseDto addQuizQuestion(Long id, QuestionRequestDto questionRequestDto);

  QuestionResponseDto deleteQuizQuestion(Long id, Long questionId);

}
