package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostLikeUseCase;
import com.example.fastcampusmysql.application.usecase.CreatePostUseCase;
import com.example.fastcampusmysql.application.usecase.GetTimelinePostUseCase;
import com.example.fastcampusmysql.domain.member.dto.PostDto;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostUseCase getTimelinePostUseCase;
    private final CreatePostUseCase createPostUseCase;
    private final CreatePostLikeUseCase createPostLikeUseCase;

    @PostMapping("")
    public Long create(PostCommand postCommand){
        return createPostUseCase.execute(postCommand);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest dailyPostCountRequest){
        return postReadService.getDailyPostCount(dailyPostCountRequest);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(
            @PathVariable Long memberId,
            Pageable pageRequest
            ) {
        return postReadService.getPosts(memberId, pageRequest);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostUseCase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like/v1")
    public void likePost(@PathVariable Long postId){
//        postWriteService.likePost(postId);
        postWriteService.likePostByOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId){
        createPostLikeUseCase.execute(postId, memberId);
    }

}
