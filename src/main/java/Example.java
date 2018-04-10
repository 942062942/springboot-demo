import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@EnableAutoConfiguration
public class Example {
	@RequestMapping("/")
    String home() {
        return "Hello World!";
    }
	@RequestMapping(value="/verify",method=RequestMethod.GET)
    String verify(@RequestParam Map<String,Object> params) {
		System.out.println();
		System.err.println(params);
		System.out.println();
		String signature= params.get("signature").toString();//微信加密签名
		String timestamp= params.get("timestamp").toString();//时间戳
		String nonce= params.get("nonce").toString();//随机数
		String echostr= params.get("echostr").toString();//随机字符串
		StringBuilder res= new StringBuilder();
    	String[] arr = new String[] { "gaojunming", timestamp, nonce};  
    	Arrays.sort(arr);//字典顺序排序
    	for (int i = 0; i < arr.length; i++) {
            res.append(arr[i]);
        }
    	String r= res.toString();
    	String sign = DigestUtils.sha1Hex(r);//对排序结果进行sha1加密
    	if(null!= sign && sign.equals(signature)) {
    		return echostr;//校验成功原样返回随机字符串
    	}else {
    		return "failed";
    	}
    }
    public static void main(String[] args) throws Exception {
    	//SpringApplication.run(Example.class, args);
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=8_HNP-PiggdXVAuOgVEv1hBLpO78RfDfw9TgTjqiO2nFNF_zMNBLoxX8ALDtYfP14pGavCZoamJ5EaPypcpWvew7gHtaYojukfsBLrZp5sH7QUgNNIB4PbM2U2sw03HkSA0BJDzSwGJH7mSW7SHBXgAJARLH");
    	JsonObject menu= new JsonObject();
    	JsonArray button= new JsonArray();
    	JsonObject button1= new JsonObject();
    	button1.addProperty("type", "click");
    	button1.addProperty("name", "点我");
    	button1.addProperty("key", "test");
    	button.add(button1);
    	menu.add("button", button);
    	System.out.println(menu);
    	/*List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    	nvps.add(new BasicNameValuePair("username", "vip"));
    	nvps.add(new BasicNameValuePair("password", "secret"));*/
    	//menu.getAsString();未实现，方法内部直接抛出的异常
    	StringEntity param= new StringEntity(menu.toString());
    	//httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    	httpPost.setEntity(param);
    	CloseableHttpResponse response2 = httpclient.execute(httpPost);
    	try {
    	    System.out.println(response2.getStatusLine());
    	    HttpEntity entity2 = response2.getEntity();
    	    // do something useful with the response body
    	    // and ensure it is fully consumed
    	    System.out.println(entity2);
    	    EntityUtils.consume(entity2);
    	} finally {
    	    response2.close();
    	}
    	
    }
}
