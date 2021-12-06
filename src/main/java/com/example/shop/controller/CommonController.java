package com.example.shop.controller;

import com.example.shop.model.Attach;
import com.example.shop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final BoardService boardService;

    @GetMapping(value = "/common/downloadFile/{idx}")
    public void downloadFile(@PathVariable Long idx, HttpServletResponse response) throws Exception {
        Attach attach = boardService.findFileOne(idx);
        String path = "C:"+File.separator+"captchaFile"+File.separator;

        byte fileByte[] = FileUtils.readFileToByteArray(new File(path + attach.getStoredFileName()));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(attach.getOriginalFileName(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
