package com.example.redis;

import com.example.redis.model.Link;
import com.example.redis.repository.LinkRepository;
import com.example.redis.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    @Test
    public void shortensLink() {
        Link link = new Link("http://localhost:8080/", "key");

        when(linkRepository.save(any(Link.class))).thenReturn(Mono.just(link));

        StepVerifier.create(linkService.shortenLink("http://spring.io"))
                .expectNextMatches(result -> result != null && result.startsWith("http://localhost:8080/"))
                .expectComplete()
                .verify();
    }

}