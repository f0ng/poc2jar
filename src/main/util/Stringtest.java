package main.util;



import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import static main.util.encodeUtil.hexStr2Str;
import static main.util.encodeUtil.toByteArray;


public class Stringtest {
    //public static void main(String[] args) throws Base64DecodingException, UnsupportedEncodingException {
    //
    //    String hex_a = "f24c9cb90066fa35a7069f2a4436c3b7";
    //
    //    String str_a = "BAOiIp05FiRr1QD3zQBbUw==";
    //
    //    System.out.println(Arrays.toString(hexStr2Str(hex_a).getBytes(StandardCharsets.UTF_8)));
    //    System.out.println(Arrays.toString(toByteArray(hex_a)));
    //    System.out.println(Arrays.toString(hexToByteArray(hex_a)));
    //    System.out.println(Arrays.toString(Base64.decode(str_a)));
    //    System.out.println(bytesToHex(Base64.decode(str_a)));
    //
    //}

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
