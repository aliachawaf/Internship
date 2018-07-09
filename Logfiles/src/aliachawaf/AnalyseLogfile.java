package aliachawaf;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class AnalyseLogfile {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		ListRegexp listRegexp = new ListRegexp();
		listRegexp.setListRegexp("expandedREDefs.txt");

		ListLogPatterns listLogPatterns = new ListLogPatterns();
		listLogPatterns.setListPatterns("LogPatterns.txt");

		boolean fileNotFound = true;
		String fileName;

		System.out.print("Enter the name of your logfile (path) : ");

		do {

			fileName = scanner.nextLine();
			Logfile logFile = new Logfile(fileName);
			logFile.setFields();

			File file = new File(fileName);

			if (Files.isReadable(file.toPath())) {

				System.out.println("\n" + logFile.compareAllLogPatterns(listLogPatterns, listRegexp, 0, 20));
				//System.out.print("header line : " + logFile.hasHeaderLine(listLogPatterns, listRegexp));
				fileNotFound = false;
			}
		} while (fileNotFound);

		
		
		scanner.close();
	}
}