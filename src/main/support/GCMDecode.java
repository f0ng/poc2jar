package main.support;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.shiro.codec.Base64;
import org.bouncycastle.crypto.CryptoException;
import java.security.SecureRandom;


public class GCMDecode {

    public static void main(String[] args) throws Exception {
//        System.out.println(encrypt("zSyK5Kp6PZAAjlT+eeNMlg=="));

        System.out.println(decrypt("zSyK5Kp6PZAAjlT+eeNMlg=="));


    }
    public static String encrypt(String Shirokey) throws Exception {

        byte[] payloads = "aaaa".getBytes();
        byte[] key = java.util.Base64.getDecoder().decode(Shirokey);
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        System.out.println(bytesToHex(iv));
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128,iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(payloads);
        byte[] encryptedIvandtext = new byte[ivSize + encrypted.length];

        System.arraycopy(iv, 0, encryptedIvandtext, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedIvandtext, ivSize, encrypted.length);
        System.out.println(bytesToHex(encryptedIvandtext));
        String b64Payload = Base64.encodeToString(encryptedIvandtext);
        return b64Payload;
    }

    public static String decrypt(String Shirokey) throws Exception {

        byte[] encryptedIvandtext = java.util.Base64.getDecoder().decode("ytlVi/3eothrcAtfkJD6/a+uXaykU2g1jVIFxe/BI5hc+4A4");
        byte[] key = java.util.Base64.getDecoder().decode(Shirokey);
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        byte[] encrydata = new byte[encryptedIvandtext.length - iv.length];

        System.arraycopy(encryptedIvandtext, 0, iv, 0, 16);
        System.out.println(bytesToHex(iv));
        System.arraycopy(encryptedIvandtext, 16, encrydata, 0, encryptedIvandtext.length - iv.length);
        System.out.println(bytesToHex(encryptedIvandtext));
        System.out.println(bytesToHex(encrydata));

        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128,iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKeySpec, ivParameterSpec);

        System.out.println("***************");
        System.out.println(bytesToHex(encrydata));
        System.out.println(bytesToHex(key));
        System.out.println(bytesToHex(iv));
        byte[] encrypted = cipher.doFinal(encrydata);
        return new String(encrypted);
    }


    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() < 2) {
                sb.append(0);
            }

            sb.append(hex);
        }

        return sb.toString();
    }


}
