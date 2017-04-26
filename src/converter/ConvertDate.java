package converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ConvertDate {
	public static Date StringtoDate(String data) throws ParseException{
		//Converte String para Date
		java.util.Date date = null; 
		if (data == null || data.equals("")) {
			date = null;  
		}
		else {
			try {  
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = (formatter.parse(data));
			} catch (ParseException e) {              
				throw e;  
			}  
		}
		return date;
	}

	public static String DatetoString(Date data){
		//Converte Date para String
		String date = null; 
		if (data == null || data.equals("")) {
			date = null;  
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (formatter.format(data));  
		}
		return date;
	}
}
