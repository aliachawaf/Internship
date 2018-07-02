package aliachawaf;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListRegexp {

	private List<Regexp> listRegexp;

	// constructor
	public ListRegexp() {
		listRegexp = new ArrayList<Regexp>();
	}

	// getter
	public List<Regexp> getListRegexp() {
		return listRegexp;
	}

	// setter
	public void setListRegexp(String fileName) {

		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";
			String[] regexp;

			while ((line = b.readLine()) != null) {
				regexp = line.split(" ", 2);

				// regexp[0] is the name
				// regexp[1] is the definition
				Regexp re = new Regexp(regexp[0], regexp[1]);

				// we add the couple name/definition found
				this.listRegexp.add(re);
			}

			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// methods
	public String getDefinitionByName(String name) {

		String def = null;

		for (Regexp re : this.listRegexp) {

			if (re.getName().matches(name.toUpperCase())) {
				def = re.getDefinition();
			}
		}

		return def;
	}

	@Override
	public String toString() {
		String buffer = "";

		for (Regexp re : this.getListRegexp()) {

			buffer = buffer + re + "\n";

		}

		return buffer;
	}
}