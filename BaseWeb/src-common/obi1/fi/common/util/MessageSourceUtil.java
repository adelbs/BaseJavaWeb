package obi1.fi.common.util;

import java.util.Locale;

import obi1.fi.business.util.AppContext;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class MessageSourceUtil {
	
	private static MessageSource message;
	
	private static Locale locale = LocaleContextHolder.getLocale();

	private MessageSourceUtil() { }
	
	public static String getMessage(String keyProperties) {
		return getMessage(keyProperties, locale);
	}

	public static String getMessage(String keyProperties, Locale locale) {
		
		String strMessage = "";
		
		try {
			if (message == null) {
				message = AppContext.getApplicationContext().getBean(MessageSource.class);
			}

			strMessage = message.getMessage(keyProperties, null, locale);
		}
		catch (Exception x) {
			strMessage = keyProperties;
		}
		
		return strMessage;
	}

	public static String[] getMessage(String... keyProperties) {
		String[] result = new String[keyProperties.length];
		for (int i = 0; i < keyProperties.length; i++) {
			result[i] = getMessage(keyProperties[i]);
		} 
		return result;	
	}

}
