package main.support;

//import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
//import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Base64;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Expdecode {
    public Expdecode(){}

    public static void main(String[] args) throws Exception {
        SerializationDumper sd = new SerializationDumper();
        byte[] fileContents;
//        String rememberMe = "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";

        // gcm的cookie
        String rememberMe = "2aFmeBbUSYOtYPz4N1wX8yppHpBI6005E0A52swUxzszuUcOrzwQnhX0Yz3i4DxjZJcMP2uoD4rqNAfxvQYDXmFqxJ0FnPUdwsK8t3zrH8sHwaWFb0NJcACv2wY9Fa0XPTVO7oKpLzaA3LieBNMD4HqnhzCgBsABLBIi83BD2a9OcWtJA8wssI9odRpN4BIbUiwmNZMaFreVWffZdf9+jEnOJBQc+Z9OfPhsSqeFuZfOTjsL9pL6VgNb+hxGre91FqxGqPy+zZmWFv/n/dz6qiBdVgnP7M2qSj5KezTGEA5/Yhp6FawSFABqC7Tr90gnw9dFTrCi6PcwJOUDE16rp9iZRbROrsBcJfe4JNulp5uopxvcLGsA2Y2dKC8xa8EeLPX+UZOUFeW4s+4MUAcOE72QwG1mRep7TdXArkBwI2jgdSbTl0HtB7ibXbPUqTue+O0fhW5aM9cSjQDvQcMnlcFFdPsGxFVE1K3aXn9Q4DwBXhssdKEZVjv1XEINBME5kQ==";
//        String rememberMe = "ytlVi/3eothrcAtfkJD6/a+uXaykU2g1jVIFxe/BI5hc+4A4";
        Expdecode shiroDecypt = new Expdecode();
        fileContents = shiroDecypt.BruteCipherKeygcm(rememberMe, "/Users/f0ngf0ng/JAVA/seeyontest/src/main/support/keys.conf");

        System.out.println(  new String(fileContents) );
//        for(int i = 0; i < fileContents.length; ++i) {
//            sd._data.add(fileContents[i]);
//        }
//
//        sd._enablePrinting = false;
//        sd.parseStream();
//        File fileBytecodes = new File("/Users/f0ngf0ng/JAVA/seeyontest/bytecodes.java");
//
//        if (fileBytecodes.exists() && fileBytecodes.length() > 0L) {
//            System.out.println("[+] 使用的Gadeget为TemplatesImpl，bytecodes中的代码存放在bytecodes.class，可直接用idea等查看");
//        } else {
//            System.out.println("[+] 序列化数据存放在shiro.ser，可使用-r shiro.ser命令查看序列化数据结构 或 xxd shiro.ser来查找感兴趣的内容");
//        }
    }

    private byte[] DecyptCookiegcm(byte[] cipherTextBytes, byte[] keyBytes, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        if (keyBytes.length != 16)
//            return null;

        try {
            System.out.println(bytesToHex(keyBytes));
            GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128, iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(cipherTextBytes);
            return original;
        }catch (Exception e){
//            e.printStackTrace();
            return null;
        }
    }

    private byte[] DecyptCookie(byte[] cipherTextBytes, byte[] keyBytes, byte[] iv) {
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(2, skeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(cipherTextBytes);
            return original;
        } catch (NoSuchAlgorithmException var8) {
        } catch (NoSuchPaddingException var9) {
        } catch (InvalidKeyException var10) {
        } catch (InvalidAlgorithmParameterException var11) {
        } catch (IllegalBlockSizeException var12) {
        } catch (BadPaddingException var13) {
        }

        return null;
    }

    private String bytesToHex(byte[] bytes) {
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

    public byte[] BruteCipherKey(String memberCookie, String cipherKey) {
        System.out.println("[+] 默认碰撞的密钥为广泛流传的100 key");
        File f = new File(cipherKey);
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(cipherKey);
        ArrayList cipherKeysList = new ArrayList();

        try {
//            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String s = null;

            while((s = br.readLine()) != null) {
                cipherKeysList.add(s);
            }

            br.close();
        } catch (Exception var18) {
            var18.printStackTrace();
        }

        String[] cipherKeys = (String[])cipherKeysList.toArray(new String[cipherKeysList.size()]);
        String[] var20 = cipherKeys;
        int var7 = cipherKeys.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String key = var20[var8];

            try {
                byte[] bytekey = Base64.getDecoder().decode(key);
                byte[] rawmemberCookie = Base64.getDecoder().decode(memberCookie);
                byte[] iv = new byte[bytekey.length];
                System.arraycopy(rawmemberCookie, 0, iv, 0, bytekey.length);

                byte[] bytesPayload = new byte[rawmemberCookie.length - bytekey.length];
                System.arraycopy(rawmemberCookie, bytekey.length, bytesPayload, 0, bytesPayload.length);
                byte[] decryptResult = this.DecyptCookie(bytesPayload, bytekey, iv);
                if (decryptResult != null) {
                    String hexResult = this.bytesToHex(decryptResult);
                    if (hexResult.startsWith("aced")) {
                        FileOutputStream fos = null;
                        System.out.println("[+] Shiro Cookie 加密密钥为：" + key);
                        fos = (new FileOutputStream("property/shiro.ser"));
                        try{
                            fos.write(decryptResult);
                            fos.close();
                        }catch (Exception e){

                        }
//                        (new FileOutputStream(new File("property/shiro.ser"))).close();
                        return decryptResult;
                    }
                }
            } catch (IOException var17) {
            }
        }

        return null;
    }

    public byte[] BruteCipherKeygcm(String memberCookie, String cipherKey) {
        System.out.println("[+] 默认碰撞的密钥为广泛流传的100 key");

        File f = new File(cipherKey);
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(cipherKey);
        ArrayList cipherKeysList = new ArrayList();

        try {
//            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String s = null;
            while((s = br.readLine()) != null) {
                cipherKeysList.add(s);
            }
            br.close();
        } catch (Exception var18) {
            var18.printStackTrace();
        }
        String[] cipherKeys = (String[])cipherKeysList.toArray(new String[cipherKeysList.size()]);
        String[] var20 = cipherKeys;
        int var7 = cipherKeys.length;
        for(int var8 = 0; var8 < var7; ++var8) {
            String key = var20[var8];
            System.out.println(key);

            try {
                byte[] bytekey = java.util.Base64.getDecoder().decode(key);
                byte[] rawmemberCookie = Base64.getDecoder().decode(memberCookie);
                byte[] iv = new byte[bytekey.length];
                byte[] bytesPayload = new byte[rawmemberCookie.length-bytekey.length];

                System.arraycopy(rawmemberCookie, 0, iv, 0, bytekey.length);
                System.arraycopy(rawmemberCookie, bytekey.length, bytesPayload, 0, rawmemberCookie.length-bytekey.length);

                byte[] decryptResult = this.DecyptCookiegcm(bytesPayload, bytekey, iv);

                if (decryptResult != null) {
                    String hexResult = this.bytesToHex(decryptResult);
                    System.out.println(hexResult);
                    if (hexResult.startsWith("aced")) {
                        FileOutputStream fos = null;
                        System.out.println("[+] Shiro Cookie 加密密钥为：" + key);
                        fos = (new FileOutputStream("property/shiro.ser"));
                        try{
                            fos.write(decryptResult);
                            fos.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    return decryptResult;
                }

            } catch (IOException var17) {
                var17.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String BruteCipherKey(String memberCookie, String cipherKey,String code) {
        System.out.println("[+] 默认碰撞的密钥为广泛流传的100 key");
        File f = new File(cipherKey);
        ArrayList cipherKeysList = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String s = null;

            while((s = br.readLine()) != null) {
                cipherKeysList.add(s);
            }

            br.close();
        } catch (Exception var18) {
            var18.printStackTrace();
        }

        String[] cipherKeys = (String[])cipherKeysList.toArray(new String[cipherKeysList.size()]);
        String[] var20 = cipherKeys;
        int var7 = cipherKeys.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String key = var20[var8];

            byte[] bytekey = Base64.getDecoder().decode(key);
            byte[] rawmemberCookie = Base64.getDecoder().decode(memberCookie);
            byte[] iv = new byte[bytekey.length];
            System.arraycopy(rawmemberCookie, 0, iv, 0, bytekey.length);
            byte[] bytesPayload = new byte[rawmemberCookie.length - bytekey.length];
            System.arraycopy(rawmemberCookie, bytekey.length, bytesPayload, 0, bytesPayload.length);
            byte[] decryptResult = this.DecyptCookie(bytesPayload, bytekey, iv);
            if (decryptResult != null) {
                String hexResult = this.bytesToHex(decryptResult);
                if (hexResult.startsWith("aced")) {
                    System.out.println("[+] Shiro Cookie 加密密钥为：" + key);
                    return key;
                }
            }
        }

        return null;
    }

    public String BruteCipherKeygcm(String memberCookie, String cipherKey ,String code) {
        System.out.println("[+] 默认碰撞的密钥为广泛流传的100 key");

        File f = new File(cipherKey);
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(cipherKey);
        ArrayList cipherKeysList = new ArrayList();

        try {
//            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String s = null;
            while((s = br.readLine()) != null) {
                cipherKeysList.add(s);
            }
            br.close();
        } catch (Exception var18) {
            var18.printStackTrace();
        }
        String[] cipherKeys = (String[])cipherKeysList.toArray(new String[cipherKeysList.size()]);
        String[] var20 = cipherKeys;
        int var7 = cipherKeys.length;
        for(int var8 = 0; var8 < var7; ++var8) {
            String key = var20[var8];
            System.out.println(key);

            try {
                byte[] bytekey = java.util.Base64.getDecoder().decode(key);
                byte[] rawmemberCookie = Base64.getDecoder().decode(memberCookie);

                byte[] iv = new byte[bytekey.length];
                System.out.println(bytesToHex(rawmemberCookie));

                byte[] bytesPayload = new byte[rawmemberCookie.length-bytekey.length];

                System.arraycopy(rawmemberCookie, 0, iv, 0, bytekey.length);
                System.arraycopy(rawmemberCookie, bytekey.length, bytesPayload, 0, rawmemberCookie.length-bytekey.length);

                System.out.println(bytesToHex(iv));
                System.out.println(bytesToHex(bytesPayload));

                byte[] decryptResult = this.DecyptCookiegcm(bytesPayload, bytekey, iv);
//                System.out.println(bytesToHex(decryptResult));

                if (decryptResult != null) {
                    String hexResult = this.bytesToHex(decryptResult);
                    System.out.println(hexResult);
                    if (hexResult.startsWith("aced")) {
                        FileOutputStream fos = null;
                        System.out.println("[+] Shiro Cookie 加密密钥为：" + key);
                        fos = (new FileOutputStream("property/shiro.ser"));
                        try{
                            fos.write(decryptResult);
                            fos.close();
                        }catch (Exception e){  }
                    }
                    return key;
                }

            } catch (IOException var17) {
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}


