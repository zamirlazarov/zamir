package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateEvents {
	// TODO: Auto-generated Javadoc
	/**
	 * The Class DateEvents.
	 */

	/** The format. */
	private static String format = "yyyy-MM-dd";

	public static long getDateFromToday(int days) {
		long result = System.currentTimeMillis() + days * 24 * 60 * 60 * 1000;
		return result;
	}

	public static String intToDate(int day, int month, int year) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, day);
		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		String startDate = dateFormat.format(date);

		return startDate;
	}

	public static String formatCurrentTime() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		String startDate = dateFormat.format(date);

		return startDate;
	}

	/**
	 * Format.
	 *
	 * @return the string
	 */
	public static String sqlDateStringToDate(String date) throws Exception {

		SimpleDateFormat fromSql = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat toJavaDate = new SimpleDateFormat(format);
		String reformattedString=null;
		try {
			
			reformattedString = toJavaDate.format(fromSql.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reformattedString;
	}
}