package me.adorerose.worms.util;

import java.util.Random;

public class TextUtils {
    private static final Random random = new Random(System.currentTimeMillis());

    public static String coloredFormat(String str, Object... args) {
        String colourPalette = "2345679abcde";
        final String colour = "§" + colourPalette.toCharArray()[Math.abs(random.nextInt()) % colourPalette.length()];
        int afterArgIdx = 0, argIdx = 0;
        StringBuilder formatted = new StringBuilder(32);
        byte[] byteStr = str.getBytes();

        for (int i = 0; i < byteStr.length; i++) {
            if (byteStr[i] == '%' && i + 1 < byteStr.length && byteStr[i + 1] == 's') {
                formatted.append(str, afterArgIdx, i).append(colour).append(args[argIdx]).append("§f");
                i += 2;
                argIdx++;
                afterArgIdx = i;
            }
        }
        if (afterArgIdx < str.length()) formatted.append(str, afterArgIdx, str.length());
        return formatted.toString();
    }
}
