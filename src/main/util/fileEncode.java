package main.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author f0ng
 * @date 2022/4/1
 */
public class fileEncode {

    public  static String bytesEncode(File filename ) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(100000);
        byte[] b = new byte[100000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        byte[] data = bos.toByteArray();
        bos.close();
        for(byte a :data)
            System.out.println(a);

        String total = "{";
        for (byte d:data) {
            total = total + d  + ",";
        }
        total = total.substring(0,total.length()-1);
        total = total + "}";
        return total;
    }
    public static String Base64Encode(File fileName) throws IOException {


        byte[] bytes = loadFile(fileName);
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String encodedString = new String(encoded, StandardCharsets.US_ASCII);

        return encodedString;
    }
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
        }

        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        is.close();
        return bytes;

    }
}
