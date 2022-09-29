package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-29T13:40:32-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public QuestionResponseDto entityToDto(Question entity) {
        if ( entity == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setId( entity.getId() );
        questionResponseDto.setText( entity.getText() );
        questionResponseDto.setAnswers( answerMapper.entitiesToDtos( entity.getAnswers() ) );

        return questionResponseDto;
    }

    @Override
    public List<QuestionResponseDto> entitiesToDtos(List<Question> entities) {
        if ( entities == null ) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>( entities.size() );
        for ( Question question : entities ) {
            list.add( entityToDto( question ) );
        }

        return list;
    }

    @Override
    public QuestionResponseDto entityToDto(Optional<Question> findById) {
        if ( findById == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        return questionResponseDto;
    }

    @Override
    public Question requestDtoToEntity(QuestionRequestDto questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setText( questionRequestDto.getText() );
        question.setAnswers( answerMapper.dtosToEntities( questionRequestDto.getAnswers() ) );

        return question;
    }
}
