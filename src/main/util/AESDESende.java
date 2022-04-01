package main.util;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import static main.util.encodeUtil.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import sun.misc.BASE64Encoder;
import java.util.Base64;

public class AESDESende {
    public static void main(String[] args) throws Exception {

        System.out.println(encrypt("1234","wA431mUEPYbLH6en0PbKhA==","1234567812345678","AES","GCM","PKCS5Padding" ,"无" ));
//        System.out.println(decrypt("VjgFyEZFfOazFj/eW4dupitYXcNO4WPqW4FZq9rzLg17oOKbLMVJbbOMoGP9hzQvaJhWSxWpmIUQ9fmECn/wFDPl5m8favc7KV1qqTO3MYu0BZ2kiht+crFzhBhPdIoUodGTGtfIcgc1D9v6NxaIn7Cx855rfmegSpnYTpOMAs3Lj4NKrtL8cKmg+Uj0dl8pihpv6hvJCNkMeIIN0RaZNA==","wA431mUEPYbLH6en0PbKhA==","oN2nWtIVIqv1eMO4WjPLnQ==","AES","CBC","PKCS5Padding","无"));
        System.out.println();
    }

    public static String encrypt(String sSrc, String sKey, String iVparam,String encodemode, String ivmode , String paddingmode ,String sSrcmode) throws Exception {
//        try {
            Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, encodemode);

            if (ivmode.equals("ECB") || ivmode.equals("GCM")) { // 如果为ECB模式，不进行偏移量加载
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            } else {
                IvParameterSpec iv = new IvParameterSpec(iVparam.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            }
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            
            String dDes = "";

            if (sSrcmode.equals("Base64")) {
                dDes = new String(java.util.Base64.getEncoder().encode(encrypted));
                dDes = java.util.Base64.getEncoder().encodeToString(dDes.getBytes(StandardCharsets.UTF_8));
            }

            else if (sSrcmode.equals("Hex"))
                dDes = bytesToHex(encrypted);
            else  dDes = new String(java.util.Base64.getEncoder().encode(encrypted));

            return dDes;//此处使用BASE64做转码。
//        }catch (Exception e){
//            return "加密错误，请确认选项无误!";
//        }
    }

    public static String decrypt(String sSrc, String slatKey, String vectorKey,String encodemode, String ivmode , String paddingmode,String sSrcmode) throws Exception {
        try {
            byte[] content ;
            Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
            byte[] raw = slatKey.getBytes();
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Key secretKey = new SecretKeySpec(raw, encodemode);

            if (ivmode.equals("ECB")) { // 如果为ECB模式，不进行偏移量加载
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            }
            if (sSrcmode.equals("Base64")) {
                sSrc = new String(java.util.Base64.getDecoder().decode(sSrc));
                content = Base64.getDecoder().decode(sSrc);
            } else if (sSrcmode.equals("Hex")) {
                content = toByteArray(sSrc);
            } else{
                sSrc = new String(java.util.Base64.getDecoder().decode(sSrc));
                content = Base64.getDecoder().decode(sSrc);
            }
            byte[] encrypted = cipher.doFinal(content);
            return new String(encrypted);
        }catch (Exception e){
            return "解密错误，请确认选项无误!";
        }
    }

}
