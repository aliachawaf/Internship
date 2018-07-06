package aliachawaf;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Logfile {

	private String fileName;
	private List<CSVRecord> listLines;

	// constructor
	public Logfile(String fileName) {
		this.fileName = fileName;
		this.listLines = new ArrayList<CSVRecord>();
	}

	// getters & setters
	public String getFileName() {
		return fileName;
	}

	public List<CSVRecord> getListLines() {
		return listLines;
	}

	// read the logfile and add its lines in the list
	public void setFields() {
		
		try {		
			Reader reader = Files.newBufferedReader(Paths.get(fileName));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
			
			for (CSVRecord line : csvParser) {
				
				this.listLines.add(line);
				
			}

			csvParser.close();
			
		} catch (java.nio.file.NoSuchFileException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// compare each lines of the logfile with the pattern entered as parameter
	public int compareLogPattern(LogPattern pattern, ListRegexp listRegexp) {

		int nbLinesMatching = 0;
		boolean matches = true;

		String regexNameExpected;
		String regexDefExpected;

		// we analyse each line of logfile
		for (CSVRecord line : this.listLines) {

			// for each field of the current line, we check if it matches the regex expected
			for (int i = 0; i < line.size(); i++) {
				
				if (matches && i < pattern.getListRegexName().size()) {

					regexNameExpected = pattern.getListRegexName().get(i);

					// we get the definition of the regex from its name
					regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

					// compare the current field of the line with the pattern's regex expected
					matches = Pattern.matches(regexDefExpected, line.get(i));
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

	
	// compare all the lines of the logfile with all the patterns of listLogPatterns
	public String compareAllLogPatterns(ListLogPatterns listLogPatterns, ListRegexp listRegexp) {

		String result = "";
		String patternMatching = "Pattern(s) matching : ";
		int nbLinesMatching;

		for (LogPattern pattern : listLogPatterns.getListPatterns()) {

			nbLinesMatching = this.compareLogPattern(pattern, listRegexp);

			result = result + pattern.getLogInfos()[0] + " pattern" + pattern.getLogInfos()[2] + " : " + nbLinesMatching
					+ " / " + this.getListLines().size() + "\n";

			if (nbLinesMatching == this.getListLines().size()) {
				patternMatching = patternMatching + pattern.getLogInfos()[0] + " n°" + pattern.getLogInfos()[2] + " ; ";
			}
		}

		result = patternMatching + "\n\n" + result;

		return result;
	}
	
	
	public boolean hasHeaderLine(ListLogPatterns listLogPatterns, ListRegexp listRegexp) {
		
		boolean headerLine = false;
		String regexNameExpected;
		String regexDefExpected;
		
		CSVRecord firstLine = this.listLines.get(0);
		
		for (LogPattern pattern : listLogPatterns.getListPatterns()) {
			
		
			for (int i = 0; i < firstLine.size(); i++) {

				if (!headerLine) {
				regexNameExpected = pattern.getListRegexName().get(i);

				// we get the definition of the regex from its name
				regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

				// compare the current field of the line with the pattern's regex expected
				headerLine = Pattern.matches(regexDefExpected, firstLine.get(i));
				System.out.println(headerLine);
				}
			}

		}
		
		return !headerLine;
		
	}
}
