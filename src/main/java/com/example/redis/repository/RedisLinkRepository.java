package com.example.redis.repository;

import com.example.redis.model.Link;
import com.example.redis.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RedisLinkRepository implements LinkRepository {

    private final ReactiveRedisOperations<String, String> operations;

    public RedisLinkRepository(@Qualifier("cache") ReactiveRedisOperations<String, String> operations) {
        this.operations = operations;
    }

    @Override
    public Mono<Link> save(Link link) {
        return operations.opsForValue()
                         .set(link.getKey(), link.getOriginalLink())
                         .map(__ -> link);
    }

    @Override
    public Mono<Link> findByKey(String key) {
        return operations.opsForValue()
                         .get(key)
                         .map(result -> new Link(result, key));
    }
}