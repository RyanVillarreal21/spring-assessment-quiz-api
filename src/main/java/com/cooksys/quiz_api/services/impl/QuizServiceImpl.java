package com.cooksys.quiz_api.services.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.UserResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final AnswerRepository answerRepository;
	
	private Quiz getQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findByIdAndDeletedFalse(id);
		if(optionalQuiz.isEmpty()) {
			throw new NotFoundException("No quiz found with the id: " + id);
		}
		
		List<Question> questions = optionalQuiz.get().getQuestions();   
		Quiz newQuiz = optionalQuiz.get();
//		filterList(questions, Question::isDeleted);	
		questions.removeIf(Question::isDeleted);
		newQuiz.setQuestions(questions);
		
		return newQuiz;
	}
	
	private Question getQuestion(Long id) {
		Optional<Question> optionalQuestion = questionRepository.findByIdAndDeletedFalse(id);
		if(optionalQuestion.isEmpty()) {
			throw new NotFoundException("No question found with the id: " + id);
		}
		return optionalQuestion.get();
	}
	
	public static <T> void filterList(List<T> list, Predicate<T> condition) {
        Iterator<T> itr = list.iterator();
        while (itr.hasNext()) {
            T t = itr.next();
            if (condition.test(t)) {
                itr.remove();
            }
        }
    }
	
	@Override
	public List<UserResponseDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAllByDeletedFalse();

		for(Quiz quiz : allQuizzes) {
			List<Question> questions = quiz.getQuestions();
///			filterList(questions, Question::isDeleted);	
			questions.removeIf(Question::isDeleted);

			quiz.setQuestions(questions);
		}
		
	    return quizMapper.entitiesToDtos(quizRepository.findAllByDeletedFalse());
	}
	
	@Override
	public UserResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		Quiz quizToSave = quizMapper.quizRequestDtoToEntity(quizRequestDto);
		//Question question = questionMapper.requestDtoToEntity(questionRequestDto);
		
		quizRepository.saveAndFlush(quizToSave);
		
		for(Question question : quizToSave.getQuestions()) {
			question.setQuiz(quizToSave); 
			questionRepository.saveAndFlush(question);
			
			for(Answer answer : question.getAnswers()) {
				answer.setQuestion(question); 
				answerRepository.saveAndFlush(answer);
			}
		}
		quizToSave.setDeleted(false);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToSave));
	}

	@Override
	public UserResponseDto deleteQuiz(Long id){
		Quiz quizToDelete = getQuiz(id);
		quizToDelete.setDeleted(true);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToDelete));
	}

	@Override
	public UserResponseDto updateQuiz(Long id, String newName) {
		Quiz quizToUpdate  = getQuiz(id);
		quizToUpdate.setName(newName);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));
	}

	@Override
	public QuestionResponseDto findRandomQuizQuestion(Long id) {
		Quiz quizToFind = getQuiz(id);
		List<Question>shuffleQuestions = quizToFind.getQuestions();
		Collections.shuffle(shuffleQuestions);
		Question foundQuestion = shuffleQuestions.get(0);
		
		return questionMapper.entityToDto(foundQuestion);
	}

	@Override
	public UserResponseDto addQuizQuestion(Long id, QuestionRequestDto questionRequestDto) {
		Quiz quizToFind = getQuiz(id);
		Question question = questionMapper.requestDtoToEntity(questionRequestDto);
		
		question.setQuiz(quizToFind);
		questionRepository.saveAndFlush(question);
		question.setAnswers(question.getAnswers());
		for(Answer answer : question.getAnswers()) {
			answer.setQuestion(question); 
			answerRepository.saveAndFlush(answer);
		}
		quizToFind.getQuestions().add(question);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToFind));
	}

	@Override
	public QuestionResponseDto deleteQuizQuestion(Long id, Long questionId) {
		Quiz quizToFind = getQuiz(id);
		Question questionToDelete = getQuestion(questionId);
		List<Question> newQuestions = quizToFind.getQuestions();
		if(!newQuestions.contains(questionToDelete)) {
			throw new NotFoundException("Quiz id: " + id + " does not contain Question id: " + questionId);
		}
		if(newQuestions.contains(questionToDelete)) {
			newQuestions.remove(questionToDelete);
			questionToDelete.setDeleted(true);
		}
		
		quizToFind.setQuestions(newQuestions);
		quizMapper.entityToDto(quizRepository.saveAndFlush(quizToFind));
		return questionMapper.entityToDto(questionToDelete);
	}

}
