package com.example.shop.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class ExcelUtils {

    /**
     * 엑셀파일 생성
     */
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

    /**
     * 업로드한 엑셀 파일 읽기
     */
    public List<Map<String, Object>> readDataExcel(MultipartFile excelFile, String[] headerInfo) {

        List<Map<String, Object>> list = new ArrayList<>();
        XSSFWorkbook workbook = null;
        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            //워크북
            workbook = new XSSFWorkbook(opcPackage);
            //시트
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            
            Row row = null;
            Cell cell = null;
            while (iterator.hasNext()) {
                row = iterator.next();

                //셀(Cell) 처리
                int cells = row.getPhysicalNumberOfCells(); //셀의 개수
                log.info(">>>cells="+cells);
                Map<String, Object> data = new HashMap<>();
                for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                    cell = row.getCell(cellIndex);
                    if (cell == null) {
                        continue;
                    } else {
                        String value = getCellValueToString(cell);
                        data.put(headerInfo[cellIndex],value);
                    }
                }
                CommonUtils.printMap(data);
                list.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private String getCellValueToString(Cell cell) {
        String value = "";
        CellType ct = cell.getCellTypeEnum();
        if (ct != null) {
            switch (cell.getCellTypeEnum()) {
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case NUMERIC:
                value = cell.getNumericCellValue() + "";
                break;
            case STRING:
                value = cell.getStringCellValue() + "";
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case ERROR:
                value = cell.getErrorCellValue() + "";
                break;
            }
        }
        return value;
    }

    /**
     * 엑셀 파일 스타일 생성
     */
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

    /**
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
