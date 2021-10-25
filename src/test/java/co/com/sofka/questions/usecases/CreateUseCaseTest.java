package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreateUseCaseTest {
    QuestionRepository questionRepository;
    MapperUtils mapperUtils;
    CreateUseCase createUseCase;

    @BeforeEach
    public void setup(){
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils,questionRepository);
    }

    @Test
    void createQuestionSuccessUseCaseTest(){
        var questionDTO = new QuestionDTO("asdf","User", "pregunta", "Type", "Category");
        var question = new Question();
        BeanUtils.copyProperties(questionDTO,question);

        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));
        StepVerifier.create(createUseCase.apply(questionDTO)).expectNextMatches(
                id ->{
                    assert id.equals(questionDTO.getId());
                    return true;
                }).verifyComplete();
    }

}