package aliachawaf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class LogFile {

	private String fileName;
	private ListPatternLineMatching list;

	// constructor
	public LogFile(String fileName, ListPatternLineMatching list) {
		this.fileName = fileName;
		this.list = list;
	}

	// getters
	public String getFileName() {
		return fileName;
	}

	public ListPatternLineMatching getList() {
		return list;
	}

	public void compare(ListRegexp listRegexp, char delimiter, ListLogPatterns listLogPatterns, int startLine,
			int finishLine) {

		boolean fieldMatches = true;
		boolean lineMatches = true;
		boolean patternMatches = false;
		int lineNumber = 0;

		String regexNameExpected;
		String regexDefExpected;

		try {
			Reader reader = Files.newBufferedReader(Paths.get(fileName));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(delimiter));

			// we analyse each line of logfile
			for (CSVRecord line : csvParser) {

				if (lineNumber >= startLine - 1 && lineNumber < finishLine) {
					System.out.println(lineNumber);
					// make the comparison for each pattern
					for (LogPattern pattern : listLogPatterns.getListPatterns()) {

						// we compare only if it doesn't match a pattern yet and if the line has the
						// same number of fields than the pattern
						if (!patternMatches && line.size() == pattern.getListRegexName().size()) {

							// for each field of the current line, we check if it matches the regex expected
							for (int i = 0; i < line.size(); i++) {

								regexNameExpected = pattern.getListRegexName().get(i);

								// get the definition of the regex from its name
								regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

								// compare the current field of the line with the pattern's regex expected
								fieldMatches = Pattern.matches(regexDefExpected, line.get(i));

								if (!fieldMatches) {

									// if one field doesn't match so the entire line doesn't match too.
									lineMatches = false;
								}
							}

							if (lineMatches) {
								this.list.incrementNbLinesMatching(pattern);
								patternMatches = true;
							}

							// for the next line to analyse, we give initial values
							lineMatches = true;
							fieldMatches = true;
						}
					}

					// we record the line in a CSV file if it doesn't match with any pattern
					if (!patternMatches) {

						this.recordNonMatchingLines(line);

					} else {
						patternMatches = false; // initial value
					}
				}
				lineNumber++;
			}

			csvParser.close();

		} catch (java.nio.file.NoSuchFileException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (java.nio.file.AccessDeniedException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (java.nio.file.InvalidPathException e) {
			System.out.print("Invalid path ! Re-enter it : ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// APPEND the line in parameter in a csv file
	public void recordNonMatchingLines(CSVRecord lineNonMatching) {

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;

		// Create the CSVFormat object
		CSVFormat csvFileFormat = CSVFormat.DEFAULT;

		try {

			// initialise FileWriter object
			fileWriter = new FileWriter("nonMatchingLines.csv", true);

			// initialise CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(lineNonMatching);

		} catch (Exception e) {
			System.out.println("Writing CSV error!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Flushing/closing error!");
				e.printStackTrace();
			}
		}
	}

	public int nbLinesLogFile() {

		int lineProcessed = 0;

		try {

			lineProcessed = (int) Files.lines(Paths.get(fileName)).count();

		} catch (java.nio.file.NoSuchFileException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (java.nio.file.AccessDeniedException e) {
			System.out.print("File not found ! Please check your input (path) and re-enter it : ");
		} catch (java.nio.file.InvalidPathException e) {
			System.out.print("Invalid path ! Re-enter it : ");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lineProcessed;
	}
}