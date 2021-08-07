package net.timxekhach.utility;

import java.util.Calendar;
import java.util.Date;

public class XeDateUtils {

  public static Integer getDayOfWeek(Date input){
    if ( input == null )
      return null;

    Calendar c = Calendar.getInstance();
    c.setTime(input);
    return c.get(Calendar.DAY_OF_WEEK);
  }

}
