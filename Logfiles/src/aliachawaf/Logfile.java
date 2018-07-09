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
//	public int compareLogPattern(LogPattern pattern, ListRegexp listRegexp) {
//
//		int nbLinesMatching = 0;
//		boolean matches = true;
//
//		String regexNameExpected;
//		String regexDefExpected;
//
//		// we analyse each line of logfile
//		for (CSVRecord line : this.listLines) {
//
//			if (line.size() == pattern.getListRegexName().size()) {
//
//				// for each field of the current line, we check if it matches the regex expected
//				for (int i = 0; i < line.size(); i++) {
//
//					// when a field doesn't matches, we don't analyse the next fields
//					if (matches) {
//
//						regexNameExpected = pattern.getListRegexName().get(i);
//
//						// we get the definition of the regex from its name
//						regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);
//
//						// compare the current field of the line with the pattern's regex expected
//						matches = Pattern.matches(regexDefExpected, line.get(i));
//					}
//				}
//
//				if (matches) {
//					nbLinesMatching++;
//				} else {
//					// for the next line to analyse, we give matches its initial value
//					matches = true;
//				}
//
//			} else {
//				// if the line doesn't have the same number of fields of the pattern, then it
//				// doesn't match
//				matches = false;
//			}
//		}
//		return nbLinesMatching;
//	}
//
//	// compare all the lines of the logfile with all the patterns of listLogPatterns
//	public String compareAllLogPatterns(ListLogPatterns listLogPatterns, ListRegexp listRegexp) {
//
//		String result = "";
//		String patternMatching = "Pattern(s) matching : ";
//		int nbLinesMatching;
//
//		for (LogPattern pattern : listLogPatterns.getListPatterns()) {
//
//			nbLinesMatching = this.compareLogPattern(pattern, listRegexp);
//
//			result = result + pattern.getLogInfos()[0] + " pattern" + pattern.getLogInfos()[2] + " : " + nbLinesMatching
//					+ " / " + this.getListLines().size() + "\n";
//
//			if (nbLinesMatching == this.getListLines().size()) {
//				patternMatching = patternMatching + pattern.getLogInfos()[0] + " n°" + pattern.getLogInfos()[2] + " ; ";
//			}
//		}
//
//		result = patternMatching + "\n\n" + result;
//
//		return result;
//	}

	public boolean hasHeaderLine(ListLogPatterns listLogPatterns, ListRegexp listRegexp) {
		// returns true if the first line is header line, else returns false

		boolean matches = true;
		int nbPatternMatching = 0;

		String regexNameExpected;
		String regexDefExpected;

		CSVRecord firstLine = this.listLines.get(0);

		// for each pattern of the list
		for (LogPattern pattern : listLogPatterns.getListPatterns()) {

			if (firstLine.size() == pattern.getListRegexName().size()) {
				// for each field of the line
				for (int i = 0; i < firstLine.size(); i++) {

					if (matches && i < pattern.getListRegexName().size()) {

						// we get the regex we expect to match with from the current pattern
						regexNameExpected = pattern.getListRegexName().get(i);

						// we get the definition of the regex from its name
						regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

						// compare the current field of the line with the pattern's regex expected
						matches = Pattern.matches(regexDefExpected, firstLine.get(i));
					}
				}

				if (matches) {
					nbPatternMatching++;
				} else {
					matches = true;
				}

			} else {
				matches = false;
			}
		}

		System.out.println(nbPatternMatching);
		// if none pattern matches the first line, then it is a header line
		return (nbPatternMatching == 0);
	}

	// compare each lines of the logfile with the pattern entered as parameter
	public int compareLogPattern(LogPattern pattern, ListRegexp listRegexp, int startLine, int finishLine) {

		int nbLinesMatching = 0;
		boolean matches = true;

		String regexNameExpected;
		String regexDefExpected;
		
		// we consider that when startLine and finishLine are both equal to -1, then we have to analyse ALL the lines of the file
		if (startLine==-1 && finishLine==-1) {
			finishLine = this.listLines.size();
		}

		// we analyse each line of logfile
		for (int l = startLine; l <= finishLine; l++) {

			CSVRecord line = this.listLines.get(l);
			// for (CSVRecord line : this.listLines) {

			if (line.size() == pattern.getListRegexName().size()) {

				// for each field of the current line, we check if it matches the regex expected
				for (int i = 0; i < line.size(); i++) {

					// when a field doesn't matches, we don't analyse the next fields
					if (matches) {

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
					// for the next line to analyse, we give matches its initial value
					matches = true;
				}

			} else {
				// if the line doesn't have the same number of fields of the pattern, then it
				// doesn't match
				matches = false;
			}
		}
		return nbLinesMatching;
	}

	// compare all the lines of the logfile with all the patterns of listLogPatterns
	public String compareAllLogPatterns(ListLogPatterns listLogPatterns, ListRegexp listRegexp, int startLine, int finishLine) {

		String result = "";
		String patternMatching = "Pattern(s) matching : ";
		int nbLinesMatching;
		
		int nbLinesProcessed;
		
		// we consider that when startLine and finishLine are both equal to -1, then we have to analyse ALL the lines of the file
		if (startLine==-1 && finishLine==-1) {
			nbLinesProcessed = this.listLines.size();
		} else if (finishLine>this.listLines.size()) {
			finishLine = this.listLines.size();
			nbLinesProcessed = finishLine-startLine+1;
		} else {
			nbLinesProcessed = finishLine-startLine+1;
		}

		for (LogPattern pattern : listLogPatterns.getListPatterns()) {

			nbLinesMatching = this.compareLogPattern(pattern, listRegexp, startLine, finishLine);

			result = result + pattern.getLogInfos()[0] + " pattern" + pattern.getLogInfos()[2] + " : " + nbLinesMatching
					+ " / " + nbLinesProcessed + "\n";

			if (nbLinesMatching == nbLinesProcessed) {
				patternMatching = patternMatching + pattern.getLogInfos()[0] + " n°" + pattern.getLogInfos()[2] + " ; ";
			}
		}

		result = patternMatching + "\n\n" + result;

		return result;
	}
}
