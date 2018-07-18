package aliachawaf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListLogPatterns {

	private List<LogPattern> listPatterns;

	// constructor
	public ListLogPatterns() {
		listPatterns = new ArrayList<LogPattern>();
	}

	// getter
	public List<LogPattern> getListPatterns() {
		return listPatterns;
	}

	/* setter : 
	 * we read the file of log patterns entered in parameter, 
	 * collect the patterns found (logIdentifier + list of regex names), 
	 * and then form the list of patterns
	 */
	public void setListPatterns(String fileName) {

		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";
			String[] log;
			String regexName;

			// while we haven't reached the end of file
			while ((line = b.readLine()) != null) {
				
				log = line.split("%");

				// log[0] is the logIdentifier
				// log[1]...log[n] are regex names of the pattern
				
				LogPattern pattern = new LogPattern(log[0]);
				
				for (int i = 1; i < log.length; i++) {
					
					//we only take the name without the {} which surround it in the file
					regexName = log[i].substring(1, log[i].length()-1);
					
					//add the name to the list
					pattern.getListRegexName().add(regexName);
				}

				// we add the pattern found to the list
				this.listPatterns.add(pattern);
			}

			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
