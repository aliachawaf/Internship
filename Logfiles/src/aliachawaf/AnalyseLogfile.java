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
		boolean linesInput = false;
		String fileName;
		int startLine = 0;
		int finishLine = 0;
		Logfile logFile;

		System.out.print("Enter the name of your logfile (path) : ");

		do {

			fileName = scanner.nextLine();
			logFile = new Logfile(fileName);
			logFile.setFields();

			File file = new File(fileName);

			
			if (Files.isReadable(file.toPath())) {
				
				fileNotFound = false;
			}
		} while (fileNotFound);

		
		
		System.out.print("Do you want to analyse all the lines (yes/no)? ");
		
		do {
			String allLines = scanner.nextLine();
			
			if (allLines.toLowerCase().matches("yes")) {
				startLine = -1;
				finishLine = -1;
				linesInput = true;
			} else if (allLines.toLowerCase().matches("no")) {
				System.out.println("enter lines : ");
				startLine = scanner.nextInt();
				finishLine = scanner.nextInt();
				linesInput = true;
			}
			
		} while (!linesInput);
		
		System.out.println("\n" + logFile.compareAllLogPatterns(listLogPatterns, listRegexp, startLine, finishLine));
		//System.out.print("header line : " + logFile.hasHeaderLine(listLogPatterns, listRegexp));
		
		scanner.close();
	}
}