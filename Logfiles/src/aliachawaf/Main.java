package aliachawaf;

public class Main {

	public static void main(String[] args) {

		ListRegexp listRegexp = new ListRegexp();
		listRegexp.setListRegexp("expandedREDefs.txt");
	
		ListLogPatterns listLogPatterns = new ListLogPatterns();
		listLogPatterns.setListPatterns("LogPatterns.txt");
		
		Logfile log = new Logfile("strat.csv");
		
		System.out.println(log.compareLogPattern(listLogPatterns.getListPatterns().get(0), listRegexp));
	}
}
