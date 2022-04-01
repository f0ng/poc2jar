package main.finalshelltest;


import java.io.IOException;
import java.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class seeyonGetpass {
    private static String SrcData = "dGZzd2ZzcGIzLzYyOTQyNQ==";

    public static void log(String s) {
        System.out.println("------>" + s);
    }

    public static void main(String[] args) throws Exception {

        log("jdk base64Decode2:" + jdkBas64Decode2(SrcData));
        String asciis = stringToAscii(jdkBas64Decode2(SrcData));
        log(asciiToString(asciis));
    }
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]-1).append(",");
            }
            else {
                sbu.append((int)chars[i]-1);
            }
        }
        return sbu.toString();
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    // jdk base64加密 jdk util
    private static String jdkBas64Encode2(String src) {
        return Base64.getEncoder().encodeToString(src.getBytes());
    }

    // jdk base64揭秘 jdk util
    public static String jdkBas64Decode2(String encodeData) {
        return new String(Base64.getDecoder().decode(encodeData));
    }
}
