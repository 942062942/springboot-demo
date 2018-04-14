package cn.bugcatch.demo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
@Component
public class Exit implements  DisposableBean{

	@Override
	public void destroy() throws Exception {
		System.err.println("destroy");
	}


}
