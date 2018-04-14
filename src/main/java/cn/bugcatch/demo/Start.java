package cn.bugcatch.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class Start implements CommandLineRunner {
	@Value("${hello}${name} ${haha}")
	private String name;
	@Override
	public void run(String... args) throws Exception {
		System.err.println(name+" start:"+Arrays.toString(args));
	}

}
