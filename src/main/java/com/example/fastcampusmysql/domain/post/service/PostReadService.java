package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCount>getDailyPostCount(DailyPostCountRequest dailyPostCountRequest){
        return postRepository.groupByCreatedDate(dailyPostCountRequest);
    }

    /**
     * offset 기반 pagination
     * @param memberId
     * @param pageRequest
     * @return
     */
    public Page<Post> getPosts(Long memberId, Pageable pageRequest){
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    /**
     * Cursor 기반 pagination
     * @param memberId
     * @param cursorRequest
     * @return
     */
    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest){
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()){
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else{
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

}
