package obi1.fi.business.util;

import org.springframework.context.ApplicationContext;

public final class AppContext {

	private static ApplicationContext context;

	private AppContext() {
		
	}

	public static void setApplicationContext(ApplicationContext context) {
		AppContext.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}
}
