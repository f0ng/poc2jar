package main.RequestPoc;

import java.io.FileNotFoundException;
import java.util.Map;
import static main.RequestPoc.Readfile.exptoexp;
import static main.RequestPoc.Readfile.ymlFiletoexp;
import static main.RequestPoc.makeRequest.inputurl;
import static main.RequestPoc.makeRequest.listMakeRequest;

public class test4 {
    public static void main(String[] args) {

    }

    // 第四个函数，根据选中的poc来发起exp请求，返回为url是否有漏洞的数组，即[url1 vul，url2 notvul………………]
    public static String[] poctoexp(String pocname ,String[] urllist,int conditiontime,String conditionwords) throws FileNotFoundException {
        String[] poctoexplist = new String[10000];
        int i = 0,a = 0;
        String responsetime = null;
        String responseheaderbody = null;
        // 读取yml文件为explist
        String[] explist = ymlFiletoexp(pocname);

        //  根据explist转换为requestheader
        String[][] requestHeader = exptoexp(explist);

        for (String url : urllist) {
            // 根据requestheader替换目标url
            String[][] requestHeader2 = inputurl(requestHeader, url);

            // 根据替换后的url来发起请求
            Map<String, String> responseHeaderbody = listMakeRequest(requestHeader2);
            for (String key : responseHeaderbody.keySet()) {
                try {
                    String[] response = responseHeaderbody.get(key).split("\n");
                    responsetime = response[0].split(":")[1].split("ms")[0];
                    responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n' + response[1];
                } catch (ArrayIndexOutOfBoundsException e) { // 捕捉响应为空的请求体
                    responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n';
                }
            }

            a = Integer.parseInt(responsetime);

            if ((responseheaderbody.contains(conditionwords))&&(!conditionwords.equals(""))  ||  (( a >= conditiontime) && ( a <= 1.5 * conditiontime))){
                poctoexplist[i] = url + "``````````````````````````存在漏洞";
            }else{
                poctoexplist[i] = url ;
            }
            i++;

        }


        return poctoexplist;
    }
}
