package main.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Usualcmd {

    public static void main(String[] args) {
        String[] list = readFileByLines2("cmdlists.txt");

        for (String str : list)
            if (str != null && !str.equals(""))
                System.out.println(str);

        Map<String,String> usualcmdlist = usualcmdlist(list);

        System.out.println(keytovalue(usualcmdlist,"linux写文件"));


//        for (Map.Entry<String, String> entry : usualcmdlist.entrySet()) {
//            if (entry.getKey().equals("根据进程查找进程文件"))
//            System.out.println(entry.getKey() + '\n' + entry.getValue());
//        }

//        ObservableList<String> a = readFilestokey("cmdlists.txt");
//       for (String str : a)
//           System.out.println(str);
    }

    public static String keytovalue ( Map<String,String> usualcmdlist,String key){
        String value = "";
        for (Map.Entry<String, String> entry : usualcmdlist.entrySet()) {
            if (entry.getKey().equals(key))
                 value = entry.getValue();
        }
        return value;
    }

    public static ObservableList<String> readFilestokey(String fileName) {
        ObservableList<String> strList = FXCollections.observableArrayList();
        String[] list = readFileByLines2(fileName);
        Map<String,String> usualcmdlist = usualcmdlist(list);
        int i = 0;
        for (Map.Entry<String, String> entry : usualcmdlist.entrySet()) {
            strList.add(entry.getKey());
            i++;
        }
        return strList;
    }

    public static Map<String,String> usualcmdlist(String[] list) {

        Map<String,String> cmdlist = new TreeMap<String, String>();
        for (String str: list)
            if (str!=null && !str.equals("")){
                String[] list_single = str.split("::::");
                if (list_single[1].contains("、")){
                    String[] list1 = list_single[1].split("、");
                    list_single[1] = "";
                    for (int i=0;i<list1.length;i++)
                        list_single[1] += list1[i] + '\n';
                }
                cmdlist.put(list_single[0],list_single[1]);
            }

        return cmdlist;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String[] readFileByLines2(String fileName) {
        String[] resultlist = new String[10000];
        File file = new File(fileName);
        BufferedReader reader = null;
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = "";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null )
            {
                resultlist[i] = tempString;
                i++;
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
}
