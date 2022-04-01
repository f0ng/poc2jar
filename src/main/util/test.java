package main.util;

public class test {

    public static void main(String[] args) {
        String[][] testlists = new String[4][3];
        testlists[0][0] = "1";
        testlists[1][0] = "9";
        testlists[2][0] = "2";

        testlists[0][1] = "1";
        testlists[1][1] = "9";
        testlists[2][1] = "2";
        for (int i = 0; i < testlists.length; i++)
            for (int j = 0; j < testlists.length - 1; j++)
                if (testlists[i][0] != null && testlists[i + 1][0] != null) {
                    System.out.println(Integer.parseInt(testlists[i][1]));
                    System.out.println(Integer.parseInt(testlists[i+1][1]));
                    if (Integer.parseInt(testlists[i][1]) > Integer.parseInt(testlists[i + 1][1])) {
                        String[][] mid = new String[1][3];
                        mid[0][0] = testlists[i][0];
                        mid[0][1] = testlists[i][1];
                        mid[0][2] = testlists[i][2];
                        testlists[i][0] = testlists[i + 1][0];
                        testlists[i][1] = testlists[i + 1][1];
                        testlists[i][2] = testlists[i + 1][2];
                        testlists[i + 1][0] = mid[0][0];
                        testlists[i + 1][1] = mid[0][1];
                        testlists[i + 1][2] = mid[0][2];
                    }
                }
        System.out.println("666");
        for (int i = 0; i < testlists.length; i++)
            System.out.println(testlists[i][1]);
    }
}
