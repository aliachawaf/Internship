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
		boolean checkNotException = false;
		String fileName;
		int startLine = 0;
		int finishLine = 0;
		Logfile logFile;
		String allLines;

		// input logfile name
		System.out.print("Enter the name of your logfile (path) : ");

		do {

			fileName = scanner.nextLine();
			logFile = new Logfile(fileName);
			logFile.setFields();

			File file = new File(fileName);

			if (Files.isReadable(file.toPath()) && !fileName.matches("")) {
				fileNotFound = false;
			}
		} while (fileNotFound);

		// ask if he want to analyse the whole file
		System.out.print("\nDo you want to analyse all the lines (yes/no)? ");

		do {
			allLines = scanner.nextLine();

			if (!allLines.matches("no") && !allLines.matches("yes")) {
				System.out.print("Your answer is different of 'no' or 'yes'. Re-enter it : ");
			}

		} while (!allLines.matches("no") && !allLines.matches("yes"));

		if (allLines.toLowerCase().matches("yes")) {
			startLine = -1;
			finishLine = -1;

		} else {

			// input start line
			System.out.println("Enter start line number : ");

			while (!checkNotException) {
				try {
					do {
						startLine = scanner.nextInt();
						scanner.nextLine();

						if (startLine <= 0) {
							System.out.print("your start line is <= 0. Re-enter it: ");
						}

					} while (startLine <= 0);

					checkNotException = true;

				} catch (java.util.InputMismatchException e) {
					System.out.print("\nThe line number you've entered is not a number ! Re-enter it : ");
					scanner.nextLine();
				}
			}

			checkNotException = false;

			// input finish line
			System.out.println("Enter finish line number : ");

			while (!checkNotException) {
				try {
					do {
						finishLine = scanner.nextInt();
						scanner.nextLine();

						if (finishLine <= 0) {
							System.out.print("your finish line is <= 0. Re-enter it: ");
						}

					} while (finishLine <= 0);

					checkNotException = true;

				} catch (java.util.InputMismatchException e) {
					System.out.print("\nThe line number you've entered is not a number ! Re-enter it : ");
					scanner.nextLine();
				}
			}
		}

		// lanch the comparison with all the patterns according to the lines entered
		if (startLine <= finishLine) {
			System.out
					.println("\n" + logFile.compareAllLogPatterns(listLogPatterns, listRegexp, startLine, finishLine));
		} else {
			System.out
					.println("\n" + logFile.compareAllLogPatterns(listLogPatterns, listRegexp, finishLine, startLine));
		}

		// input file name for none matching lines
		System.out.print("Enter a file name to create recording the none matching lines : ");
		String file = scanner.nextLine();
		logFile.recordNoneMatchingLine(file);

		scanner.close();
	}
}