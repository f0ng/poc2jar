package main.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
提取路径
找所有的xml properties 直接拼接
 */
public class extractPath {
    public static void main(String[] args) {
        Set<String> response = extractLists("https://www.nagasoft.cn/WEB-INF/web.xml");

        System.out.println(response);
    }

    public static Set<String> extractLists (String url ){
        Set<String> response_uri_lists = new HashSet<>();

        String response = sendGet(url) ;

//        String pattern = "(org\\..*?|net\\..*?)[<|&|\"| ]";

        String pattern = "(\\/WEB-INF\\/.*?)[\\s{1}|<|&|\"| ]";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(response);
        while(m.find()) {
            String single_uri = m.group().trim().replace("\"","").replace("<","")
                    .replace("&","");

            if (single_uri.contains("*"))
                continue;
            else {
                response_uri_lists.add(single_uri);
                System.out.println(single_uri);
            }
        }

        return response_uri_lists;
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            result = "发送请求出现异常！";
            System.out.println("发送请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
