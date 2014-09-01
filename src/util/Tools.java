
package util;

import java.io.File;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import myjava.util.StringFormattedDate;

/**
 * 
 * @author Mouha
 *
 */
public class Tools {
	public static final String DATABASE_DATE_FORMAT_SHORT = "MM/dd/yyyy"; //$NON-NLS-1$
	public static final String DATABASE_FORMAT_SHORT = "yyyy-MM-dd"; //$NON-NLS-1$
	public static final String DATABASE_DATE_FORMAT_LONG  = "yyyy-MM-dd HH:mm:ss"; //$NON-NLS-1$
	public static final String HUMAN_DATE_FORMAT_SHORT    = "dd/MM/yyyy"; //$NON-NLS-1$
	public static final String HUMAN_DATE_FORMAT_LONG     = "dd/MM/yyyy HH:mm:ss"; //$NON-NLS-1$
	public static final String TIME_ONLY     = "HH:mm"; //$NON-NLS-1$


	public void test(){
	}

	/**
	 * Use this only when no comparable timestamp is required.
	 */
	public static String getCurrentDateTime() {
		return Tools.getCurrentDate(HUMAN_DATE_FORMAT_LONG); //$NON-NLS-1$
	}
	
	public static String getTime(Date database){
		
		StringFormattedDate sfd = new StringFormattedDate(DATABASE_DATE_FORMAT_LONG, TIME_ONLY, database.toString());
		return sfd.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return Tools.getCurrentDate(HUMAN_DATE_FORMAT_SHORT); //$NON-NLS-1$
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDateDDMMYYYY() {
		return Tools.getCurrentDate(HUMAN_DATE_FORMAT_SHORT); //$NON-NLS-1$
	}

	/**
	 *
	 */
	public static String getCurrentDate(String format) {
		return new java.text.SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Date ConvertStringToDate(String s){
		SimpleDateFormat sdf = new SimpleDateFormat(HUMAN_DATE_FORMAT_SHORT);
		Date d = null;
		try {
			d = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Time ConvertStringToTime(String s){
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Time time = null;
		try {
			time = (Time) sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String toHumanDate(String databaseDate) {
		StringFormattedDate sfd = new StringFormattedDate(DATABASE_DATE_FORMAT_SHORT, HUMAN_DATE_FORMAT_SHORT, databaseDate);
		return sfd.toString();
	}


	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String toHumanDateFormat(String databaseDate) {
		StringFormattedDate sfd = new StringFormattedDate(DATABASE_FORMAT_SHORT, HUMAN_DATE_FORMAT_SHORT, databaseDate);
		return sfd.toString();
	}

	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String toHumanDateTime(String databaseDate) {

		StringFormattedDate sfd = new StringFormattedDate(DATABASE_DATE_FORMAT_LONG, HUMAN_DATE_FORMAT_LONG, databaseDate);
		
		return sfd.toString();

	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String toDBTime(String s) {
		if (s.length() == 0)
			return ""; //$NON-NLS-1$

		return s + ":00"; //$NON-NLS-1$
	}

	/**
	 * Convert to Int ignoring any exception.
	 */
	public static int toInt(String s, int defaultVal) {
		int v = defaultVal;
		try {
			v = Integer.parseInt(s);
		} catch (Exception e) {            
		}
		return v;
	}

	/**
	 * 
	 * @param dateYMDHMS
	 * @return
	 */
	public static GregorianCalendar createGregorianCalendar(String dateYMDHMS) { 
		GregorianCalendar g = new GregorianCalendar();
		if (dateYMDHMS != null) {
			if (dateYMDHMS.length() == 19) {
				int y = toInt(dateYMDHMS.substring(0, 4), 0);
				int m = toInt(dateYMDHMS.substring(5, 7), 0) - 1;
				int d = toInt(dateYMDHMS.substring(8, 10), 0);
				int h = toInt(dateYMDHMS.substring(11, 13), 0);
				int min = toInt(dateYMDHMS.substring(14, 16), 0);
				int s = toInt(dateYMDHMS.substring(17, 19), 0);            
				g.set(y, m, d, h, min, s);
			}
			else if (dateYMDHMS.length() == 10) {
				int y = toInt(dateYMDHMS.substring(0, 4), 0);
				int m = toInt(dateYMDHMS.substring(5, 7), 0) - 1;
				int d = toInt(dateYMDHMS.substring(8, 10), 0);
				g.set(y, m, d, 0, 0, 0);
			}
		}
		return g;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date) {

		if (date == null) {

			return("");

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(HUMAN_DATE_FORMAT_SHORT);
			return sdf.format(date);
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {

		if (date == null) {

			return("");

		} else {

			SimpleDateFormat sdf = new SimpleDateFormat(HUMAN_DATE_FORMAT_LONG);
			return sdf.format(date);

		}

	}


	/**
	 * 
	 * @param dateNaissance
	 * @param toDay
	 * @return
	 */
	public static int nbJours(String dateNaissance, String toDay)
	{
		Date date1 = ConvertStringToDate(dateNaissance);
		Date date2 = ConvertStringToDate(toDay);

		long nbJours = (date2.getTime() - date1.getTime()) / 86400000;
		int nb = (Math.round(nbJours));


		return nb;
	}
	
	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String getYearForDate(String databaseDate) {
		if(!"".equals(databaseDate)){
			String[] date = databaseDate.split("/");
			return date[2];
		}
		else{
			return "";
		}
	}

    /**
     * 
     * @param databaseDate
     * @return
     */
	public static String getDayForDate(String databaseDate) {
		if(!databaseDate.equals("") && databaseDate!=null){
			String[] date = databaseDate.split("/");
			return date[0];
		}
		else{
			return "";
		}
	}

	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String getMonthForDate(String databaseDate) {
		if(!databaseDate.equals("") && databaseDate!=null){
			String[] date = databaseDate.split("/");
			return date[1];
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String getYearForDateTime(String databaseDate) {
		if(!"".equals(databaseDate)){
			String[] date = databaseDate.split(" ");
			date = date[0].split("/");
			return date[2];
		}
		else{
			return "";
		}
	}

    /**
     * 
     * @param databaseDate
     * @return
     */
	public static String getDayForDateTime(String databaseDate) {
		if(!databaseDate.equals("") && databaseDate!=null){
			String[] date = databaseDate.split(" ");
			         date = date[0].split("/");
			return date[0];
		}
		else{
			return "";
		}
	}

	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String getMonthForDateTime(String databaseDate) {
		if(!databaseDate.equals("") && databaseDate!=null){
			String[] date = databaseDate.split(" ");
			date = date[0].split("/");
			return date[1];
		}else{
			return "";
		}
	}

	/**
	 * 
	 * @param databaseDate
	 * @return
	 */
	public static String getformatDate(String databaseDate){ 
		if(databaseDate!=null && !databaseDate.equals("")){
			databaseDate=databaseDate.trim().substring(0, 10);

			String moisL;
			int mois=Integer.parseInt(getMonthForDate(databaseDate));
			switch(mois){
			case 1 :
				moisL="janvier";
				break;
			case 2 :
				moisL="février";
				break;
			case 3:
				moisL="mars";
				break;
			case 4:
				moisL="avril";
				break;
			case 5:
				moisL="mai";
				break;
			case 6:
				moisL="juin";
				break;
			case 7:
				moisL="juillet";
				break;
			case 8:
				moisL="août";
				break;
			case 9:
				moisL="septembre";
				break;
			case 10:
				moisL="octobre";
				break;
			case 11:
				moisL="novembre";
				break;
			case 12:
				moisL="décembre";
				break;
			default:
				moisL="Inconnu";
			}
			return getDayForDate(databaseDate)+" "+moisL+" "+getYearForDate(databaseDate);
		}
		else
			return "";

	}
	
	public static String getDateInt(String databaseDate){ 
		if(databaseDate != null){
		if(databaseDate.equalsIgnoreCase("Janvier"))
			return "01";
		else if(databaseDate.equalsIgnoreCase("Fevrier"))
			return "02";
		else if(databaseDate.equalsIgnoreCase("Mars"))
			return "03";
		else if(databaseDate.equalsIgnoreCase("Avril"))
			return "04";
		else if(databaseDate.equalsIgnoreCase("Mai"))
			return "05";
		else if(databaseDate.equalsIgnoreCase("Juin"))
			return "06";
		else if(databaseDate.equalsIgnoreCase("Juillet"))
			return "07";
		else if(databaseDate.equalsIgnoreCase("Août"))
			return "08";
		else if(databaseDate.equalsIgnoreCase("Septembre"))
			return "09";
		else if(databaseDate.equalsIgnoreCase("Octobre"))
			return "10";
		else if(databaseDate.equalsIgnoreCase("Novembre"))
			return "11";
		else if(databaseDate.equalsIgnoreCase("Décembre"))
			return "12";
		else
			return "";
		}
		else return "";
	}

	/**
	 * 
	 * @param heure
	 * @return
	 */
	public static int getMinutes(String heure){
		if(!heure.equals("") && heure!=null){
			StringTokenizer st = new StringTokenizer(heure.trim(),": ");
			String[] elt = new String[st.countTokens()];
			int i=0;
			while(st.hasMoreElements()){
				elt[i]=st.nextToken();
				i++;
			}
			return Integer.parseInt(elt[1]);
		}else{
			return 0;
		}
	}
    
	/**
	 * 
	 * @param heure
	 * @return
	 */
	public static int getHeures(String heure){
		if(!"".equals(heure) && heure!=null){
			String[] h =heure.trim().split(":");
			int x = Integer.parseInt(h[0]);

			return x;
		}
		else{
			return 0;
		}
	}

    /**
     * 
     * @param databaseDate
     * @return
     */
	public static String getMoisLettre(String databaseDate){ 
		if(databaseDate!=null){
			databaseDate=databaseDate.trim().substring(0, 10);

			String moisL;
			int mois=Integer.parseInt(getMonthForDate(databaseDate));
			switch(mois){
			case 1 :
				moisL="janvier";
				break;
			case 2 :
				moisL="fïévrier";
				break;
			case 3:
				moisL="mars";
				break;
			case 4:
				moisL="avril";
				break;
			case 5:
				moisL="mai";
				break;
			case 6:
				moisL="juin";
				break;
			case 7:
				moisL="juillet";
				break;
			case 8:
				moisL="août";
				break;
			case 9:
				moisL="septembre";
				break;
			case 10:
				moisL="octobre";
				break;
			case 11:
				moisL="novembre";
				break;
			case 12:
				moisL="décembre";
				break;
			default:
				moisL="";
			}
			return moisL;
		}
		else
			return "";

	}

	/**
	 * 
	 * @param dateNaiss
	 * @return
	 */
	public static String typeDeclarationNaissance(String dateNaiss){

		Date date1 = ConvertStringToDate(dateNaiss);
		Date date2 = new Date();
		long nbJours = (date2.getTime() - date1.getTime()) / 86400000;
		int nb = (Math.round(nbJours));
		if(nb<=45)
			return "Déclaration normale";
		else if(45<nb && nb<=365)
			return "Déclaration tardive";
		else
			return "Jugement";
	}
	
	public static String getAge(String dateNaiss){

		  Date date1 = ConvertStringToDate(dateNaiss);
		  Date date2 = new Date();
		  long nbJours = (date2.getTime() - date1.getTime()) / 86400000;
		  int nb = (Math.round(nbJours));
		  nb=nb/365;
		  return nb+"";
	}
	
	public static String typeDeclarationMariage(String dateNaiss){

		Date date1 = ConvertStringToDate(dateNaiss);
		Date date2 = new Date();
		long nbJours = (date2.getTime() - date1.getTime()) / 86400000;
		int nb = (Math.round(nbJours));
		if(nb<184)
			return "Déclaration normale";
		else
			return "Jugement";
	}

	
	public static String typeDeclarationDeces(String dateNaiss){

		Date date1 = ConvertStringToDate(dateNaiss);
		Date date2 = new Date();
		long nbJours = (date2.getTime() - date1.getTime()) / 86400000;
		int nb = (Math.round(nbJours));
		if(nb==0)
			return "Mort né";
		else if(nb<46)
			return "Déclaration normale";
		else
			return "Jugement";
	}
	
	
	public static boolean initCenter(String fic){
		boolean flag = false;
		
		File fichier = new File("C:/Users/admin/git/cec/cec/parametre/test.txt");
	    if (fichier.exists()) {
	      // Si ce fichier existe déjà, on s'arrête pour ne pas le modifier.
	      // 'System.err' est le flux d'erreur standard, qui s'affiche normalement
	      // dans le terminal qui a servi à lancer l'exécution du programme.
	      System.err.println("# Erreur : \"" + fichier.getName() + "\" existe déjà.");
	      System.exit(1);
	    }
		return flag;
	}
	
	
	
	public static String Chiffertext(String plainText){
		String code = "";
		int nbreLettre = plainText.length();
		int lettremanq = nbreLettre % 4;
		if(lettremanq != 0)
			lettremanq = 4 - lettremanq;
		int x = 0;
		while(lettremanq > x){
			plainText = plainText+"@";
			x++;
		}
		int nbreColone = plainText.length()/4;
		char[][] tab = new char[nbreColone][4];
		int t = 0;
		for(int i = 0; i < nbreColone ; i++){
			for(int j = 0 ; j < 4 ; j++){
				tab[i][j] = plainText.charAt(t);
				t++;
			}
		}
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][1];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][3];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][0];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][2];
		}
		
		return code;
	}
}
