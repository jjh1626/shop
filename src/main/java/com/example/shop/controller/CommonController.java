package com.example.shop.controller;

import com.example.shop.common.util.CommonUtils;
import com.example.shop.common.util.ExcelUtils;
import com.example.shop.model.Attach;
import com.example.shop.model.Member;
import com.example.shop.service.BoardService;
import com.example.shop.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping(value = "/common/downloadFile/{idx}")
    public void downloadFile(@PathVariable Long idx, HttpServletResponse response) throws Exception {
        Attach attach = boardService.findFileOne(idx);
        String path = "C:" + File.separator + "captchaFile" + File.separator;

        byte fileByte[] = FileUtils.readFileToByteArray(new File(path + attach.getStoredFileName()));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(attach.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping(value = "/common/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        //회원 목록
        List<Member> members = memberService.findAll();
        
        //엑셀 Body 값
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = null;
        List<Map<String,Object>> bodyList = new ArrayList<>();
        for (Member member : members) {
            map = objectMapper.convertValue(member, Map.class); //member 객체를 Map 으로 변경 (ObjectMapper 사용)
            bodyList.add(map);
        }
        CommonUtils.printList(bodyList);

        //엑셀 Header 값
        String[] arr = {"No","이름", "도시", "주소", "우편번호"};
        List<String> headerList = Arrays.asList(arr);   //배열(array) to List

        //엑셀 생성
        ExcelUtils excelUtils = new ExcelUtils();
        XSSFWorkbook workbook = excelUtils.createExcel(headerList, bodyList, "member");

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=\""+ExcelUtils.getFileName(request)+"\";");

        // 엑셀 출력
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(workbook != null) { workbook.close(); }
                if(outputStream != null) { outputStream.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
