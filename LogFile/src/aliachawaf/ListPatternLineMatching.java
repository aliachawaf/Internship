package aliachawaf;

import java.util.ArrayList;
import java.util.List;

public class ListPatternLineMatching {

	private List<LineMatchingPattern> listPatternLineMatching;

	
	
	public ListPatternLineMatching() {
		listPatternLineMatching = new ArrayList<LineMatchingPattern>();
	}

	
	
	public List<LineMatchingPattern> getListPatternLineMatching() {
		return listPatternLineMatching;
	}

	public void setListPatternLineMatching(ListLogPatterns listPatterns) {
		
		LogPattern pattern;
		
		for (int i = 0 ; i<listPatterns.getListPatterns().size() ; i++) {
			
			pattern = listPatterns.getListPatterns().get(i);
			LineMatchingPattern couple = new LineMatchingPattern(pattern);
			this.listPatternLineMatching.add(couple);
			
		}	
	}

	public void incrementNbLinesMatching(LogPattern pattern) {

		// searching for the pattern in the list
		for (LineMatchingPattern l : this.listPatternLineMatching) {
			
			if (l.getPattern() == pattern) {
				// increment the nb of lines matching
				//System.out.println(l.getNbLineMatching());
				int nb = l.getNbLineMatching() + 1;
				//System.out.println(pattern.getLogInfos()[0]+pattern.getLogInfos()[2] + " " + nb);
				l.setNbLineMatching(nb);
			}
		}

	}

}
