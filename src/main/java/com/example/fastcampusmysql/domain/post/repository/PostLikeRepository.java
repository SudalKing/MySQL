package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class PostLikeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final static private String TABLE = "PostLike";

    private static final RowMapper<PostLike> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> PostLike
            .builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Long count(Long postId){
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE postId = :postId
                """, TABLE);
        var params = new MapSqlParameterSource()
                .addValue("postId", postId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public PostLike save(PostLike postLike){
        if (postLike.getId() == null) {
            return insert(postLike);
        }

        throw new UnsupportedOperationException("Timeline은 갱신을 지원하지 않습니다.");
    }



    private PostLike insert(PostLike postLike) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE) // table이 먼저 생성되어 있어야 함
                .usingGeneratedKeyColumns("id");
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(postLike);

        var id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return PostLike.builder()
                .id(id)
                .memberId(postLike.getMemberId())
                .postId(postLike.getPostId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }

}
