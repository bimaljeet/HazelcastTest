package com.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringForCmsWsTests {

	private static ApplicationContext appCtx = null;
	
	private static SpringForCmsWsTests oneInstance;
	static {
		oneInstance = new SpringForCmsWsTests();
		appCtx = new ClassPathXmlApplicationContext(new String[] {
				"testServiceMasterSpringContext.xml"
		});				
	}
	
	public static SpringForCmsWsTests getInstance() {
		return oneInstance;				
	}
	
	
	public Object getBean(String beanId) {
		return appCtx.getBean(beanId);
	}

}
