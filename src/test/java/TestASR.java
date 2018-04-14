//package asr;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class TestASR {

    public class HttpRequest {
        public String post(String url, byte[] data) {
            DataOutputStream out = null;
            BufferedReader in = null;
            String result = "";
            try {
                URL realUrl = new URL(url);
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                out = new DataOutputStream(conn.getOutputStream());
                out.write(data);
                out.flush();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"gb2312"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                System.out.println("发送 POST 请求出现异常！" + e);
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return result;
        }
    }

    public static String ServerIp = "123.56.227.97"; //云端
    public static int ServerPort = 80;

    public static String generate(String sessionId, int idx, boolean last) {
        String url = "http://" + ServerIp + ":" + ServerPort
                + "/asr.php?sid=" + sessionId
                + "&username=" + "tkw_program"
                + "&auth=aaaaa"
                + "&idx=" + idx
                + "&islast=" + ((last == true) ? "1" : "0")
                + "&did=" + "ccccc"
                + "&rectype=" + 1
                + "&etype=" + "stream"
                + "&dtype=unknown";
        return url;
    }

    public static void TestASRMultiPost(String filename) throws IOException {
        int total = 0, index = 1;
        String sessionId = "TestASR1234";

        byte[] buffer = new byte[1024];

        InputStream in = new FileInputStream(filename);
        boolean last = false;
        HttpRequest request = new TestASR().new HttpRequest();
        while (!last) { // 仅仅测试发送90次，每次1024字节语音
            int n = in.read(buffer);

            total += n;
            //System.out.println(n+" "+total);
            last = (total >= 90 * 1024);
            if (n < 0) {
                last = true;
            }
            String url = generate(sessionId, index++, last);
            //System.out.println(url);
            String response = request.post(url, buffer);
            System.out.println(response);
        }
    }

    public static void TestASROnePost(String filename) throws IOException {
        String sessionId = "TestASR12345";

        byte[] buffer = new byte[90 * 1024];  // 一次性发送90k语音
        InputStream in = new FileInputStream(filename);
        in.read(buffer);

        String url = generate(sessionId, 1, true);
        Arrays.toString(buffer);
        HttpRequest request = new TestASR().new HttpRequest();
        String response = request.post(url, buffer);
        System.out.println(response);
    }

    /*public static void main(String[] args) throws IOException {
        //String voicePath = "data/testASR.wav";
    	//String voicePath = "src/main/resources/testASR.wav";
    	String voicePath = "src/main/resources/save_11nrs9r2ecGlJHyjnKRjhYxCy-y5EZeGDl6Bpo9WXq1gs9J_Kg6TnHCGoFxildS0.mp3";
        TestASROnePost(voicePath);
//        TestASRMultiPost(voicePath);
    }*/
}
