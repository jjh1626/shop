package com.example.shop.service;

import com.example.shop.model.Code;
import com.example.shop.repository.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {

    @Autowired CommonMapper commonMapper;

    public List<Code> findCodes(String codeGroup) {
        List<Code> codes = commonMapper.findCodes(codeGroup);
        return codes;
    }
}
