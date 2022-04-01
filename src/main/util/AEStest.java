package main.util;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static main.util.encodeUtil.*;


//import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Base64;
//import sun.misc.BASE64Encoder;

public class AEStest {
    public static void main(String[] args) throws Exception {
//        String a = encryptJsUserInfo("{\"openType\":\"order\"}","vpRZ1kmU","EbpU4WtY","DES","CBC","PKCS5Padding","Hex","无");
//        System.out.println(a);
        byte[] aa = {1};
        System.out.println(Base64.getEncoder().encode(aa));





        //System.out.println(decryptJsCode("L3osml/mtOdp/NdAzhpsqA==","f0ngtest","f0ngf0ng","DES","CBC","PKCS7Padding","Base64","无"));
        //System.out.println(encryptJsUserInfo("{\"id\":\"1''\"}","f0ngtest","f0ngf0ng","DES","CBC","PKCS7Padding","Base64","无"));
    }

    public static String encryptJsUserInfo(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode ,String keyivmode) throws Exception {
        try {
        byte[] data = null;
        byte[] aseKey ;
        byte[] ivData ;

        data = encryptedData.getBytes(StandardCharsets.UTF_8);
        if (keyivmode.equals("Base64")) {
            aseKey = Base64.getDecoder().decode(sessionKey);
            ivData = Base64.getDecoder().decode(iv);
        } else if(keyivmode.equals("Hex")) {
            aseKey = hexToByteArray(sessionKey);
            ivData = hexToByteArray(iv);
        }else {
            aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
            ivData = iv.getBytes(StandardCharsets.UTF_8);
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
        Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,ivParameterSpec );// 初始化
        byte[] result = cipher.doFinal(data);
        String final_result = "";
        if (sSrcmode.equals("Base64") || sSrcmode.equals("无")) {
            final_result = new sun.misc.BASE64Encoder().encode(result).replace("\n", "");
        }

        if (sSrcmode.equals("Hex"))
            final_result = bytesToHex(result);

        return final_result;
        } catch (Exception e) {
            e.printStackTrace();
            return "加密错误，请确认选项无误!"; }
    }

    public static String decryptJsCode(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode,String keyivmode) throws Exception {

        try {
        byte[] data ;
        byte[] aseKey = new byte[0];
        byte[] ivData = new byte[0];
        System.out.println(encryptedData);
        System.out.println(sSrcmode);
        if (sSrcmode.equals("Base64")) data = Base64.getDecoder().decode(encryptedData);
        else if (sSrcmode.equals("Hex")) {
            data = hexToByteArray(encryptedData);
        }
        else data = encryptedData.getBytes(StandardCharsets.UTF_8);

//            data = hex2byte(new String(data));

        if (keyivmode.equals("Base64")) {

            aseKey = Base64.getDecoder().decode(sessionKey);
            ivData = Base64.getDecoder().decode(iv);

        }else if(keyivmode.equals("Hex")) {

            aseKey = hexToByteArray(sessionKey);
            ivData = hexToByteArray(iv);
            for (byte a : data)
                System.out.println(a);

        }else {
            aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
            ivData = iv.getBytes(StandardCharsets.UTF_8);
        }
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
            Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);// 初始化

            byte[] result = cipher.doFinal(data);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "解密错误，请确认选项无误!";
        }

    }

    public static AlgorithmParameters generateIv(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
    public static byte[] hex2byte(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length % 2 == 1) {
            return null;
        }
        final int n = length / 2;
        final byte[] array = new byte[n];
        for (int i = 0; i != n; ++i) {
            final int n2 = i * 2;
            array[i] = (byte)Integer.parseInt(s.substring(n2, n2 + 2), 16);
        }
        return array;
    }

}
