package aliachawaf;

import java.util.ArrayList;
import java.util.List;

public class ListPatternLineMatching {

	private List<PatternLineMatching> listPatternLineMatching;

	public ListPatternLineMatching() {
		listPatternLineMatching = new ArrayList<PatternLineMatching>();
	}

	public List<PatternLineMatching> getListPatternLineMatching() {
		return listPatternLineMatching;
	}

	public void setListPatternLineMatching(ListLogPatterns listPatterns) {

		LogPattern pattern;

		for (int i = 0; i < listPatterns.getListPatterns().size(); i++) {

			pattern = listPatterns.getListPatterns().get(i);
			PatternLineMatching couple = new PatternLineMatching(pattern);
			this.listPatternLineMatching.add(couple);
		}
	}

	public void incrementNbLinesMatching(LogPattern pattern) {

		// searching for the pattern in the list
		for (PatternLineMatching l : this.listPatternLineMatching) {

			if (l.getPattern() == pattern) {

				// increment the nb of lines matching
				int nb = l.getNbLineMatching() + 1;
				l.setNbLineMatching(nb);
			}
		}
	}
}
