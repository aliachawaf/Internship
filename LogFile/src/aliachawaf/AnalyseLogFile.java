package aliachawaf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.Json;

public class AnalyseLogFile {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// Open and read JSON Config file
		JsonReader reader = null;
		JsonObject jsonConfig = null;

		try {
			InputStream fileInputStream = new FileInputStream("jsonConfig.json");
			reader = Json.createReader(fileInputStream);
			jsonConfig = reader.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("JSON file not found !");
		} finally {
			reader.close();
		}

		// create list of regular expressions
		ListRegexp listRegexp = new ListRegexp();
		listRegexp.setListRegexp(jsonConfig.getString("regexps"));
		listRegexp.replaceNamesByDef();

		// create list of log patterns
		ListLogPatterns listLogPatterns = new ListLogPatterns();
		listLogPatterns.setListPatterns(jsonConfig.getString("patterns"));

		// read the logfile and set a list of its lines depending on the delimiter
		LogFile logFile = new LogFile(jsonConfig.getString("inputLog"));
		logFile.setListLines(jsonConfig.getString("delimiter").charAt(0));

		// launch comparison with all the patterns according to start and finish lines
		String result = logFile.compareAllLogPatterns(listLogPatterns, listRegexp, jsonConfig.getInt("startLine"), jsonConfig.getInt("finishLine"));

		// record the infos of non matching lines in a new csv file only if there is at least 1 pattern matching
		if (!result.matches("")) {
			logFile.recordInfosNonMatchingLine(jsonConfig.getString("infosNonMatching"));
		}
		
		System.out.println("\n" + result);

		scanner.close();
	}
}