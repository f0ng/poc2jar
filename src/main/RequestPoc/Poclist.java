package main.RequestPoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static main.RequestPoc.Readfile.exptoexp;
import static main.RequestPoc.Readfile.ymlFiletoexp;
import static main.RequestPoc.makeRequest.listMakeRequest;
import static main.RequestPoc.makeRequest.inputurl;

public class Poclist {



    // 第一个函数，获取所有的poc文件夹名，如poc/tongda、poc/fanwei等
    // 第二个函数，根据选中的属性，比如选中了tongda文件夹，来出现tongda文件夹下面的所有poc名称
    // 第三个函数，根据选中的poc来发起exp请求

    public static void main(String[] args) throws FileNotFoundException {
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8080");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8080");


        

    }

    // 第一个函数，获取所有的poc文件夹名，如poc/tongda、poc/fanwei等
    public static ObservableList<String> dirnametlistview1(String dirname) throws FileNotFoundException {
        ObservableList<String> strList = FXCollections.observableArrayList() ;
        File file = new File(dirname);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    String[] arr = file2.getAbsolutePath().split("/");
                    int next = arr.length-1;
                    strList.add(file2.getAbsolutePath().split("/")[next]);
                }
            }

        } else {
            System.out.println("文件不存在!");
        }
        return strList;

    }


    // 第二个函数，根据选中的属性，比如选中了tongda文件夹，来出现tongda文件夹下面的所有poc名称
    public static ObservableList<String> dirnametlistview2(String dirname) throws FileNotFoundException {
        ObservableList<String> strList = FXCollections.observableArrayList() ;
        File file = new File(dirname);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (!file2.isDirectory() && !file2.getAbsolutePath().contains(".DS_Store")) {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    String[] arr = file2.getAbsolutePath().split("/");

                    int next = arr.length-1;
                    strList.add(file2.getAbsolutePath().split("/")[next]);
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return strList;
    }


    // 第三个函数，根据选中的poc来发起exp请求
    public static String[] poctoexp(String pocname ,String url,String ishttps) throws FileNotFoundException {
        String[] poctoexplist = new String[2];
        String responsetime = null;
        String responseheaderbody = null;
        // 读取yml文件为explist
        String[] explist = ymlFiletoexp(pocname);
        System.out.println("explist");
//        for (String strr : explist)
//            if (strr != null)
//                System.out.println(strr);

//         根据explist转换为requestheader
        String[][] requestHeader = exptoexp(explist);

//        for (String[] str : requestHeader)
//            for (String strr : str)
//                if (strr != null)
//                    System.out.println(strr);

        // 根据requestheader替换目标url
        String[][] requestHeader2 = inputurl(requestHeader,url);
        System.out.println("requestHeader2");
        for (String[] str : requestHeader2)
            for (String strr : str)
                if (strr != null)
                    System.out.println(strr);


        // 根据替换后的url来发起请求
        Map<String,String> responseHeaderbody = listMakeRequest(requestHeader2,ishttps);
        for (String key : responseHeaderbody.keySet()) {

        try {

                String[] response = responseHeaderbody.get(key).split("ms\n");
                responsetime = response[0].split(":")[1].split("ms")[0];
                responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n' + response[1];
//            System.out.println(responseheaderbody);
        }catch (ArrayIndexOutOfBoundsException e){ // 捕捉响应为空的请求体
            responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n';
        }
        }
        poctoexplist[0] = responsetime;
        poctoexplist[1] = responseheaderbody;
        System.out.println("responsetime" + responsetime);
        System.out.println("responseheaderbody" + responseheaderbody);

        return poctoexplist;
    }


}

