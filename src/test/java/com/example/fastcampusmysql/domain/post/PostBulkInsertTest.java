package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    /**
     * easyRandom() 을 통해 posts 생성 후 한데 모아 bulkInsert() 에 한번에 쿼리를 날림
     * `%s` 는  ' 가 아닌   ~버튼의 `를 사용해야 함!
     */
    @DisplayName("1. [100만건 post 삽입]")
    @Test
    void bulkInsert(){
        var easyRandom = PostFixtureFactory.get(
                2L,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        var objectStopWatch = new StopWatch();
        objectStopWatch.start();

        var posts = IntStream.range(0, 10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        objectStopWatch.stop();
        System.out.println("객체 생성 시간 : " + objectStopWatch.getTotalTimeSeconds());


        var queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB Insert 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
