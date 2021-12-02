package com.example.shop.common.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class CaptchaController {
    /**
     * 이미지 캡차 호출
     */
    @RequestMapping("/nCaptchaReq.do")
    @ResponseBody
    public Map<String, Object> nCaptchaReq(HttpServletRequest request, @RequestParam Map<String,String> commandMap) throws Exception {
        //디렉토리 구분자 확인 (윈도우 : \ , 리눅스 : /)
        //String dirPath = request.getSession().getServletContext().getRealPath("captchaFile") + File.separator;
        String dirPath = "C:"+File.separator+"captchaFile"+File.separator;
        log.info(">>>"+dirPath);

        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String key = NaverCaptcha.captchaNkey();
        String captchaImageName = NaverCaptcha.captchaImage(key, dirPath);

        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("captchaFileName", captchaImageName);

        //기존 이미지 삭제처리 (새로고치기 전 이미지 삭제)
        String fileName = (String)commandMap.get("fileName");
        log.info("fileName>>>"+fileName);
        if(fileName!=null) {
            if(!"".equals(fileName)) {
                NaverCaptcha.captchaFileRemove(fileName, dirPath);
            }
        }
        return map;
    }

    /**
     * 음성 캡차 호출
     */
    @RequestMapping("/sCaptchaReq.do")
    @ResponseBody
    public Map<String, Object> sCaptchaReq(HttpServletRequest request, @RequestParam Map<String,String> commandMap) throws Exception {
        //디렉토리 구분자 확인 (윈도우 : \ , 리눅스 (운영): /)
        //String dirPath = request.getSession().getServletContext().getRealPath("/").concat("resources") + File.separator + "captchaFile";
        String dirPath = "C:"+File.separator+"captchaFile"+File.separator;

        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String key = NaverCaptcha.captchaSkey();
        String captchaSoundName = NaverCaptcha.captchaSound(key, dirPath);
        log.info("key>>>"+key);
        log.info("captchaSoundName>>>"+captchaSoundName);

        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("captchaFileName", captchaSoundName);

        //기존 이미지 삭제처리 (새로고치기 전 이미지 삭제)
        String fileName = (String)commandMap.get("fileName");
        log.info("fileName>>>"+fileName);
        if(fileName!=null) {
            if(!"".equals(fileName)) {
                NaverCaptcha.captchaFileRemove(fileName, dirPath);
            }
        }
        return map;
    }
}
