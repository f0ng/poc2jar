package main.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class encodeUtil {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // Unicode 编码测试
//        UnicodeEncode("\"title\":\"\\u4fa7\\u8fb9\\u680f-\\u8d85\\u503c\\u5546\\u54c1\\u63a8\\u8350"); //编码
//        System.out.println(UnicodeDecode("\"title\":\"\\u4fa7")); //解码

        // URL 编码测试
//        System.out.println(URLEncoder.encode("%23","utf-8")); // 编码
//        System.out.println(URLDecoder.decode("%E5%95%8A","utf-8")); // 解码

//        // Base64 编码测试
//        String base64encodedString = Base64.getEncoder().encodeToString("runoob?java8".getBytes(StandardCharsets.UTF_8));
//        System.out.println("Base64 编码字符串 (基本) :" + base64encodedString);
//        // 解码
//        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
//        System.out.println("原始字符串: " + new String(base64decodedBytes, StandardCharsets.UTF_8));

//        // hex(十六进制)编码
//        String a = str2HexStr("aasdf","utf-8");
//        System.out.println(a);
//        System.out.println(hexStr2Str(a,"utf-8"));

//        // HTML编码
//        System.out.println(HtmlEncode("啊"));
//        System.out.println(HtmlDecode("&#21834;"));

        // ASCII 编码
        String str = "hello";
        System.out.println(AsciiEncode(str));

        // ASCII 解码
        String a = "104 101 108 108 111";
        String[] t = a.split(" ");
        for (String ts:t)
        System.out.println(byteAsciiToChar(Integer.parseInt(ts)));


    }

    // ascii 编码
    public static String AsciiEncode(String str){
        String total = "";
        char[] chars =  str.toCharArray();
        for(char c:chars){
            int value = c;
            total = total  + value +  " ";
        }
        return total;
    }

    // ascii 解码
    public static char byteAsciiToChar(int ascii){
        char ch = (char)ascii;
        return ch;
    }

    // html编码
    // &#开头的编码换转成中文
    public static String HtmlDecode(String str) {
        String[] tmp = str.split(";&#|&#|;");
//        System.out.println(Arrays.toString(tmp));
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].matches("\\d{5}")) {
                sb.append((char) Integer.parseInt(tmp[i]));
                // System.out.println(sb.toString());
            } else {
                sb.append(tmp[i]);
            }
        }
        return sb.toString();
    }

    // 字符串转&#编码
    public static String HtmlEncode(String str) {
        char[] tmp = str.toCharArray();
//        System.out.println(Arrays.toString(tmp));
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < tmp.length; i++) {
            sb.append("&#").append((int) tmp[i]).append(";");
        }
        return sb.toString();
    }


    // hex(十六进制)编码
    public static String str2HexStr(String input, String charsetName) throws UnsupportedEncodingException {
        byte buf[] = input.getBytes(charsetName);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            //以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            //如果参数为负，那么无符号整数值为参数加上 2^32；否则等于该参数。将该值转换为十六进制（基数 16）的无前导 0 的 ASCII 数字字符串。
            //如果无符号数的大小值为零，则用一个零字符 '0' (’\u0030’) 表示它；否则，无符号数大小的表示形式中的第一个字符将不是零字符。
            //用以下字符作为十六进制数字【0123456789abcdef】。这些字符的范围是从【'\u0030' 到 '\u0039'】和从【'\u0061' 到 '\u0066'】。
            String hex = Integer.toHexString(buf[i] & 0xFF);//其实核心也就这一样代码
            if (hex.length() == 1) hex = '0' + hex;
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    // hex(十六进制)解码
    public static String hexStr2Str(String hexStr, String charsetName) throws UnsupportedEncodingException {
        if (hexStr.length() < 1) return null;
        byte[] hexbytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            hexbytes[i] = (byte) (high * 16 + low);
        }
        return new String(hexbytes, charsetName);
    }
    // hex(十六进制)解码
    public static String hexStr2Str(String hexStr) throws UnsupportedEncodingException {
        if (hexStr.length() < 1) return null;
        byte[] hexbytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            hexbytes[i] = (byte) (high * 16 + low);
        }
        return new String(hexbytes);
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }



    // Unicode编码
    //中文转字符串
    public static String UnicodeEncode(final String gbString) { //gbString = "测试"
        char[] utfBytes = gbString.toCharArray(); //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]); //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
//        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    //Unicode转中文
    public static String UnicodeDecode(String ascii) {
        List<String> ascii_s = new ArrayList<String>();
        String zhengz = "\\\\u[0-9,a-f,A-F]{4}";
        Pattern p = Pattern.compile(zhengz);
        Matcher m = p.matcher(ascii);
        while (m.find()) {
            ascii_s.add(m.group());
        }
        for (int i = 0, j = 2; i < ascii_s.size(); i++) {
            String code = ascii_s.get(i).substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            ascii = ascii.replace(ascii_s.get(i), String.valueOf(ch));
        }
        return ascii;
    }

    // 字节数组转hex
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //hex 转字节数组
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
//奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
//偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }
    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }
}
