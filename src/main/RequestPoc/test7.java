package main.RequestPoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.*;
import main.Poc2ExpguiController;
import static main.RequestPoc.Readfile.exptoexp;
import static main.RequestPoc.Readfile.ymlFiletoexp;
import static main.RequestPoc.makeRequest.inputurl;
import static main.RequestPoc.makeRequest.listMakeRequest;
import static main.RequestPoc.test7.Url.extractLists;

public class test7 {
    static {
        try {
            test7.disableSSLCertificateChecking();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static void disableSSLCertificateChecking() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = new HostnameVerifier(){
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public static List<String> vullist = new ArrayList();
    public test7() {
    }
    public void getUriList( String[] url_list,String pocname){
        List<Thread> list = new ArrayList<Thread>();
        ExecutorService executor = Executors.newCachedThreadPool();
        try{
            for(int i = 0; i < url_list.length; i++) {
                MyRunnable runnable = new MyRunnable(pocname);
                Thread thread = new Thread(runnable, url_list[i]);
                thread.start();
                list.add(thread);
            }
            try {
                for(Thread thread : list) {
                    thread.join();
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            ;
        }
        executor.shutdown();
        while (!executor.isTerminated()){}
    }

    public static class MyRunnable implements Runnable {
        String pocname;
        public MyRunnable( String pocname){
            this.pocname = pocname;

        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start");
            Url a = new Url();
            a.addList(Thread.currentThread().getName(),pocname);
            System.out.println(Thread.currentThread().getName() + " end");
        }

    }
    public static class Url {

        private static Lock lock = new ReentrantLock();     // 开启显式家锁

        public Url() {
        }

        public void addList( String url,String pocname){
            lock.lock();

            try {

                Set<String> response = extractLists(url,pocname);
                for (String str:response)
                    vullist.add(str);

                // 根据替换后的url来发起请求

            } catch (Exception ex) {
                ;
            } finally {
                lock.unlock();     // 解锁
            }
        }

        public static Set<String> extractLists (String url ,String pat){
            Set<String> response_uri_lists = new HashSet<>();

            String response = sendGet(url) ;
            String pattern;

//        String pattern = "(org\\..*?|net\\..*?)[<|&|\"| ]";
            if (pat.contains(".."))
                pattern =  pat + "[a-zA-Z0-9_./-]{1,}";
            else
                pattern =   pat + "[a-zA-Z0-9_./-]{1,}";
//                  pattern = "/(.*)";
            // 创建 Pattern 对象
            System.out.println(pattern);
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
                }
            }

            return response_uri_lists;
        }

        public static Set<String> extractListsinput (String input ,String pat){
            Set<String> response_uri_lists = new HashSet<>();

            String pattern;

//        String pattern = "(org\\..*?|net\\..*?)[<|&|\"| ]";
            if (pat.contains(".."))
                pattern =  pat + "[a-zA-Z0-9_./-]{1,}";
            else
                pattern =   pat + "[a-zA-Z0-9_./-]{1,}";
//                  pattern = "/(.*)";
            // 创建 Pattern 对象
            System.out.println(pattern);
            Pattern r = Pattern.compile(pattern);
            // 现在创建 matcher 对象
            Matcher m = r.matcher(input);
            while(m.find()) {
                String single_uri = m.group().trim().replace("\"","").replace("<","")
                        .replace("&","");

                if (single_uri.contains("*"))
                    continue;
                else {
                    response_uri_lists.add(single_uri);
                }
            }

            return response_uri_lists;
        }

        public static String sendGet(String url) {
            String result = "";
            BufferedReader in = null;
            if (url.contains("/heapdump")){
                return "";
            }
            try {
                String urlNameString = url ;
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty( "user-agent" , "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段

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
//                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
//                    e2.printStackTrace();
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8080");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8080");

        String url = "";


        String pattern = "(http[s]{0,1}://.*?)/|(http[s]{0,1}://.*)";

//        String pat = "org.\nnet."; // 匹配的
        String total = "";
//        (org\..*?|net\..*?)
        String pat = ""; // 匹配的
        if (pat.contains(".")){

            String[] pats = pat.split("\n");
            for( String str: pats) {
                total = total + str.replace(".", "\\.");
                total = total + ".*?|";
            }
            pat = "(" + total.substring(0,total.length()-1) + ")";

        }else{
            if (pat.equals(""))
                pat = "/";
        }

        String black_lists = ".html\n.ftl\n//"; // 黑名单
        String[] url_list ;

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(url);
        m.find();
        String host = m.group(); // 获取协议+域名
        Set<String> response = extractLists(url, pat);

        url_list = new ArrayList<>(response).toArray(new String[0]);

        for (int i = 0; i < url_list.length; i++) {
            test7.vullist.add(url_list[i]);
            if (!url_list[i].contains("http"))
                url_list[i] = host.substring(0, host.length() - 1) + url_list[i];
        }

        test7 a = new test7();
        a.getUriList( url_list , pat );
        String total2 = "" ;
        String[] blacklists = black_lists.split("\n");
        Set<String> total_lists = new HashSet<>();

        for (String strr: test7.vullist) {
            String isblack = "0";
            if (!black_lists.equals(""))
            for (String str:blacklists) { // 剔除黑名单
                if (strr.contains(str))
                    isblack = "1";
            }
            if (isblack.equals("0"))
                total_lists.add(strr.trim());
        }
        for (String strr: total_lists) {
            total2 = total2 + strr + "\n";
        }
        System.out.println(total2);
    }
}