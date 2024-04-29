package com.nowcoder.community;

import java.io.IOException;

/**
 * @Author: yyp
 * @Date: 2024/4/29 - 04 - 29 - 8:28
 * @Description: com.nowcoder.community
 * @version: 1.0
 */
public class WkTests {

    public static void main(String[] args) {
        String cmd = "E:/JAVA/wkhtmltopdf/startUp/bin/wkhtmltoimage http://testfire.net E:/JAVA/work/community/data/wk-images/testfire.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("OK.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
