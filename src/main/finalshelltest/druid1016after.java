package main.finalshelltest;

import com.alibaba.druid.filter.config.ConfigTools;

public class druid1016after {
    public static void main(String[] args) throws Exception {

//        // 密码明文
//        String password = "1q2w3e4r";
//
//        System.out.println("密码[ " + password + " ]的加密信息如下：\n");
//
//        String[] keyPair = ConfigTools.genKeyPair(512);
//        // 私钥
//        String privateKey = keyPair[0];
//        // 公钥
//        String publicKey = keyPair[1];
//        // 用私钥加密后的密文
//        password = ConfigTools.encrypt(privateKey, password);
//
//        System.out.println("privateKey:" + privateKey);
//        System.out.println("publicKey:" + publicKey);
//        System.out.println("password:" + password);
//        String decryptPassword = ConfigTools.decrypt(publicKey, password);
//        System.out.println("decryptPassword：" + decryptPassword);


//        String cipherText = "HShsmthuOQtUyzlXNu2f8prK1/NEI/RcKjTWSFg1mBI/bFchRYJs9p32etYEEe9UsDDk8jKDsm6/RP5Yr+s8Cg==";

            String publickey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhmJn/IljtzrVJRdMFmCdMMTHzf7lnIRH5KgZ9jMdmK1ZeTO39fqaCBIvA6eE3BwX7inS9w9UejKku5D6TJDoUCAwEAAQ==";
            String password = "M9gTODvsTFcUFMuPJHOb4JMrVKwHHrh8tp2iEoPQ7F85t5ez4ZGe0l/GRMAkidVyion7WQch79FCcBHmCvPS9w==";
            System.out.println(ConfigTools.decrypt(publickey, password));
            System.out.println("111");

    }
}
