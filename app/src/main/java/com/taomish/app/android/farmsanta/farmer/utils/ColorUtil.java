package com.taomish.app.android.farmsanta.farmer.utils;

import java.util.Random;

public class ColorUtil {
    public static String randomHexCodeWithTransparency() {
        Random obj                              = new Random();
        int rand_num                            = obj.nextInt(0xffffff + 1);
        // format it as hexadecimal string and print
        String colorCode                        = String.format("%06x", rand_num);
        colorCode                               = "#66" + colorCode;
        System.out.println("skt-- " + colorCode);
        return colorCode;
    }

    public static String randomHexCode() {
        Random obj                              = new Random();
        int rand_num                            = obj.nextInt(0xffffff + 1);
        // format it as hexadecimal string and print
        String colorCode                        = String.format("%06x", rand_num);
        colorCode                               = "#" + colorCode;
        System.out.println("skt-- hex code = " + colorCode);
        return colorCode;
    }
}
