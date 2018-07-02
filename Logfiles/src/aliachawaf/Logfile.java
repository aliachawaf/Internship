package aliachawaf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Logfile {

	private String fileName;
	private List<String> fields;

	// constructor
	public Logfile(String fileName) {
		this.fileName = fileName;
		this.fields = new ArrayList<String>();
	}

	// getters & setters
	public String getFileName() {
		return fileName;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields() {
		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";

			while ((line = b.readLine()) != null) {
				this.fields.add(line);
			}

			b.close();

		} catch (java.io.FileNotFoundException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// methods
	public int compareLogPattern(LogPattern pattern, ListRegexp listRegexp) {

		int nbLinesMatching = 0;
		boolean matches = true;

		String[] fieldsLine;
		String regexNameExpected;
		String regexDefExpected;

		// we analyse each line of logfile
		for (String line : this.fields) {

			fieldsLine = line.split(",");

			// for each field of the current line, we check if it matches the regex expected
			for (int i = 0; i < fieldsLine.length; i++) {

				if (matches && i < pattern.getListRegexName().size()) {

					regexNameExpected = pattern.getListRegexName().get(i);

					// we get the definition of the regex from its name
					regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

					// compare the current field of the line with the pattern's regex expected
					matches = Pattern.matches(regexDefExpected, fieldsLine[i]);

					/*
					 * if (!matches) { System.out.println("field : " + fieldsLine[i] +
					 * " ; expected : " + regexNameExpected + " " + regexDefExpected); }
					 */
				}
			}

			if (matches) {
				nbLinesMatching++;
			} else {
				matches = true;
			}
		}
		return nbLinesMatching;
	}

	public String compareAllLogPatterns(ListLogPatterns listLogPatterns, ListRegexp listRegexp) {

		String result = "";
		String patternMatching = "Pattern(s) matching : ";
		int nbLinesMatching;

		for (LogPattern pattern : listLogPatterns.getListPatterns()) {

			nbLinesMatching = this.compareLogPattern(pattern, listRegexp);

			result = result + pattern.getLogInfos()[0] + " pattern" + pattern.getLogInfos()[2] + " : "
					+ nbLinesMatching + " / " + this.getFields().size() + "\n";
			
			if (nbLinesMatching==this.getFields().size()) {
				patternMatching = patternMatching + pattern.getLogInfos()[0] + " n°" + pattern.getLogInfos()[2] + " ; ";
			}
		}
		
		result = patternMatching + "\n\n" + result;
		
		return result;
	}

}
