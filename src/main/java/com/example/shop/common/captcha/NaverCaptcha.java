package com.example.shop.common.captcha;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Slf4j
public class NaverCaptcha {
    private static final String clientId = "X1QrpzSbv3x5UYoP6X7J";	    //테스트용(내꺼) 클라이언트 아이디값
    private static final String clientSecret = "QlsygG86mD";			//테스트용(내꺼) 클라이언트 시크릿값

    public static String captchaNkey() {
        String result = null;
        try {
            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            //System.out.println(sb.toString());
            //System.out.println(sb.toString().substring(8, 8 + 16));
            result = sb.toString().substring(8, 8 + 16);

        } catch(Exception e) {
            log.info(e.toString());
        }

        return result;
    }

    public static String captchaImage(String key, String dirPath) {
        String result = null;
        try {
            String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                //랜덤한 이름으로 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(dirPath + tempname + ".jpg");
                f.createNewFile();
                //생성한 파일에 가져온 내용을 작성
                OutputStream output = new FileOutputStream(f);
                while((read = is.read(bytes)) != -1 ) {
                    output.write(bytes, 0, read);
                }
                result = tempname + ".jpg";
                output.close();
                is.close();

            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer sb = new StringBuffer();
                while((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                System.out.println(sb.toString());
            }

        } catch (Exception e) {
            log.info(e.toString());
        }
        return result;
    }

    public static String captchaNkeyResult(String key, String value) {
        String result = null;
        try {
            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            System.out.println("key >>> " + key + " value >>> " + value);
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            //System.out.println(sb.toString());
            //System.out.println(sb.toString().substring(10,14));
            result = sb.toString().substring(10,14);

        } catch(Exception e) {
            log.info(e.toString());
        }
        return result;
    }

    public static void captchaFileRemove(String fileName, String dirPath) {
        try {
            File file = new File(dirPath + fileName);
            log.info("captchaFileRemove >>>" + dirPath + fileName);
            if(file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    public static String captchaSkey() {
        String result = null;
        try {
            String code = "0"; // 키 발급시 0,  캡차 음성 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/skey?code=" + code;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            //System.out.println(sb.toString());
            //System.out.println(sb.toString().substring(8, 8 + 16));
            result = sb.toString().substring(8, 8 + 16);

        } catch(Exception e) {
            log.info(e.toString());
        }

        return result;
    }

    public static String captchaSound(String key, String dirPath) {
        String result = null;
        try {
            String apiURL = "https://openapi.naver.com/v1/captcha/scaptcha.bin?key=" + key;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                //랜덤한 이름으로 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(dirPath + tempname + ".wav");
                f.createNewFile();
                //생성한 파일에 가져온 내용을 작성
                OutputStream output = new FileOutputStream(f);
                while((read = is.read(bytes)) != -1 ) {
                    output.write(bytes, 0, read);
                }
                result = tempname + ".wav";
                output.close();
                is.close();

            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer sb = new StringBuffer();
                while((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                System.out.println(sb.toString());
            }

        } catch (Exception e) {
            log.info(e.toString());
        }
        return result;
    }

    public static String captchaSkeyResult(String key, String value) {
        String result = null;
        try {
            String code = "1"; // 키 발급시 0,  캡차 음성 비교시 1로 세팅
            System.out.println("key >>> " + key+ " value >>> " + value);
            String apiURL = "https://openapi.naver.com/v1/captcha/skey?code=" + code + "&key=" + key + "&value=" + value;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            //System.out.println(sb.toString());
            //System.out.println(sb.toString().substring(10,14));
            result = sb.toString().substring(10,14);

        } catch(Exception e) {
            log.info(e.toString());
        }
        return result;
    }
}
