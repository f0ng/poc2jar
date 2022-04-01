package main.RequestPoc;

import java.util.*;
import static main.RequestPoc.makeRequest.listMakeRequest;
import sun.net.www.MessageHeader;

public class test extends MessageHeader  {
    @Override
    public synchronized void setIfNotSet(String arg0, String arg1) {
//        System.out.println("hook: " + arg0);
        if ("Content-type".equals(arg0)) {
        return;
    }

        if ("Connection".equals(arg0)) {
        return;
    }

        if ("Accept".equals(arg0)) {
        return;
    }

        super.setIfNotSet(arg0, arg1);
}

    public int nkeys;
    public String[] keys;
    public String[] values;


    public static void main(String[] args) {
        {
            String total = "";
            String str = "param: DontCheckLogin=1&filePath=c:/windows/system32/drivers/etc/hosts";
            for (String strr: java.util.Arrays.copyOfRange(str.split(":"),1,str.split(":").length))

                total = total + ":" + strr.trim();

            System.out.println(total.substring(1));


        }
    }
    public static void listMakeRequest (String[][] request_header, String ishttps) {

    }


}


