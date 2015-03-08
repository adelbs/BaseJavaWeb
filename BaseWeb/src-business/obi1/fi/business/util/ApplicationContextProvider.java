package obi1.fi.business.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class ApplicationContextProvider implements ApplicationContextAware {
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		AppContext.setApplicationContext(context);
	}
}
