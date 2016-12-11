package netgloo.com.java.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;

/**
 * 
 * @author Miloš Davitković
 *
 */
@Controller
public class TimeFn {

	public TimeFn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Instant fromUnixToInstant(long unix) {
		Instant instant = Instant.ofEpochSecond(unix);
		return instant;
	}
	
	/**
	 * 
	 * @param instant An Instant represents a timestamp in Java 8.
	 * @return
	 */
	public Date fromInstantToDate(Instant instant) {
		Date date = java.util.Date.from( instant );
		return date;
	}
	
	/**
	 * With the static Date.from() method you can convert an Instant to a java.util.Date instance.
	 * @param unix
	 * @return
	 */
	public Date fromUnixToDate(long unix) {
		Date date = Date.from( Instant.ofEpochSecond( unix ) );
		return date;
	}
	
	/**
	 * Date's constructor expects the timeStamp value to be in milliseconds. 
	 * Multiply your timestamp's value with 1000, then pass is to the constructor.
	 * @param unix
	 * @return
	 */
	public Date fromUnixToDate2(long unix) {
		Date date = new Date ();
		date.setTime((long)unix*1000);
		return date;
	}
	
	/**
	 * 
	 * @param unix
	 * @param format - MM.dd.yyyy hh:mm:ss or something like that
	 * @return
	 */
	public String fromUnixToSpecificString(long unix, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(unix);
		return date;
	}
	
	/**
	 * 
	 * @param unix_timestamp
	 * @return
	 * @throws ParseException
	 */
	public String fromUnixToStringDefault(String unix_timestamp) throws ParseException {    
	    long timestamp = Long.parseLong(unix_timestamp) * 1000;

	    SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm:ss", Locale.US);
	    String date = sdf.format(timestamp);

	    return date.toString();
	}

}
