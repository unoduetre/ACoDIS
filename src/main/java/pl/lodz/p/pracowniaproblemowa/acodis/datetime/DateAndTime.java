package pl.lodz.p.pracowniaproblemowa.acodis.datetime;

import java.io.Serializable;
import java.util.Calendar;

public class DateAndTime implements Serializable
{
  protected Calendar calendar = null;

  public DateAndTime()
  {
    calendar = Calendar.getInstance();
  }

  public Integer getYear()
  {
    return calendar.get(Calendar.YEAR);
  }

  public Integer getMonth()
  {
    return calendar.get(Calendar.MONTH);
  }

  public Integer getDayOfMonth()
  {
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public Integer getDayOfWeek()
  {
    return calendar.get(Calendar.DAY_OF_WEEK);
  }

  public Integer getHour()
  {
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  public Integer getMinute()
  {
    return calendar.get(Calendar.MINUTE);
  }

  public Integer getSecond()
  {
    return calendar.get(Calendar.SECOND);
  }
}
