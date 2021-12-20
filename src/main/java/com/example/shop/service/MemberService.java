package com.example.shop.service;

import com.example.shop.model.Member;
import com.example.shop.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    //회원가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberMapper.save(member);
        return member.getMemberId();
    }

    //중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberMapper.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 아이디로 조회
    public Member findOne(Long memberId) {
        return memberMapper.findOne(memberId);
    }

    //전체회원 조회
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    //회원 이름으로 조회
    public List<Member> findByName(String name) {
        return memberMapper.findByName(name);
    }

    //회원 수정
    public void modifyMember(Member member) {
        memberMapper.modify(member);
    }
}
