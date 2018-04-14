package cn.bugcatch.demo.web;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
	@Value("${name}")
	private String name;
	@RequestMapping("/")
    String home() {
        return "hello "+name;
    }
	@RequestMapping("/index")
    String index() {
        return "index";
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
}
