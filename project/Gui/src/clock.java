import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class clock extends javax.swing.JFrame{
	public static String getTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String formattedDate1 = df.format(c.getTime());
		return formattedDate1;
		
	}
	public static String getDMY() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(" EE dd MMMM yyyy ");
		String formattedDate1 = df.format(c.getTime());
		return formattedDate1;
		
	}
}