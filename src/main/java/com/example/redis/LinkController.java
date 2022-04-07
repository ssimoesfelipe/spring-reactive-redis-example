package com.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/link")
    public Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequest request) {
        return linkService.shortenLink(request.getLink())
                .map(CreateLinkResponse::new)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/{key}")
    public Mono<Link> getLink(@PathVariable String key) {
        return linkService.getOriginalLink(key)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
