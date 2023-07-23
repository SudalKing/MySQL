package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;

    private final Long memberId;

    private final String contents;

    private final LocalDate createdDate;

    private Long likeCount;

    // 낙관적 락을 위한 version 필드
    private Long version;

    private final LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, LocalDate createdDate, Long likeCount, Long version, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;

        // 현업에서는 migration 전략을 사용해 데이터를 조금씩 조금씩 디폴트 값으로 넣어주거나
        // 조회 시점에 0을 넣어주는 방식을 사용해 24시간 서비스를 유지
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.version = version == null ? 0 : version;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount(){
        likeCount += 1;
    }

}
