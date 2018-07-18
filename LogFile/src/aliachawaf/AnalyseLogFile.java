package aliachawaf;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class AnalyseLogFile {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// declare variables
		boolean fileNotFound = true;
		boolean checkNotException = false;
		String fileName;
		String analyseAllLines;
		char delimiter;
		int startLine = 0;
		int finishLine = 0;
		LogFile logFile;

		// create the list of regular expressions from the file expandedREDefs.txt
		ListRegexp listRegexp = new ListRegexp();
		listRegexp.setListRegexp("expandedREDefs.txt");

		// create the list of log patterns from the file LogPatterns.txt
		ListLogPatterns listLogPatterns = new ListLogPatterns();
		listLogPatterns.setListPatterns("LogPatterns.txt");

		// input log file name
		System.out.print("Enter the name of your logfile (path) : ");

		do {

			fileName = scanner.nextLine();
			logFile = new LogFile(fileName);

			System.out.print("Enter the delimiter (separator) used in your file : ");
			delimiter = scanner.nextLine().charAt(0);

			// read the file input and set a list of its lines depending on the delimiter
			logFile.setListLines(delimiter);

			File file = new File(fileName);

			// check if the file is reachable and readable
			if (Files.isReadable(file.toPath()) && !fileName.matches("")) {
				fileNotFound = false;
			}
		} while (fileNotFound);

		// ask if the user want to analyse the whole file
		System.out.print("\nDo you want to analyse all the lines (yes/no)? ");

		do {
			analyseAllLines = scanner.nextLine();

			if (!analyseAllLines.toLowerCase().matches("no") && !analyseAllLines.toLowerCase().matches("yes")) {
				System.out.print("Your answer is different of 'no' or 'yes'. Re-enter it : ");
			}

		} while (!analyseAllLines.toLowerCase().matches("no") && !analyseAllLines.toLowerCase().matches("yes"));

		if (analyseAllLines.toLowerCase().matches("yes")) {
			// start and finish lines initialised at -1 means that we have to analyse all
			// the lines
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
							System.out.print("Your start line is <= 0. Re-enter it: ");
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
							System.out.print("Your finish line is <= 0. Re-enter it: ");
						}
					} while (finishLine <= 0);

					checkNotException = true;

				} catch (java.util.InputMismatchException e) {
					System.out.print("\nThe line number you've entered is not a number ! Re-enter it : ");
					scanner.nextLine();
				}
			}
		}

		// launch the comparison with all the patterns according to the lines entered
		String result;
		if (startLine <= finishLine) {
			result = logFile.compareAllLogPatterns(listLogPatterns, listRegexp, startLine, finishLine);
			System.out.println("\n" + result);
		} else {
			// in case start line is > to finish line, we interchange them in the function
			result = logFile.compareAllLogPatterns(listLogPatterns, listRegexp, finishLine, startLine);
			System.out.println("\n" + result);
		}

		// input file name to record the infos of non matching lines in a new csv file
		if (!result.matches("")) {
			System.out.print("Enter the file name desired to record INFOS of non matching lines : ");
			fileName = scanner.nextLine();
			logFile.recordInfosNonMatchingLine(fileName);
		}

		scanner.close();
	}
}