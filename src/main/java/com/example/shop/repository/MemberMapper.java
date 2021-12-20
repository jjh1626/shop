package com.example.shop.repository;

import com.example.shop.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Member findOne(Long memberId);
    List<Member> findAll();
    List<Member> findByName(String name);
    void modify(Member member);
}
