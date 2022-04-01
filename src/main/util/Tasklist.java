package main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Tasklist {
    public static void main(String[] args) {
//        Map<String,String> exelist = new HashMap<String, String>();
//
//        String[] exetestlist = readFileByLines("exetest.txt");
//        for (String str : exetestlist)
//            if (str!=null)
//            exelist.put(str.split(": ")[0],str.split(": ")[1]);
//
//        String[] resultlist2 = readFileByLines("src/main/1.txt");
//
//        String finallist = ifexe(resultlist2,exelist);
//        System.out.println(finallist);

//        String[] res = {"1.exe   asdffdsa","   fdsaaf"};
//
//        res = taskexechange(res);
//
//        for (String str : res)
//            System.out.println("str为"  + str);

    }

    public static String ifexe(String[] resultlist2 , Map<String,String> exelist){// resultlist2为输入的
        String total = "",str2 = "";
        for (String str : resultlist2) {
            if (str != null){
                for (Map.Entry<String, String> entry : exelist.entrySet()) {
//                    System.out.println(entry.getKey().replace("\"", ""));
//                    System.out.println(entry.getValue().replace("\"", ""));

//                    System.out.println(str.split("   ")[0].trim() + "66666666");
                    if (str.split("   ")[0].trim().equals(entry.getKey().replace("\"", ""))) {

//                        System.out.println("xxxx" + entry.getValue().replace("\"", ""));
                        total = total + entry.getKey().replace("\"", "") + "->" + entry.getValue().replace("\"", "").replace(",", "") + '\n';
                    }
                }
                // 查找出远程桌面服务的pid
                if (str.contains("TermService")) {
//                    System.out.println(str);
                    for(int i=0;i<str.length();i++){
                        if(str.charAt(i)>=48 && str.charAt(i)<=57) {
                            str2+=str.charAt(i);
                        }
                    }
                }
            }
        }

        total = total + "\n远程桌面pid为" + str2 + '\n' + "netstat -ano|findstr \"" + str2 +"\"\n查找远程端口";
        return total;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String[] readFileByLines(String fileName) {
        String[] resultlist = new String[1000];
        File file = new File(fileName);
        BufferedReader reader = null;
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = "";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null)
            {
                if (tempString.contains("."))
                {
                    resultlist[i] = tempString;
                    i++;
                }else{
                    try {
                        resultlist[i] = tempString;
//                        resultlist[i - 1] = resultlist[i - 1] + tempString.trim();
                    }catch (ArrayIndexOutOfBoundsException ignored){;}
                }
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
        return resultlist;
    }

    public static String[] taskexechange(String[] resultlist) {
        String[] resultlist2 = new String[1000];
        BufferedReader reader = null;
        int i = 0;
        try {
            for (String tempString : resultlist){
            // 一次读入一行，直到读入null为文件结束
            if ((tempString != null))
//
                if (tempString.contains(".")) {
                    resultlist2[i] = tempString;
//                    System.out.println(resultlist2[i]);
                    i++;

                }else{
                    try {
                        resultlist2[i] = tempString;
                        i++;
//                        resultlist2[i - 1] = resultlist2[i - 1] + tempString.trim();

                    }catch (ArrayIndexOutOfBoundsException ignored){;}
                }
            }
        }catch (Exception e)
        {}
        return resultlist2;
    }
}
