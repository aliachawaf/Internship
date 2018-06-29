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
		this.setFields();
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

		for (String line : this.fields) {

			fieldsLine = line.split(",");

			for (int i = 0; i < fieldsLine.length; i++) {

				if (matches && i < pattern.getListRegexName().size()) {

					regexNameExpected = pattern.getListRegexName().get(i);

					// we have to found the definition of the regex from its name

					regexDefExpected = listRegexp.getDefinitionByName(regexNameExpected);

					// compare the current fields of the line with the pattern's regex expected
					matches = Pattern.matches(regexDefExpected, fieldsLine[i]);

				} 
			}

			System.out.println(matches);
			if (matches) {
				nbLinesMatching++;
			} else {
				matches = true;
			}
		}
		return nbLinesMatching;
	}

}
