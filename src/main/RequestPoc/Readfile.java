package main.RequestPoc;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.net.ssl.*;
import main.Main;
import main.Poc2ExpguiController;
import main.RequestPoc.makeRequest.*;

import static main.RequestPoc.makeRequest.listMakeRequest;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.yaml.snakeyaml.Yaml;


// 三个函数
// 第一个函数，将请求读取成数组，所有的数据信息放置在数组里
// 第二个函数，读取yml文件转为exp
// 第三个函数，读取数据包、响应包条件、exp名称，exp描述为yml文件

public class Readfile {
    // 解决https的问题 24-59行
    // https://gist.github.com/aembleton/889392

    static {
        try {
            Poc2ExpguiController.disableSSLCertificateChecking();
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
    // 修改的截止

    public static void main(String[] args) throws FileNotFoundException {


    modifyFileContent("src/main/poc/test/1.yml","expdescribe","111111expn3ame22243431113哈哈3333" );

    }

    // 读取yaml文件，返回为请求的字符数组，
    public static String[] ymlFiletoexp(String filename) throws FileNotFoundException {
        String exp[] = new String[100];
        String val = null;
        int i = 0;
        String total = "{";

        Yaml yaml = new Yaml();
        File dumpFile = new File(filename);
        //获取test.yaml文件中的配置数据，然后转换为obj
        Object load = yaml.load(new FileInputStream(dumpFile));

        Map<String, String> map = (Map<String, String>) yaml.load(new FileInputStream(dumpFile));

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            try {
                val = entry.getValue().toString();
                if (key.contains("others")) {
//                    // 旧版本
//                    System.out.println("val为" + val);
//                    // todo 有些参数可能有=符号
//                    val = entry.getValue().toString().replace("=", ": ");
// val = {User-Agent=a, like, Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8}
                    // 新版本

                    String[] vallist = val.replace("{","").replace("}","").replace(" like","like").replace(" deflate","deflate").replace(" image","image").replace(" *","*").split(", ");
                    for (String str : vallist)

                        total = total + str.replaceFirst("=",": ") + ", ";

                    total = total.substring(0,total.length()-2);
                    val = total + "}";
                    System.out.println("val为"+ val);

                }
                exp[i] = key + ": " + val;
                i++;
            } catch (NullPointerException e) { //  空指针异常
//                e.printStackTrace();
                System.out.println(i);
                if (key.contains("data"))
                exp[i++] = "data: ";
                else if(key.contains("param"))
                exp[i++] = "param: ";
            }
        }
            return exp;
        }

    // 读取yaml文件返回的字符数组，转换成请求专用双数组
    public static String[][] exptoexp(String[] yamllist) throws FileNotFoundException{
        String exp[][] = new String[2][100] ;
        String[] otherslist = new String[0];
        int i = 0,j = 0 ;
        String total = "";

        for (String str: java.util.Arrays.copyOf(yamllist,6))// java.util.Arrays.copyOf(explist,6) 意思为截取了前六个长度的数组
            if (str != null ) {
                if (str.equals("data: ")) { // 如果data没有数据，不做操作，也不做复制
                    ;
                }else if(str.contains("data: ")){ // 如果data有数据，那么全部提取出来
                    exp[0][5] = str.split("data: ")[1].replace("\n","\r\n");
                    // todo 当post请求有换行符的时候，yml文件无法准确表示
//                    System.out.println(exp[0][5]);

                }else{
//                System.out.println(str);
                    if ( str.split(":").length > 2 ) {
                        for (String strr: java.util.Arrays.copyOfRange(str.split(":"),1,str.split(":").length))
                            if (strr.endsWith(" "))
                                total = total + ":" + strr;
                            else
                                total = total + ":" + strr.trim();

                        // 去除total首部的:符号
                        exp[0][i] = total.substring(1);

                    }
                    else
                    exp[0][i] = str.split(":")[1].trim();
                    System.out.println("exp0i" + exp[0][i]);
                    i++;
            }
            }


        // todo 当请求头里有逗号参数即(,)的时候可能会报错，这里我只能采用replace空格+参数为参数来临时解决,参数目前为like、deflate、image、*
        otherslist = yamllist[6].replace("{","").replace("}","").replace(" like","like").replace(" deflate","deflate").replace(" image","image").replace(", *",",*").split("others:")[1].split(", ");
        // 读取其他参数头

        for (String str: otherslist)

            if (str != null) {
                System.out.println(str);
//                if (str.contains("like")||str.contains("deflate"))
//                    str = " " + str;
                if (str.contains("  "))
                    exp[1][j] = str;
                else
                    exp[1][j] = str.trim();
                j++;
            }

        return exp;
    }

    // 读取yaml文件，返回为 关键字、响应时间、expname、expdescribe
    public static String[] ymlFiletoconditioninformation(String filename) throws FileNotFoundException{
        String exp[] = new String[100];
        int i = 0;
        Yaml yaml = new Yaml();
        File dumpFile = new File(filename);
        //获取test.yaml文件中的配置数据，然后转换为obj
        Object load =yaml.load(new FileInputStream(dumpFile));

        Map<String, String> map =(Map<String, String>)yaml.load(new FileInputStream(dumpFile));

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            try {
                Object val = entry.getValue().toString();

                if (key.contains("condition") || key.contains("expinformation")) {
                    val = entry.getValue().toString();
                    System.out.println("val为" + val);

                    val = entry.getValue().toString().replaceFirst("expname=", "expname:").replaceFirst("expdescribe=", "expdescribe:").replaceFirst("words=","words:").replaceFirst("time=","time:").replaceFirst("allowDirects=","allowDirects:");
                    exp[i] = key + ": " + val;
                    System.out.println("expi为" + exp[i]);
                }
                i++;
            } catch (NullPointerException e) { //捕捉当data为空时，object爆空指针异常
                ;
            }
        }
        return exp;
    }

    // 将数据包保存为yaml文件
    public static boolean datatoymlFile(String[][] request_header, String filename ,String  words,String time ,String expname ,String expdescribe){
        String method = request_header[0][0];
        System.out.println(method);
        String url ;
        String tlsversion = request_header[0][2];
        String uri = request_header[0][3];
        String param = request_header[0][4];
        String data = request_header[0][5];

        request_header[0][1] = "$"; // 将具体的url转换成$变量符号
        url = request_header[0][1];
        File file=new File(filename);

        //  判断文件夹，不存在就创建文件夹
        String[] filedirlist = filename.split("/");
        String dirname = filedirlist[0] + "/" + filedirlist[1] + "/";// + filedirlist[2] + "/" + filedirlist[3] + "/";
        File filedir = new File(dirname);
        System.out.println(dirname);
        if  (!filedir.exists()  && !filedir .isDirectory())
        {
            filedir .mkdir();
        }

        // 判断文件
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter(filename)) {
            // 写入第一个数组
            fileWriter.append("method: " + method +'\n');
            fileWriter.append("url: " + url +'\n');
            fileWriter.append("tlsversion: " + tlsversion +'\n');
            fileWriter.append("uri: " + uri +'\n');
            fileWriter.append("param: |\r\n " + param +'\n');
            // data要加|，是因为下面的数据会识别换行符，要加空格的原因是缩进问题，否则会报错
            fileWriter.append("data: |\r\n " + data.replace("\n","\n ") +'\n');

            // 获取其他请求头 others参数
            fileWriter.append("others: " +'\n');
            for (String str : request_header[1])
            {
                if (str != null) {
                    if (str.contains("*") || str.contains("=")){
                        String[] str_list = str.split(":");
                        str = str_list[0] + ": " +"'" + str_list[1].trim() + "'";
                    }else if(str.endsWith(": ")){
                        String[] str_list = str.split(":");
                        str = str_list[0] + ": " +"'" + str_list[1].trim() + "'";
                    }
                    fileWriter.append("    " + str + '\n');
                }
            }
            fileWriter.append("condition: " +'\n');
            fileWriter.append("    words: " + ">" + "\n          " + words + '\n');
            fileWriter.append("    time: " + time + '\n');

            fileWriter.append("expinformation: " +'\n');
            fileWriter.append("    expname: " + expname + '\n');
            fileWriter.append("    expdescribe: " + expdescribe );
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 将数据包保存为yaml文件
    public static boolean datatoymlFile(String[][] request_header, String filename ,String  words,String time ,String expname ,String expdescribe ,boolean isallowDirects){
        String method = request_header[0][0];
        System.out.println(method);
        String url ;
        String tlsversion = request_header[0][2];
        String uri = request_header[0][3];
        String param = request_header[0][4];
        String data = request_header[0][5];

        request_header[0][1] = "$"; // 将具体的url转换成$变量符号
        url = request_header[0][1];
        File file=new File(filename);

        //  判断文件夹，不存在就创建文件夹
        String[] filedirlist = filename.split("/");
        String dirname = filedirlist[0] + "/" + filedirlist[1] + "/";// + filedirlist[2] + "/" + filedirlist[3] + "/";
        File filedir = new File(dirname);
        System.out.println(dirname);
        if  (!filedir.exists()  && !filedir .isDirectory())
        {
            filedir .mkdir();
        }

        // 判断文件
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter(filename)) {
            // 写入第一个数组
            fileWriter.append("method: " + method +'\n');
            fileWriter.append("url: " + url +'\n');
            fileWriter.append("tlsversion: " + tlsversion +'\n');
            fileWriter.append("uri: " + uri +'\n');
            fileWriter.append("param: |\r\n " + param +'\n');
            // data要加|，是因为下面的数据会识别换行符，要加空格的原因是缩进问题，否则会报错
            fileWriter.append("data: |\r\n " + data.replace("\n","\n ") +'\n');

            // 获取其他请求头 others参数
            fileWriter.append("others: " +'\n');
            for (String str : request_header[1])
            {
                if (str != null) {
                    if (str.contains("*") || str.contains("=")){
                        String[] str_list = str.split(":");
                        str = str_list[0] + ": " +"'" + str_list[1].trim() + "'";
                    }else if(str.endsWith(": ")){
                        String[] str_list = str.split(":");
                        str = str_list[0] + ": " +"'" + str_list[1].trim() + "'";
                    }
                    fileWriter.append("    " + str + '\n');
                }
            }
            fileWriter.append("condition: " +'\n');
            fileWriter.append("    words: " + ">" + "\n          " + words + '\n');
            fileWriter.append("    time: " + time + '\n');
            if (isallowDirects == true){
                System.out.println("允许跳转，不写入");
            }else{
            fileWriter.append("    allowDirects: " + isallowDirects +'\n');}

            fileWriter.append("expinformation: " +'\n');
            fileWriter.append("    expname: " + expname + '\n');
            fileWriter.append("    expdescribe: " + expdescribe );
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 修改yml文件属性，value为参数，value为值
    public static void  modifyFileContent(String fileName, String key, String value) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            String line = null;
            String line2 = "";
            long lastPoint = 0; //记住上一次的偏移量
            while ((line = raf.readLine()) != null) {
                final long ponit = raf.getFilePointer();
                if(line.contains(key+": ")){
                    String[] strr = line.split(": ");
                    String str = strr[0] + ": " + value +'\n' ;
                    System.out.println(str);
                    System.out.println(ponit);
                    if ((line2 = raf.readLine()) !=null){
                        line2 = new String(line2.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        System.out.println(line2);
                    }else{
                        line2 = "";
                    }
                    raf.seek(lastPoint);
                    byte[] bt2 = line2.getBytes();
                    byte[] bt1 = (str.getBytes(StandardCharsets.UTF_8));
                    byte[] bt3 = new byte[bt1.length+bt2.length];

                    System.arraycopy(bt1, 0, bt3, 0, bt1.length);
                    System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
                    raf.write(bt3);
                }
                    lastPoint = ponit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 返回整个header头，包括[请求方法，请求url，请求版本，请求路径，请求参数，请求数据]，以及其他的header头，如Cookie、token等
    public static String[][] readFileByLines(JarEntry jarEntry) throws IOException {
        /*
         * 返回整个header头，包括[请求方法，请求url，请求版本，请求路径，请求参数，请求数据]，以及其他的header头，如Cookie、token等
         *
         * [
         * method 请求方法
         * url 请求的url
         * tlsversion 请求版本
         * path  请求路径
         * param 请求参数
         * data 请求数据，如果请求方法为GET就没有
         * ]
         * [
         * a:b
         * c:d
         * ]
         *
         *
         * */
        JarFile jarPath;
        String jarFile = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        if (jarFile.contains(".jar")){ //判断是否为jar启动的
            jarPath= new JarFile(jarFile);
        }else{ // 非jar启动就直接从jar取test
            jarPath= new JarFile("/Users/f0ngf0ng/SSSSshentou/A-jarjarjarjarjar/minejar/poc2jar.jar");
        }
        String[][] requestheader = new String[2][100];
        InputStream input = jarPath.getInputStream(jarEntry);
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(isr);
        BufferedReader reader = null;
        String method = "";
        String url = "";
        String tlsversion = "";
        String path = "";
        String param = "";
        String Postdata_single = "";
        String Postdata = "";
        String tempString = null;

        // 数字[1]的索引
        int i = 0;
        try {
            int line = 0 ;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = br.readLine()) != null) {
                System.out.println(tempString + "****");
                // 读第一行的时候，读取请求方法、uri、以及http版本
                if (line == 0){
                    System.out.println(line);
                    String[] methoduri = tempString.split(" ");
                    method = methoduri[0];
                    String uri = methoduri[1];
                    System.out.println(uri);
                    if (uri.contains("?")){
                        String[] uripath = uri.split("\\?");
//                        for (int ii = 0 ; ii < uripath.length -1 ; ii ++ )
//                            path += uripath[ii];

                        path = uripath[0];
                        param = uripath[1];
                    }else{
//                        String[] uripath = uri.split("\\?");
//                        for (int ii = 0 ; ii < uripath.length -1 ; ii ++ )
//                            path += uripath[ii];
//                        path = uripath[0];
                        path = uri;
                        param = "";
                    }
                    tlsversion = methoduri[2];
                }

                // 获取请求的url
                if (tempString.contains("Host:") || tempString.contains("host:")) {
                    url = readFiletourl(tempString);
                    System.out.println("HOST" +tempString);
                }

                if (method.equals("POST") && tempString.equals("")) {

                    do{
                        Postdata = Postdata + "\r\n" + Postdata_single;
                        System.out.println(Postdata + "######");
                    }while ((Postdata_single = br.readLine()) !=null);
                    // todo 当请求方法为POST的时候，怎样去取数据
                }

                if (tempString.contains(":") && !tempString.contains("host") && !tempString.contains("Host") && !tempString.contains("HTTP/1") ){
                    requestheader[1][i] = tempString;
                    i++;

                }

                // todo 其他请求的格式转换为数组填入返回值

                line++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        requestheader[0][0] = method ;
        requestheader[0][1] =  url ;
        requestheader[0][2] = tlsversion ;
        requestheader[0][3] = path ;
        System.out.println(requestheader[0][3]);
        requestheader[0][4] = param ;
        requestheader[0][5] = Postdata.replaceAll("^[\r\n]+", ""); //去除左边的换行
        return requestheader;
    }



    // 返回整个header头，包括[请求方法，请求url，请求版本，请求路径，请求参数，请求数据]，以及其他的header头，如Cookie、token等
    public static String[][] readFileByLines(String fileName) {
        /*
        * 返回整个header头，包括[请求方法，请求url，请求版本，请求路径，请求参数，请求数据]，以及其他的header头，如Cookie、token等
        *
        * [
        * method 请求方法
        * url 请求的url
        * tlsversion 请求版本
        * path  请求路径
        * param 请求参数
        * data 请求数据，如果请求方法为GET就没有
        * ]
        * [
        * a:b
        * c:d
        * ]
        *
        *
        * */
        String[][] requestheader = new String[2][100];
        File file = new File(fileName);
        BufferedReader reader = null;
        String method = "";
        String url = "";
        String tlsversion = "";
        String path = "";
        String param = "";
        String Postdata_single = "";
        String Postdata = "";
        String tempString = null;

        // 数字[1]的索引
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 0 ;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                // 读第一行的时候，读取请求方法、uri、以及http版本
                if (line == 0){
                    String[] methoduri = tempString.split(" ");
                    method = methoduri[0];
                    String uri = methoduri[1];
                    System.out.println(uri);
                    if (uri.contains("?")){
                        String[] uripath = uri.split("\\?");
//                        for (int ii = 0 ; ii < uripath.length -1 ; ii ++ )
//                            path += uripath[ii];

                        path = uripath[0];
                        param = uripath[1];
                    }else{
//                        String[] uripath = uri.split("\\?");
//                        for (int ii = 0 ; ii < uripath.length -1 ; ii ++ )
//                            path += uripath[ii];
//                        path = uripath[0];
                        path = uri;
                        param = "";
                    }
                    tlsversion = methoduri[2];
                }

                // 获取请求的url
                if (tempString.contains("Host:") || tempString.contains("host:")) {
                    url = readFiletourl(tempString);
                    System.out.println("HOST" +tempString);
                }

                if (method.equals("POST") && tempString.equals("")) {
//                    do{
//                        Postdata = Postdata + "\r\n" + Postdata_single;
//                    }while ((Postdata_single = reader.readLine()) !=null);
                    while ((Postdata_single = reader.readLine()) !=null){
                        Postdata = Postdata + "\r\n" + Postdata_single;
                        System.out.println(Postdata + "data");
                    }
                    // todo 当请求方法为POST的时候，怎样去取数据
                }

                if (tempString.contains(":") && !tempString.contains("host") && !tempString.contains("Host") && !tempString.contains("HTTP/1") ){
                    requestheader[1][i] = tempString;
                    i++;

                }

                // todo 其他请求的格式转换为数组填入返回值

                line++;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        requestheader[0][0] = method ;
        requestheader[0][1] =  url ;
        requestheader[0][2] = tlsversion ;
        requestheader[0][3] = path ;
        requestheader[0][4] = param ;
        requestheader[0][5] = Postdata.replaceAll("^[\r\n]+", ""); //去除左边的换行
        return requestheader;
    }

//    // 读取HOST属性返回url
//    public static String[] readurltourllist(String host){
//        String[] urllist = new String[10000];
//        return urllist;
//
//    }

    // 读取HOST属性返回url
    public static String readFiletourl(String host){

        String url = "";
        String[] urltohost = host.split(":");
        for (String str:urltohost)
            System.out.println(str);
        if (urltohost.length > 2)
            url = urltohost[1].trim() + ":" + urltohost[2].trim(); // 针对于含有端口的host
        else
            url = urltohost[1].trim(); // 正常host
        return url;

    }


    // 增加命令至文件内
    public static void addCmdlists(String key,String value){

    }
}
