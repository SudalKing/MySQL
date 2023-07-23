package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.member.dto.PostDto;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostLikeRepository;
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
    private final PostLikeRepository postLikeRepository;

    public List<DailyPostCount>getDailyPostCount(DailyPostCountRequest dailyPostCountRequest){
        return postRepository.groupByCreatedDate(dailyPostCountRequest);
    }

    /**
     * offset 기반 pagination
     * @param memberId
     * @param pageRequest
     * @return
     */
    public Page<PostDto> getPosts(Long memberId, Pageable pageRequest){
        return postRepository.findAllByMemberId(memberId, pageRequest).map(this::toDto);
    }

    /**
     * 스케쥴러를 통해 몰아서 처리하는 구성 가능
     * @param post
     * @return
     */
    private PostDto toDto(Post post){
        return new PostDto(
          post.getId(),
          post.getContents(),
          post.getCreatedAt(),
          postLikeRepository.count(post.getId())// query가 계속 나가기때문에 읽기 성능을 희생하고 조회를 얻음
        );
    }

    public Post getPost(Long postId){
        return postRepository.findById(postId, false).orElseThrow();
    }

    /**
     * Cursor 기반 pagination
     * @param memberId
     * @param cursorRequest
     * @return
     */
    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest){
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest){
        var posts = findAllBy(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);

        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids){
        return postRepository.findAllByInId(ids);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()){
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        } else{
            return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        }
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()){
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        } else{
            return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
        }
    }


    private long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
