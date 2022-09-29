package com.cooksys.quiz_api.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

  private Long id;

  private String name;
  
//  private boolean deleted;  //Used for testing purposes

  public List<QuestionResponseDto> questions;

}
