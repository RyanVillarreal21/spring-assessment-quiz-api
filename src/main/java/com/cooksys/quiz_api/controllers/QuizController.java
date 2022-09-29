package com.cooksys.quiz_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.UserResponseDto;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto createQuiz(@RequestBody QuizRequestDto quizRequestDto) {
	  return quizService.createQuiz(quizRequestDto);
  }
  
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserResponseDto deleteQuiz(@PathVariable Long id) {
	  return quizService.deleteQuiz(id);
  }

  @PatchMapping("/{id}/rename/{newName}")
  @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
  public UserResponseDto updateQuiz(@PathVariable Long id,@PathVariable String newName) {
	  return quizService.updateQuiz(id, newName);
  }
  
  @GetMapping("/{id}/random")
  @ResponseStatus(HttpStatus.OK)
  public QuestionResponseDto findRandomQuizQuestion(@PathVariable Long id) {
	  return quizService.findRandomQuizQuestion(id);
  }
  
  @PatchMapping("/{id}/add")
  @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
  public UserResponseDto addQuizQuestion(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequestDto) {
	  return quizService.addQuizQuestion(id, questionRequestDto);
  }
  
  @DeleteMapping("/{id}/delete/{questionID}")
  @ResponseStatus(HttpStatus.OK)
  public QuestionResponseDto deleteQuizQuestion(@PathVariable Long id, @PathVariable Long questionID) {
	  return quizService.deleteQuizQuestion(id, questionID);
  }
}
