package com.cooksys.quiz_api.mappers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.UserResponseDto;
import com.cooksys.quiz_api.entities.Quiz;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface QuizMapper {
	
  Quiz quizRequestDtoToEntity(QuizRequestDto quizRequestDto); 
	
  UserResponseDto entityToDto(Quiz entity);

  List<UserResponseDto> entitiesToDtos(List<Quiz> entities);

}
