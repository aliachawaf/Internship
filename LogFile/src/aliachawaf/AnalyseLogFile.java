package aliachawaf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.json.JsonReader;

import javax.json.JsonObject;
import javax.json.Json;

public class AnalyseLogFile {

	public static void main(String[] args) {

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

		// create a list of the couples pattern/numberOfLineMatching
		// PS : at the beginning, numberOfLineMatching = 0
		ListPatternLineMatching list = new ListPatternLineMatching();
		list.setListPatternLineMatching(listLogPatterns);

		// read the logfile and set a list of its lines depending on the delimiter
		LogFile logFile = new LogFile(jsonConfig.getString("inputLog"), list);

		// change the value of start and finish line if both equal to -1
		int startLine, finishLine;

		if (jsonConfig.getInt("startLine") == -1 && jsonConfig.getInt("finishLine") == -1) {
			startLine = 1;
			finishLine = logFile.nbLinesLogFile();
		} else {
			startLine = jsonConfig.getInt("startLine");
			finishLine = jsonConfig.getInt("finishLine");
		}

		// we clear the file nonMatchingLines.csv to append the new non-matching lines
		try {
			// append = false
			FileWriter fw = new FileWriter("nonMatchingLines.csv", false);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// launch the analysis
		logFile.compare(listRegexp, jsonConfig.getString("delimiter").charAt(0), listLogPatterns, startLine,
				finishLine);

		// output the results
		int nbLinesProcessed = finishLine - startLine + 1;
		for (PatternLineMatching l : logFile.getList().getListPatternLineMatching()) {

			System.out.println(l.getPattern().getLogInfos()[0] + l.getPattern().getLogInfos()[2] + ": "
					+ l.getNbLineMatching() + " / " + nbLinesProcessed);
		}
	}
}