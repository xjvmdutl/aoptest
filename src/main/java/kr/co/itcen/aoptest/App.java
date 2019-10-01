package kr.co.itcen.aoptest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		ApplicationContext ac=
				new ClassPathXmlApplicationContext("config/applicationContext.xml");
		ProductService ps=ac.getBean(ProductService.class);
		ProductVo vo=ps.find("TV");
		System.out.println(vo);
		((ConfigurableApplicationContext)ac).close();
	}
}
