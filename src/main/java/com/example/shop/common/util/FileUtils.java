package com.example.shop.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Component
@Slf4j
public class FileUtils {

    public List<Map<String, Object>> parseInsertFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception {

        String filePath = request.getSession().getServletContext().getRealPath("files") + File.separator;

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> listMap = null;

        String boardIdx = (String)map.get("idx");

        File file = new File(filePath);
        if (file.exists() == false) {
            file.mkdirs();
        }

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if (multipartFile.isEmpty() == false) {
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;

                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);     //파일 저장

                listMap = new HashMap<String,Object>();
                listMap.put("board_idx", boardIdx);
                listMap.put("original_file_name", originalFileName);
                listMap.put("stored_file_name", storedFileName);
                listMap.put("file_size", multipartFile.getSize());
                list.add(listMap);
            }
        }
        CommonUtils.printList(list);
        return list;
    }

    public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception {

        String filePath = request.getSession().getServletContext().getRealPath("/") + File.separator;

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> listMap = null;

        String boardIdx = (String) map.get("idx");
        String requestName = null;
        String idx = null;

        while (iterator.hasNext()) {
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if (multipartFile.isEmpty() == false) {
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.indexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;

                multipartFile.transferTo(new File(filePath + storedFileName));  //파일 저장

                listMap = new HashMap<>();
                listMap.put("is_new", "Y");
                listMap.put("board_idx", boardIdx);
                listMap.put("original_file_name", originalFileName);
                listMap.put("stored_file_name", storedFileName);
                listMap.put("file_size", multipartFile.getSize());
                list.add(listMap);
            } else {
                requestName = multipartFile.getName();
                log.info("multipartFile.getName():"+multipartFile.getName());
                idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
                log.info("idx:"+idx);
                if(map.containsKey(idx) == true && map.get(idx) != null){
                    listMap = new HashMap<String,Object>();
                    listMap.put("is_new", "N");
                    listMap.put("file_idx", map.get(idx));
                    list.add(listMap);
                }
            }
        }
        CommonUtils.printList(list);
        return list;
    }
}
