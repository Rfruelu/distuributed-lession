package com.lujia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoMapstructApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DemoMapstructApplication.class, args);

		SpringApplication springApplication=new SpringApplication(DemoMapstructApplication.class);

		/**
		 * 设置隐藏  启动banner
		 */
		//springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

}

