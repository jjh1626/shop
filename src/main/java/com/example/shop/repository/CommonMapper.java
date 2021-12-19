package com.example.shop.repository;

import com.example.shop.model.Code;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {
    List<Code> findCodes(String codeGroup);
}
