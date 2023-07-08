package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 1. UseCase: domain service의 흐름만을 제어
 * 2. 비즈니스 로직이 없어야함
 */
@RequiredArgsConstructor
@Service
public class CreateFollowMemberUseCase {

    private final MemberReadService memberReadService; // member에 대해 읽기 권한만을 가짐
    private final FollowWriteService followWriteService;

    public void execute(Long fromMemberId, Long toMemberId){
        /*
            1. 입력받은 memberId로 회원조회
            2. FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }

}
