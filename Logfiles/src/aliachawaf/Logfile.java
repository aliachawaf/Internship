package aliachawaf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Logfile {

	private String fileName;
	private String fields;

	// constructor
	public Logfile(String fileName) {
		this.fileName = fileName;
		this.setFields();
	}

	// getters & setters
	public String getFileName() {
		return fileName;
	}

	public String getFields() {
		return fields;
	}
	
	public void setFields() {
		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";

			while ((line = b.readLine()) != null) {
				System.out.println(line);
			}

			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
