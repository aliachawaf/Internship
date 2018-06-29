package aliachawaf;

import java.util.ArrayList;
import java.util.List;

public class LogPattern {

	/*
	 * logInfos[0] is the log type ;
	 * logInfos[1] is the number of fields ;
	 * logInfos[2] is the n° of pattern for this log type
	 */
	
	private String[] logInfos;
	private List<String> listRegexName;

	// constructor
	public LogPattern(String logIdentifier) {
		logInfos = new String[3];
		listRegexName = new ArrayList<String>();
		
		this.setLogInfos(logIdentifier);
	}

	// getters & setters
	public List<String> getListRegexName() {
		return listRegexName;
	}

	public String[] getLogInfos() {
		return logInfos;
	}

	public void setLogInfos(String logIdentifier) {

		String[] substrings = logIdentifier.split("-");                                                                                          

		if (substrings.length == 2) {

			logInfos[0] = substrings[0];
			logInfos[1] = substrings[1].split("re")[0];
			logInfos[2] = substrings[1].split("re")[1];
			
		} else {
			logInfos[0] = substrings[0] + "-" + substrings[1];
			logInfos[1] = substrings[2].split("re")[0];
			logInfos[2] = substrings[2].split("re")[1];
		}
	}

	@Override
	public String toString() {	
		return "type : " + logInfos[0] + " ; nb fields : " + logInfos[1] + " ; patterns n°" + logInfos[2] + "regex names : " + listRegexName;
	}

}
