package cn.bugcatch.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
@ComponentScan//不需要指定扫描路径，原因见下面注释
//通常建议将应用的main类放到其他类所在包的顶层(root package)，并将@EnableAutoConfiguration注解到你的main类上，这样就隐式地定义了一个基础的包搜索路径
@EnableAutoConfiguration
@Profile("dev")//声明此配置类文件只有在dev Profile被激活时生效，被此注解标注的类相当于application-{profile}.properties属性文件(此处只是demo，实际使用应放在特定配置类文件中，比如jdbc配置信息)
//@Configuration
public class Application {
	/**
	 * 注意：spring boot项目只能存在一个main类，否则打jar包将失败
	 */
    public static void main(String[] args) throws Exception {
    	SpringApplication app = new SpringApplication(Application.class);
    	app.setBannerMode(Banner.Mode.OFF);//关闭banner
    	Map<String,Object> properties= new HashMap<>();//相当于application.properties
    	properties.put("name", "gaojunming");
    	//properties.put("trace", true);
    	app.setDefaultProperties(properties);
    	//app.setAdditionalProfiles("dev");//相当于application.properties中的spring.profiles.active
    	app.run(args);
    }
}
