package main.RequestPoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static main.RequestPoc.Readfile.exptoexp;
import static main.RequestPoc.Readfile.ymlFiletoexp;
import static main.RequestPoc.makeRequest.inputurl;
import static main.RequestPoc.makeRequest.listMakeRequest;

public class test6test {
    private static List<String> vullist = new ArrayList();
    public test6test() {

    }

    public void getUriList(String pocname,String[] url_list,int conditiontime ,String conditionwords){
        List<Thread> list = new ArrayList<Thread>();
        ExecutorService executor = Executors.newCachedThreadPool();
        try{
            for(int i = 0; i < url_list.length; i++) {
                MyRunnable runnable = new MyRunnable(pocname,conditiontime, conditionwords);
                Thread thread = new Thread(runnable, url_list[i]);
                thread.start();
                list.add(thread);
            }
            try {
                for(Thread thread : list) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            ;
        }
        executor.shutdown();
        while (!executor.isTerminated()){}
    }

    public static class MyRunnable implements Runnable {
        String pocname;
        String conditionwords;
        int conditiontime;
        public MyRunnable(String pocname,int conditiontime ,String conditionwords ){
            this.pocname = pocname;
            this.conditionwords = conditionwords;
            this.conditiontime = conditiontime;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start");
            Url a = new Url();
            a.addList(pocname,Thread.currentThread().getName(),conditiontime,conditionwords);
            System.out.println(Thread.currentThread().getName() + " end");
            System.out.println(vullist);
        }

    }
    public static class Url {

        private static Lock lock = new ReentrantLock();     // ??????????????????
        private static List<String> urlList = new ArrayList();

        public Url() {
        }

        public void addList( String pocname,String url,int conditiontime,String conditionword){
            lock.lock();
            String[] final_list = new String[2];
            String poctoexplist = "";
            String ishttps = "http";
            int a ;
            try {
                // ??????yml?????????explist
                String[] explist = ymlFiletoexp(pocname);

                // ??????explist?????????requestheader
                String[][] requestHeader = exptoexp(explist);

                // ??????requestheader????????????url
                String[][] requestHeader2 = inputurl(requestHeader, url);

                if (url.contains("http://"))
                    ishttps = "http";
                else if(url.contains("https://"))
                    ishttps = "https";

                // ??????????????????url???????????????
                Map<String, String> responseHeaderbody = listMakeRequest(requestHeader2 , ishttps);

                for (String key : responseHeaderbody.keySet()) {
                    try {
                        String[] response = responseHeaderbody.get(key).split("ms\n");

                        final_list[0] = response[0].split(":")[1].split("ms")[0];
                        System.out.println("final_list[0]" +  final_list[0]);
                        final_list[1] = key.replace("null:", "").replace("[", "").replace("]", "") + '\n' + response[1];
                    } catch (ArrayIndexOutOfBoundsException e) { // ??????????????????????????????
                        ;
                    }
                }
            } catch (Exception ex) {
                ;
            } finally {
                try{
                    a = Integer.parseInt(final_list[0]);
                    if (((final_list[1].contains(conditionword))&&(!conditionword.equals("")))  ||  (( a >= conditiontime) && ( a <= 1.5 * conditiontime) && (a != 0))){
                        poctoexplist = url + "``````````````````````````????????????";
                    }else{
                        poctoexplist = url ;
                    }

                }catch (NullPointerException e){
                    poctoexplist = url;
                }catch (NumberFormatException e){
                    a = 0;
                }
                vullist.add(poctoexplist);
                lock.unlock();     // ??????
            }

        }
    }

    public static void main(String[] args) {
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8080");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8080");

//        long start = System.currentTimeMillis();
        int conditiontime = 0;
        String conditionwords = "";
        String pocname = "poc/test/test.yml";
        String[] url_list = {"http://baidu.com","http://hao123.com","http://baidu2.com","http://hao1232.com","https://165.212.109.228","https://165.124.129.66"};
        test6test a = new test6test();
        a.getUriList(pocname, url_list,conditiontime,conditionwords);


    }

}