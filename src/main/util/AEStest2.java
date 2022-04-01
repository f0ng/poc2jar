package main.util;

//import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Base64;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static main.util.encodeUtil.*;
//import sun.misc.BASE64Encoder;

public class AEStest2 {
    private static Charset CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {
    }

    public static String encryptbuwei(String encryptedData,String appkey,String sessionKey,String ivStr,String encodemode, String ivmode , String paddingmode ,String sSrcmode ,String keyivmode) throws Exception {
        try {

        byte[] textBytes = encryptedData.getBytes(CHARSET);
        byte[] appKeyBytes = appkey.getBytes(CHARSET);
        byte[] beforeEncryptBytes = new byte[16 + 4 + textBytes.length + appKeyBytes.length];

        //复制用户长度
        System.arraycopy(ByteBuffer.allocate(4).putInt(textBytes.length).array(), 0, beforeEncryptBytes, 16, 4);
        //复制用户数据
        System.arraycopy(textBytes, 0, beforeEncryptBytes, 20, textBytes.length);
        System.arraycopy(appKeyBytes, 0, beforeEncryptBytes, 20 + textBytes.length, appKeyBytes.length);
        byte[] pkcs7 = PKCS7Encoder.encode(beforeEncryptBytes.length);
        beforeEncryptBytes = Arrays.copyOf(beforeEncryptBytes, beforeEncryptBytes.length + pkcs7.length);
        System.arraycopy(pkcs7, 0, beforeEncryptBytes, beforeEncryptBytes.length - pkcs7.length, pkcs7.length);

        byte[] aesKey = java.util.Base64.getDecoder().decode(sessionKey);
        byte[] encrypted;
        try {
            Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, encodemode);
            byte[] ivBytes = java.util.Base64.getDecoder().decode(ivStr);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            encrypted = cipher.doFinal(beforeEncryptBytes);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return java.util.Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "加密错误，请确认选项无误!";
        }
    }

    public static String decryptbuwei(String encryptedData,String sessionKey,String iv,String encodemode, String ivmode , String paddingmode ,String sSrcmode,String keyivmode) throws Exception {

        try {
        byte[] data ;
        byte[] aseKey = new byte[0];
        byte[] ivData = new byte[0];
        if (sSrcmode.equals("Base64")) data = Base64.getDecoder().decode(encryptedData);
        else if (sSrcmode.equals("Hex")) {
            data = hexToByteArray(encryptedData);
        }
        else data = encryptedData.getBytes(StandardCharsets.UTF_8);

        if (keyivmode.equals("Base64")) {
            aseKey = Base64.getDecoder().decode(sessionKey);
            ivData = Base64.getDecoder().decode(iv);
        }else if(keyivmode.equals("Hex")) {
            aseKey = hexToByteArray(sessionKey);
            ivData = hexToByteArray(iv);
        }else {
            aseKey = sessionKey.getBytes(StandardCharsets.UTF_8);
            ivData = iv.getBytes(StandardCharsets.UTF_8);
        }

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(encodemode + "/" + ivmode + "/" + paddingmode);
        Key sKeySpec = new SecretKeySpec(aseKey, encodemode);
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIv(ivData));// 初始化

        byte[] original = cipher.doFinal(data);
        String xmlContent;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和ClientId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            if (xmlLength > 65535) {
                throw new RuntimeException("aesKey invalid");
            }
            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
        } catch (Exception e) {
            throw new Exception(e);
        }

        return xmlContent;

        } catch (Exception e) {
            return "解密错误，请确认选项无误!";
        }

    }

    public static AlgorithmParameters generateIv(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 还原4个字节的网络字节序
     *
     * @param orderBytes 字节码
     *
     * @return sourceNumber
     */
    private static int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        int length = 4;
        int number = 8;
        for (int i = 0; i < length; i++) {
            sourceNumber <<= number;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }
}

class PKCS7Encoder {

    static Charset CHARSET = Charset.forName("utf-8");
    static int BLOCK_SIZE = 32;

    /**
     * 获得对明文进行补位填充的字节.
     *
     * @param count 需要进行填充补位操作的明文字节个数
     * @return 补齐用的字节数组
     */
    static byte[] encode(int count) {
        // 计算需要填充的位数
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE;
        }
        // 获得补位所用的字符
        char padChr = chr(amountToPad);
        String tmp = new String();
        for (int index = 0; index < amountToPad; index++) {
            tmp += padChr;
        }
        return tmp.getBytes(CHARSET);
    }

    /**
     * 删除解密后明文的补位字符
     *
     * @param decrypted 解密后的明文
     * @return 删除补位字符后的明文
     */
    static byte[] decode(byte[] decrypted) {
        int pad = (int) decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    /**
     * 将数字转化成ASCII码对应的字符，用于对明文进行补码
     *
     * @param a 需要转化的数字
     * @return 转化得到的字符
     */
    static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }
}

