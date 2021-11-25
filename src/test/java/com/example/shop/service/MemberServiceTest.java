package com.example.shop.service;

import com.example.shop.model.Member;
import com.example.shop.repository.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    Logger log = (Logger) LoggerFactory.getLogger(MemberServiceTest.class);

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);
        log.info("member.getMemberId() : " + member.getMemberId());
        log.info("saveId : " + saveId);

        Assertions.assertThat(member.getMemberId()).isEqualTo(saveId);
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        try {
            memberService.join(member2);    //예외가 발생해야 한다.
        } catch (IllegalStateException e) {
            log.info("중복회원 예외발생");
        }
    }

}