package aliachawaf;

public class LineMatchingPattern {

	private LogPattern pattern;
	private int nbLineMatching;
	
	public LineMatchingPattern(LogPattern pattern) {
		this.pattern = pattern;
		this.nbLineMatching = 0 ;
	}

	public LogPattern getPattern() {
		return pattern;
	}

	public void setPattern(LogPattern pattern) {
		this.pattern = pattern;
	}

	public int getNbLineMatching() {
		return nbLineMatching;
	}

	public void setNbLineMatching(int nbLineMatching) {
		this.nbLineMatching = nbLineMatching;
	}
	
}
