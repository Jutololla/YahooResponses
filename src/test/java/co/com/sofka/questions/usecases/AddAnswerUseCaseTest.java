package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class AddAnswerUseCaseTest {
    AnswerRepository answerRepository;
    GetUseCase getUseCase;
    AddAnswerUseCase addAnswerUseCase;
    QuestionRepository questionRepository;
//
//    @BeforeEach
//    public void setup(){
//    MapperUtils mapperUtils=new MapperUtils();
//    answerRepository = mock(AnswerRepository.class);
//    addAnswerUseCase = new AddAnswerUseCase(mapperUtils,getUseCase,answerRepository);
//
//    }
//
//    @Test
//    void validArgumentsTest(){
//        var answerDTO = new QuestionDTO("asdf","User", "pregunta", "Type", "Category");
//
//    }

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = mock(GetUseCase.class);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtils,getUseCase,answerRepository);
    }

    @Test
    void ValidArgumentsTest() {
        var questionDTO = new QuestionDTO();
        questionDTO.setId("Q-111");
        questionDTO.setUserId("xxxx-xxxx");
        questionDTO.setType("tech");
        questionDTO.setCategory("software");
        questionDTO.setQuestion("¿Que es java?");

        var question = new Question();
        question.setId("Q-111");
        question.setUserId("xxxx-xxxx");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");

        var answer = new Answer();
        answer.setId("A-111");
        answer.setUserId("xxxx-xxxx");
        answer.setQuestionId("Q-111");
        answer.setPosition(1);
        answer.setAnswer("es un lenguaje de programación");

        var answerDTO = new AnswerDTO();
        answerDTO.setId("A-111");
        answerDTO.setUserId("xxxx-xxxx");
        answerDTO.setQuestionId("Q-111");
        answerDTO.setPosition(1);
        answerDTO.setAnswer("es un lenguaje de programación");


        Mockito.when(getUseCase.apply(questionDTO.getId())).thenReturn(Mono.just(questionDTO));
        Mockito.when(answerRepository.save(any())).thenReturn(Mono.just(answer));
        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(questionDTO1 -> {
                    assert questionDTO1.getId().equals("Q-111");
                    assert questionDTO1.getUserId().equals("xxxx-xxxx");
                    assert questionDTO1.getCategory().equals("software");
                    assert questionDTO1.getQuestion().equals("¿Que es java?");
                    assert questionDTO1.getType().equals("tech");
                    return true;
                }).verifyComplete();

//        verify(getUseCase).apply(questionDTO.getId());
//        verify(answerRepository).save(refEq(answer));
    }

}