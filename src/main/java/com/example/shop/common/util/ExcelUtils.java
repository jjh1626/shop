package com.example.shop.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExcelUtils {

    public XSSFWorkbook createExcel(List<String> HeaderList, List<Map<String,Object>> bodyList, String sheetName){
        //워크북
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = null;
        Cell cell = null;
        int rowIndex = 0;
        int cellIndex = 0;

        try {
            CellStyle headStyle = getCellStyle(workbook.createCellStyle(), true);
            CellStyle bodyStyle = getCellStyle(workbook.createCellStyle(), false);

            //헤더 생성
            row = sheet.createRow(rowIndex++);
            for (String str : HeaderList) {
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(headStyle);
                cell.setCellValue(str);
            }

            //바디 생성
            cellIndex = 0;
            for (Map<String, Object> map : bodyList) {
                row = sheet.createRow(rowIndex++);

                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                Map.Entry<String,Object> entry = null;
                while (iterator.hasNext()) {
                    entry = iterator.next();
                    String value = String.valueOf(entry.getValue());
                    cell = row.createCell(cellIndex++);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(value);
                }
                cellIndex = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private CellStyle getCellStyle(CellStyle cellStyle, Boolean isHeader) {
        
        //배경색
        if(isHeader){
            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        
        //정렬
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //테두리
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        return cellStyle;
    }

    /*
     * 브라우저에 따른 파일명 인코딩
     */
    public static String getFileName(HttpServletRequest request){
        // 파일명 설정
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = simpleDateFormat.format(date);
        String fileName = "회원목록" + "_" + time + ".xlsx";

        // 브라우저 얻기
        String browser = request.getHeader("User-Agent");

        try {
            // 브라우저에 따른 파일명 인코딩 설정
            if (browser.indexOf("MSIE") > -1) {
                fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            } else if (browser.indexOf("Trident") > -1) {       // IE11
                fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            } else if (browser.indexOf("Firefox") > -1) {
                fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
            } else if (browser.indexOf("Opera") > -1) {
                fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
            } else if (browser.indexOf("Chrome") > -1) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < fileName.length(); i++) {
                    char c = fileName.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }
                fileName = sb.toString();
            } else if (browser.indexOf("Safari") > -1){
                fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
            } else {
                fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return fileName;
    }

}
