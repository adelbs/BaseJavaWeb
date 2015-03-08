package obi1.fi.common.util;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obi1.fi.common.exception.FiException;

import org.apache.commons.io.FileUtils;

public final class FiUtils {

	public static final String DEFAULT_TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	private FiUtils() { }
	
	public static boolean isEmpty(String val) {
		return val == null || "".equals(val.trim());
	}

	public static String getNumber2Digits(Integer val) {
		String result = "";
		if (val < 10) {
			result += "0" + val;
		}
		else {
			result += val;
		}
		
		return result;
	}

	public static void cleanupDir(String path) throws IOException {
		File dir = new File(path);
		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					FileUtils.forceDelete(file);
				}
				else {
					FileUtils.deleteDirectory(file);
				}
			}
		}
	}
	
	public static void redirectPage(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			final String scheme = request.getScheme().concat("://");
			final String hostname = request.getRequestURL().toString().split(scheme)[1].split("/")[0];
			final String url = scheme + hostname + request.getContextPath() + request.getServletPath() + page;
			response.sendRedirect(url);
		}
		catch (Exception e) {
			throw new FiException(e);
		}
	}

	public static String timestampToString(Timestamp date) {
		return new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT).format(date);
	}
	
	public static Timestamp stringToTimestamp(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
		return new Timestamp(format.parse(date).getTime());
	}
	
	public static long strDateToLong(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
		return format.parse(date).getTime();
	}
}
