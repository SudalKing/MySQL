package com.example.fastcampusmysql.domain.member.entity;


import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MemberTest {

    @DisplayName("1. [닉네임 제한 조건 테스트]")
    @Test
    public void validateNickname(){
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "pnupnupnupnu";

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> member.changeNickname(overMaxLengthName)
        );
    }

    @DisplayName("2. [Nickname 변경 테스트]")
    @Test
    public void testChangeName(){
        var member = MemberFixtureFactory.create();
        var toChangeName = "pnu";

        member.changeNickname(toChangeName);

        Assertions.assertEquals(toChangeName, member.getNickname());
    }
}
