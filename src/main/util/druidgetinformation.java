package main.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;


public class druidgetinformation {
    // 解决https的问题 24-59行
    // https://gist.github.com/aembleton/889392

    static {
        try {
            druidgetinformation.disableSSLCertificateChecking();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    public static void disableSSLCertificateChecking() throws NoSuchAlgorithmException, KeyManagementException {
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
    //

    public static void main(String[] args) {
//        获取jdbc链接和用户名
//        String[] test  = getJdbcUsername("http:///druid/");
//        System.out.println(test[0]);
//        System.out.println(test[1]);

//        获取sql语句
//        String url = "http:///druid/";
//        String[] lists2 = getSql(url);
//        for(int i=0;i<lists2.length;i++)
//            System.out.println(lists2[i]);

//        获取请求路径
        String url = "http://open.njnaedu.cn:7777/druid/index.html";
        String[][] lists = getUri(url);
        for(int i = 0;i<lists.length-1;i++)
            for(int j = i+1;j<lists.length-1;j++)
            if (lists[i][0] != null && lists[j][0] != null ) {
                if ( Integer.parseInt(lists[i][1]) < Integer.parseInt(lists[j][1])){
                    String[][] mid = new String[1][3];
                    mid[0][0] = lists[i][0];
                    mid[0][1] = lists[i][1];
                    mid[0][2] = lists[i][2];
                    lists[i][0] = lists[j][0];
                    lists[i][1] = lists[j][1];
                    lists[i][2] = lists[j][2];
                    lists[j][0] = mid[0][0];
                    lists[j][1] = mid[0][1];
                    lists[j][2] = mid[0][2];
                }
            }
        for(int i = 0;i<lists.length;i++)
            if (lists[i][0] != null ) {
//                System.out.println(lists[i][0]);
                System.out.println(lists[i][1]);
//                System.out.println(lists[i][2]);
            }


//        获取Session
//        String url = "http:///druid/";
//        String[] lists = getSession(url);
//        for(int i=0;i<lists.length;i++)
//            System.out.println(lists[i]);
    }

    /***
     * 去除String数组中的空值
     */
    public static String[] deleteArrayNull(String string[]) {
        String strArr[] = string;
        // step1: 定义一个list列表，并循环赋值
        ArrayList<String> strList = new ArrayList<String>();
        for (int i = 0; i < strArr.length; i++) {
            strList.add(strArr[i]);
        }
        // step2: 删除list列表中所有的空值
        while (strList.remove(null));
        while (strList.remove(""));
        // step3: 把list列表转换给一个新定义的中间数组，并赋值给它
        String strArrLast[] = strList.toArray(new String[strList.size()]);
        return strArrLast;
    }

    public static String[] getSession(String url){
        url = url.replace("index.html","");
        if (url.indexOf("druid/") > 0)
            url = url + "websession.json?orderBy=&orderType=asc&page=1&perPageCount=1000000&";
        else if(url.indexOf("druid") > 0 )
            url = url + "/websession.json?orderBy=&orderType=asc&page=1&perPageCount=1000000&";
        else if(url.endsWith("/"))
            url = url + "druid/websession.json?orderBy=&orderType=asc&page=1&perPageCount=1000000&";
        else
            url = url + "/druid/websession.json?orderBy=&orderType=asc&page=1&perPageCount=1000000&";
        String res = sendGet(url,"");
        String data = res.split("\n")[0];
        JSONObject jsonObject = JSON.parseObject(String.valueOf(data));
        JSONArray jsonArray = jsonObject.getJSONArray("Content"); // 读取Content
        String[] lists = new String[1000];

        List list = new ArrayList();
        for(int i=0;i<jsonArray.size() ;i++)
            list.add(jsonArray.getString(i));
        for(int i=0;i<list.size();i++) {
            JSONObject jsonObject2 = JSON.parseObject(String.valueOf(list.get(i)));
            String[] JdbcUsername = new String[2];
            JdbcUsername[0] = (String) jsonObject2.get("SESSIONID");
            lists[i] = JdbcUsername[0];
        }
        String[] lists2 = deleteArrayNull(lists);
        return lists2;

    }

    public static String[][] getUri(String url){
        url = url.replace("index.html","");
        if (url.indexOf("druid/") > 0)
            url = url + "weburi.json?orderBy=URI&orderType=desc&page=1&perPageCount=1000000&";
        else if(url.indexOf("druid") > 0 )
            url = url + "/weburi.json?orderBy=URI&orderType=desc&page=1&perPageCount=1000000&";
        else if(url.endsWith("/"))
            url = url + "druid/weburi.json?orderBy=URI&orderType=desc&page=1&perPageCount=1000000&";
        else
            url = url + "/druid/weburi.json?orderBy=URI&orderType=desc&page=1&perPageCount=1000000&";
        String res = sendGet(url,"");
        String data = res.split("\n")[0];
        JSONObject jsonObject = JSON.parseObject(String.valueOf(data));
        JSONArray jsonArray = jsonObject.getJSONArray("Content"); // 读取Content
        String[][] lists = new String[1000][3];

        List list = new ArrayList();
        for(int i=0;i<jsonArray.size() ;i++)
            list.add(jsonArray.getString(i));
        for(int i=0;i<list.size();i++) {
            JSONObject jsonObject2 = JSON.parseObject(String.valueOf(list.get(i)));
            String[][] JdbcUsername = new String[1][3];
            JdbcUsername[0][0] = (String) jsonObject2.get("URI");
            JdbcUsername[0][1] = jsonObject2.get("RequestCount").toString(); // 请求次数
            JdbcUsername[0][2] = jsonObject2.get("JdbcExecuteCount").toString(); // 请求次数
            lists[i][0] = String.valueOf(JdbcUsername[0][0]);
            lists[i][1] = String.valueOf(JdbcUsername[0][1]);
            lists[i][2] = String.valueOf(JdbcUsername[0][2]);
        }
        return lists;

    }

    public static String[] getSql(String url){
        url = url.replace("index.html","");
        if (url.indexOf("druid/") > 0)
            url = url + "sql.json?orderBy=SQL&orderType=desc&page=1&perPageCount=1000000&";
        else if(url.indexOf("druid") > 0 )
            url = url + "/sql.json?orderBy=SQL&orderType=desc&page=1&perPageCount=1000000&";
        else if(url.endsWith("/"))
            url = url + "druid/sql.json?orderBy=SQL&orderType=desc&page=1&perPageCount=1000000&";
        else
            url = url + "/druid/sql.json?orderBy=SQL&orderType=desc&page=1&perPageCount=1000000&";
        String res = sendGet(url,"");
        String data = res.split("\n")[0];
        JSONObject jsonObject = JSON.parseObject(String.valueOf(data));
        JSONArray jsonArray = jsonObject.getJSONArray("Content"); // 读取Content
        String[] lists = new String[1000];

        List list = new ArrayList();
        for(int i=0;i<jsonArray.size() ;i++)
            list.add(jsonArray.getString(i));
        for(int i=0;i<list.size();i++) {
            JSONObject jsonObject2 = JSON.parseObject(String.valueOf(list.get(i)));
            String[] JdbcUsername = new String[2];
            JdbcUsername[0] = (String) jsonObject2.get("SQL");
//            JdbcUsername[1] = (String) jsonObject2.get("ExecuteCount"); // 执行次数
            lists[i] = JdbcUsername[0].replace("\t","").replace("\n","").replaceAll(" +"," ").replace(",    ",",");
        }
        String[] lists2 = deleteArrayNull(lists);
        return lists2;
    }

    public static String[] getJdbcUsername(String url){
        url = url.replace("index.html","");
        if (url.indexOf("druid/") > 0)
            url = url + "datasource.json";
        else if(url.indexOf("druid") > 0 )
            url = url + "/datasource.json";
        else if(url.endsWith("/"))
            url = url + "druid/datasource.json";
        else
            url = url + "/druid/datasource.json";
        String res = sendGet(url,"");
        String data = res.split("\n")[0];
        if (data == "发送请求出现异常！"){
            String[] JdbcUsername = new String[2];
            JdbcUsername[0] = data;
            JdbcUsername[1] = data;
            return JdbcUsername;

        }else {
            try{
            JSONObject jsonObject = JSON.parseObject(String.valueOf(data));
            JSONArray jsonArray = jsonObject.getJSONArray("Content"); // 读取Content

            List list = new ArrayList();
            for (int i = 0; i < jsonArray.size(); i++)
                list.add(jsonArray.getString(i));
            JSONObject jsonObject2 = JSON.parseObject(String.valueOf(list.get(0)));
//        System.out.println(jsonObject2.get("URL")); // 获取jdbc的链接    键值
//        System.out.println(jsonObject2.get("UserName")); // 获取数据库用户名 键值
            String[] JdbcUsername = new String[2];
            JdbcUsername[0] = (String) jsonObject2.get("URL");
            JdbcUsername[1] = (String) jsonObject2.get("UserName");
            return JdbcUsername;
            }catch (Exception e) {
                String[] JdbcUsername = new String[2];
                JdbcUsername[0] = "请确认是否存在漏洞";
                JdbcUsername[1] = "请确认是否存在漏洞";
                return JdbcUsername;
            }
        }
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
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
