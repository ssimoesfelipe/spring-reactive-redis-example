package com.example.redis.service;

import com.example.redis.repository.LinkRepository;
import com.example.redis.model.Link;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LinkService {

    private static final String BASE_URL = "http://localhost:8080/";

    private final LinkRepository repository;

    public Mono<String> shortenLink(String link) {
        String randomKey = RandomStringUtils.randomAlphabetic(6);
        return repository.save(new Link(link, randomKey))
                .map(result -> BASE_URL + result.getKey());
    }

    public Mono<Link> getOriginalLink(String key) {
        return repository.findByKey(key);
    }
}
