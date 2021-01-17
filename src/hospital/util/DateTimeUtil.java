package hospital.util;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 * 
 */
public class DateTimeUtil {

	/** The date pattern that is used for conversion. Change as you wish. */
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** The date formatter. */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	/**
	 * Returns the given date as a well formatted String. The above defined
	 * {@link DateTimeUtil#DATE_PATTERN} is used.
	 * 
	 * @param date the date to be returned as a string
	 * @return formatted string
	 */
	public static String format(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	/**
	 * Converts a String in the format of the defined {@link DateTimeUtil#DATE_PATTERN}
	 * to a {@link LocalDateTime} object.
	 * 
	 * Returns null if the String could not be converted.
	 * @see {@link LocalDateTime#parse(CharSequence, DateTimeFormatter)}
	 * @param dateString the date as String
	 * @return the date object or null if it could not be converted
	 */
	public static LocalDateTime parse(String dateString) {
		try {
			// ::from gives reference of localdatetime
			return DATE_FORMATTER.parse(dateString, LocalDateTime::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * Checks the String whether it is a valid date.
	 * 
	 * @param dateString
	 * @return true if the String is a valid date
	 */
	public static boolean validDate(String dateString) {
		// Try to parse the String.
		return DateTimeUtil.parse(dateString) != null;
	}
}