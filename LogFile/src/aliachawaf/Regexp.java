package aliachawaf;

public class Regexp {

	// a regular expression is represented by a name and a definition
	private String name;
	private String definition;

	public Regexp(String name, String definition) {
		this.name = name;
		this.definition = definition;
	}

	public String getName() {
		return name;
	}

	public String getDefinition() {
		return definition;
	}

	@Override
	public String toString() {
		return name + " " + definition;
	}
}