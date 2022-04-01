package main.RequestPoc;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import main.RequestPoc.Readfile;
import sun.net.www.MessageHeader;

// 第一个函数，通过传入的数组发起http请求，返回响应包，数据为响应时间，响应内容等

public class makeRequest extends MessageHeader {
    @Override
    public synchronized void setIfNotSet(String arg0, String arg1) {
        if ("Content-type".equals(arg0)) {
            return;
        }

        if ("Connection".equals(arg0)) {
            return;
        }

        super.setIfNotSet(arg0, arg1);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8080");
        // 代理

        String[][] request_header = Readfile.readFileByLines("src/main/1.txt");
        Map<String,String> responseHeaderbody = listMakeRequest(request_header);
        System.out.println(responseHeaderbody);
        for (String key : responseHeaderbody.keySet()) {
            System.out.println("key="+ key + "\nvalue= " + responseHeaderbody.get(key));
        }

    }

    public static String[][] inputurl (String[][] request_header ,String url){

        request_header[0][1] = request_header[0][1].replace("$",url);

        return request_header;
    }


    public static  Map<String,String> listMakeRequest (String[][] request_header) {

        String result = "";
        String url = "";
        String data = "";
        String urlNameString = null;

        if (request_header[0][1].trim().contains("http"))
            url =  request_header[0][1].trim() + request_header[0][3];
        else
            url = "http://" + request_header[0][1].trim() + request_header[0][3];

        String method = request_header[0][0].trim();
        String param = request_header[0][4].trim(); // param 参数
        try{
        data = request_header[0][5].trim();
        }catch (NullPointerException e){
            ;
        }

        BufferedReader in = null;
        PrintWriter out = null;
        Map<String, List<String>> map = null;
        String requestHeader = "";
        Map<String,String> map2 = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date datebegin = new Date();// 获取当前时间

        try {
            if ( param.equals("") ) {
                urlNameString = url; //如果param参数没有，那么漏洞url就是url
            } else {
            urlNameString = url + "?" + param; //如果param有参数，那么漏洞url就是url + ? + uri
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性

            Object target = connection;
            if (!target.getClass().equals(sun.net.www.protocol.http.HttpURLConnection.class)) {
                //https
                Field field1 = connection.getClass().getDeclaredField("delegate");
                field1.setAccessible(true);
                target = field1.get(connection);
            }
            Field field2 = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("requests");
            field2.setAccessible(true);
            test customMessageHeader = new test();
            field2.set(target, customMessageHeader);


            for(int i=0;i<request_header[1].length;i++)
            {
                if (request_header[1][i] != null ) {
                    String[] header_single = request_header[1][i].split(": ");
                    connection.setRequestProperty(header_single[0], header_single[1]);

                }
            }
            System.out.println(method);
            // 如果为POST，即方法设置为POST
            if (method.equals("POST")) {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }else if (method.equals("PUT")) {
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }

            // 建立实际的连接
//            connection.connect();

            // 获取所有响应头字段
//            try{
//                try{
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
//                }catch (NullPointerException | IOException e){
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
//                }
//            }catch (NullPointerException ignored){
//                try{
//                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso8859-1"));
//                }catch (NullPointerException | IOException e){
//                    in = new BufferedReader(new InputStreamReader(connection.getErrorStream(),"utf-8"));
//                }
//            }

            byte[] getData = null;
            InputStream inputStream;

            //测试
            try{
                inputStream = connection.getInputStream();
                getData  = readInputStream(connection.getInputStream());
            }catch (NullPointerException | IOException e){
                inputStream = connection.getErrorStream();
                getData  = readInputStream(connection.getErrorStream());
            }

            try{
            inputStream.read(getData);
            String str = new String(getData);
            InputStream is = new GZIPInputStream(new ByteArrayInputStream(str.getBytes()));
            BufferedReader ins = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = ins.readLine()) != null) {
                result += line +'\n';
            }}
            catch (NullPointerException e){
                result = "";
            }

            map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                requestHeader = requestHeader +key + ":" + map.get(key)+'\n';
            }

            Date dateover = new Date();// 获取当前时间

            ParsePosition posbegin = new ParsePosition(0);
            ParsePosition posover = new ParsePosition(0);

            Date dt1 = formatter.parse(formatter.format(datebegin), posbegin);
            Date dt2 = formatter.parse(formatter.format(dateover), posover);

            long l = dt2.getTime() - dt1.getTime();//这是他们相差的毫秒

//            System.out.println("123123" + result);
            map2.put(requestHeader ,"Reseponse:" + l + "ms\n" + result );

        } catch (Exception e) {
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
        return map2;
    }

    public static  Map<String,String> listMakeRequest (String[][] request_header,String ishttps) {
        String result = "";
        String url = "";
        String data = null;
        String urlNameString = null;

        if (request_header[0][1].trim().contains("http"))
            url =  request_header[0][1].trim() + request_header[0][3];
        else {
            url = "http://" + request_header[0][1].trim() + request_header[0][3];
        }

        if (url.contains("http://") && ishttps.equals("https")) {
            String[] urllist = url.split("//");
            if (urllist.length > 2) {
                url = "https:";
                for (String s:urllist)
                    System.out.println(s);

                for (int ii = 1; ii < urllist.length; ii++)
                    url = url + "//" + urllist[ii].trim();
            }
            else
            url = "https://" + urllist[1];
        }


        String method = request_header[0][0].trim();
        String param = request_header[0][4].trim(); // param 参数
        try{
            data = request_header[0][5].trim();
            System.out.println(data);
        }catch (NullPointerException ignored){
            ;
        }

        BufferedReader in = null;
        PrintWriter out = null;
        Map<String, List<String>> map = null;
        String requestHeader = "";
        Map<String,String> map2 = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date datebegin = new Date();// 获取当前时间

        try {
            if ( param.equals("") ) {
                urlNameString = url; //如果param参数没有，那么漏洞url就是url
            } else {
                urlNameString = url + "?" + param; //如果param有参数，那么漏洞url就是url + ? + uri
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性

            Object target = connection;
            if (!target.getClass().equals(sun.net.www.protocol.http.HttpURLConnection.class)) {
                Field field1 = connection.getClass().getDeclaredField("delegate");
                field1.setAccessible(true);
                target = field1.get(connection);
            }
            Field field2 = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("requests");
            field2.setAccessible(true);
            test customMessageHeader = new test();
            field2.set(target, customMessageHeader);

            connection.setConnectTimeout(20000);

            connection.setReadTimeout(20000);

            connection.setInstanceFollowRedirects(false); // 禁止跳转

            for(int i=0;i < request_header[1].length;i++)
            {
                if (request_header[1][i] != null ) {
                    String[] header_single = request_header[1][i].split(": ");
                    System.out.println("header_single[0]" + header_single[0]);
                    if (header_single[0].contains(":"))
                        header_single[0] = header_single[0].substring(0,header_single[0].length()-1);
                    try{
                    connection.setRequestProperty(header_single[0], header_single[1]);
                    }catch (ArrayIndexOutOfBoundsException e){ // 捕捉当请求头参数为空的时候
                        connection.setRequestProperty(header_single[0], "");
                    }
                }
            }

            // 如果为POST，即方法设置为POST
            if (method.equals("POST")) {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }else if (method.equals("PUT")) {
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
//            try{
//                try{
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
//                }catch (NullPointerException | IOException e){
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
//                }
//            }catch (NullPointerException | ZipException ignored){


//                //测试
//                byte[] getData = null;
//                InputStream inputStream;
//
//                try{
//                    inputStream = connection.getInputStream();
//                    getData  = readInputStream(connection.getInputStream());
//                }catch (NullPointerException | IOException e){
//                    inputStream = connection.getErrorStream();
//                    getData  = readInputStream(connection.getErrorStream());
//                }
//
//                try{
//                    inputStream.read(getData);
//                    String str = new String(getData);
//                    InputStream is = new ByteArrayInputStream(str.getBytes());
//                    BufferedReader ins = new BufferedReader(new InputStreamReader(is));
//
//                    String line;
//                    while ((line = ins.readLine()) != null) {
//                        result += line + '\n';
//                    }}
//                catch (NullPointerException e){
//                    result = "";
//                }

//                ————————开始
            // 获取响应头
            map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                requestHeader = requestHeader + key + ":" + map.get(key)+'\n';
//                System.out.println("requestHeader" + requestHeader);
            }

            try {
                try {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        // 解压内容
                        if (requestHeader.contains("Content-Encoding:[gzip]")){
                            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                        }


//                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso8859-1"));
                } catch (NullPointerException | IOException e) {
                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
                    // 解压内容
                    if (requestHeader.contains("Content-Encoding:[gzip]")){
                        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
                    }
//                    in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                }
            }catch (Exception e)
            {
                try{
                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                }catch (NullPointerException | IOException ee){
                    try {
                        in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }catch (NullPointerException eee){
                        in = null;
                    }
                }
            }

//            }



//            ——————————————结束
            String line ;
            try{while ((line = in.readLine()) != null) {
                result += line + '\n';
//                System.out.println("result" + result);
            }}catch (NullPointerException e){
                result = "";
            }

            Date dateover = new Date();// 获取当前时间

            ParsePosition posbegin = new ParsePosition(0);
            ParsePosition posover = new ParsePosition(0);

            Date dt1 = formatter.parse(formatter.format(datebegin), posbegin);
            Date dt2 = formatter.parse(formatter.format(dateover), posover);

            long l = dt2.getTime() - dt1.getTime();//这是他们相差的毫秒

            map2.put(requestHeader ,"Reseponse:" + l + "ms\n" + result );

        } catch (Exception e) {
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
        return map2;
    }

    public static  Map<String,String> listMakeRequest (String[][] request_header,String ishttps ,boolean isallowDirects) {
        String result = "";
        String url = "";
        String data = null;
        String urlNameString = null;

        if (request_header[0][1].trim().contains("http"))
            url =  request_header[0][1].trim() + request_header[0][3];
        else {
            url = "http://" + request_header[0][1].trim() + request_header[0][3];
        }

        if (url.contains("http://") && ishttps.equals("https")) {
            String[] urllist = url.split("//");
            if (urllist.length > 2) {
                url = "https:";
                for (String s:urllist)
                    System.out.println(s);

                for (int ii = 1; ii < urllist.length; ii++)
                    url = url + "//" + urllist[ii].trim();
            }
            else
                url = "https://" + urllist[1];
        }


        String method = request_header[0][0].trim();
        String param = request_header[0][4].trim(); // param 参数
        try{
            data = request_header[0][5].trim();
            System.out.println(data + "makeRequest");
        }catch (NullPointerException ignored){
            ;
        }

        BufferedReader in = null;
        PrintWriter out = null;
        Map<String, List<String>> map = null;
        String requestHeader = "";
        Map<String,String> map2 = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date datebegin = new Date();// 获取当前时间

        try {
            if ( param.equals("") ) {
                urlNameString = url; //如果param参数没有，那么漏洞url就是url
            } else {
                urlNameString = url + "?" + param; //如果param有参数，那么漏洞url就是url + ? + uri
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性

            Object target = connection;
            if (!target.getClass().equals(sun.net.www.protocol.http.HttpURLConnection.class)) {
                Field field1 = connection.getClass().getDeclaredField("delegate");
                field1.setAccessible(true);
                target = field1.get(connection);
            }
            Field field2 = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("requests");
            field2.setAccessible(true);
            test customMessageHeader = new test();
            field2.set(target, customMessageHeader);

            connection.setConnectTimeout(20000);

            connection.setReadTimeout(20000);

            connection.setInstanceFollowRedirects(isallowDirects); // 判断跳转条件，默认为true

            for(int i=0;i < request_header[1].length;i++)
            {
                if (request_header[1][i] != null ) {
                    String[] header_single = request_header[1][i].split(": ");
                    System.out.println("header_single[0]" + header_single[0]);
                    if (header_single[0].contains(":"))
                        header_single[0] = header_single[0].substring(0,header_single[0].length()-1);
                    try{
                        connection.setRequestProperty(header_single[0], header_single[1]);
                    }catch (ArrayIndexOutOfBoundsException e){ // 捕捉当请求头参数为空的时候
                        connection.setRequestProperty(header_single[0], "");
                    }
                }
            }

            // 如果为POST，即方法设置为POST
            if (method.equals("POST")) {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }else if (method.equals("PUT")) {
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                out.print(data);
                // flush输出流的缓冲
                out.flush();
            }

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
//            try{
//                try{
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
//                }catch (NullPointerException | IOException e){
//                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
//                }
//            }catch (NullPointerException | ZipException ignored){


//                //测试
//                byte[] getData = null;
//                InputStream inputStream;
//
//                try{
//                    inputStream = connection.getInputStream();
//                    getData  = readInputStream(connection.getInputStream());
//                }catch (NullPointerException | IOException e){
//                    inputStream = connection.getErrorStream();
//                    getData  = readInputStream(connection.getErrorStream());
//                }
//
//                try{
//                    inputStream.read(getData);
//                    String str = new String(getData);
//                    InputStream is = new ByteArrayInputStream(str.getBytes());
//                    BufferedReader ins = new BufferedReader(new InputStreamReader(is));
//
//                    String line;
//                    while ((line = ins.readLine()) != null) {
//                        result += line + '\n';
//                    }}
//                catch (NullPointerException e){
//                    result = "";
//                }

//                ————————开始
            // 获取响应头
            map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                requestHeader = requestHeader + key + ":" + map.get(key)+'\n';
//                System.out.println("requestHeader" + requestHeader);
            }

            try {
                try {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    // 解压内容
                    if (requestHeader.contains("Content-Encoding:[gzip]")){
                        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                    }


//                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso8859-1"));
                } catch (NullPointerException | IOException e) {
                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
                    // 解压内容
                    if (requestHeader.contains("Content-Encoding:[gzip]")){
                        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getErrorStream())));
                    }
//                    in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                }
            }catch (Exception e)
            {
                try{
                    in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                }catch (NullPointerException | IOException ee){
                    try {
                        in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }catch (NullPointerException eee){
                        in = null;
                    }
                }
            }

//            }



//            ——————————————结束
            String line ;
            try{while ((line = in.readLine()) != null) {
                result += line + '\n';
//                System.out.println("result" + result);
            }}catch (NullPointerException e){
                result = "";
            }


            Date dateover = new Date();// 获取当前时间

            ParsePosition posbegin = new ParsePosition(0);
            ParsePosition posover = new ParsePosition(0);

            Date dt1 = formatter.parse(formatter.format(datebegin), posbegin);
            Date dt2 = formatter.parse(formatter.format(dateover), posover);

            long l = dt2.getTime() - dt1.getTime();//这是他们相差的毫秒

            map2.put(requestHeader ,"Reseponse:" + l + "ms\n" + result );

        } catch (Exception e) {
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
        return map2;
    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }

        bos.close();
        }
        catch (NullPointerException e){
            ;
        }
        return bos.toByteArray();
    }
}
