package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.util.StageManager;


public class ProxyController {

    @FXML
    public TextField mTextField66;

    @FXML
    private TextField mTextField62;


//    public void initialize() throws IOException {
//        Poc2ExpguiController index=(Poc2ExpguiController) StageManager.CONTROLLER.get("Poc2ExpguiController");
//
//        String ipporthttp = mTextField66.getText().trim();
//        if (ipporthttp.equals("")){
//            mTextField66.setText("127.0.0.1:8080");
//        }else{
//            String index_ipport = index.mLabeltest.getText().trim();
//            String[] index_ipport_lists = index_ipport.split("ip:port为:");
//            mTextField66.setText(index_ipport_lists[1]);
//        }
//    }
/**
  * 传递数据至主窗口
  * @param event
  */
        public void openThrid(ActionEvent event) {
            Poc2ExpguiController index=(Poc2ExpguiController) StageManager.CONTROLLER.get("Poc2ExpguiController");

            String ipporthttp = mTextField66.getText().trim();

            String ipportsocks = mTextField62.getText().trim();

//            System.out.println(ipporthttp);
//            System.out.println(ipportsocks);

            if (!ipporthttp.equals("") ){
                index.mLabeltest.setText("现在是http/https代理，ip:port为:" + ipporthttp);
            } else if (!ipportsocks.equals(""))
                index.mLabeltest.setText("现在是socks代理，ip:port为:" + ipportsocks);
            else
                index.mLabeltest.setText("代理地址为空" + ipportsocks);

}


}
 
 