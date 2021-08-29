
package de.di.dokinform.util;

import java.text.SimpleDateFormat;

/**
 *
 * @author A. Sopicki
 */
public class DateFormat {

  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

  /**
   * Returns the data as ISO date format yyyyMMdd
   * param d
   * return
   */
  public static String toIsoDate(java.util.Date d) {
    return formatter.format(d);
  }

  /**
   * Format a given date to ddmmyyyy or yymmdd if shortFormat is true.
   * @param d the date to format as a string
   * @param shortFormat return date as yymmdd format if true
   * @return a formatted string of the given date
   */
  public static String formatDate(java.util.Date d, boolean shortFormat) {
    if (shortFormat) {
      return String.format("%1$ty%1$tm%1$td", d);
    }

    return String.format("%1$td%1$tm%1$tY", d);
  }

  public static String toUserDate(java.util.Date d) {
    return String.format("%1$td.%1$tm.%1$tY", d);
  }

  public static java.util.Date fromIsoString(String isoDate) throws java.text.ParseException {
    return formatter.parse(isoDate);
  }
}
