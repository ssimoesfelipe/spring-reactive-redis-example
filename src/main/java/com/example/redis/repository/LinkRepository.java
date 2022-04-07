package com.example.redis.repository;

import com.example.redis.model.Link;
import reactor.core.publisher.Mono;

public interface LinkRepository {

    Mono<Link> save(Link link);

    Mono<Link> findByKey(String key);
}