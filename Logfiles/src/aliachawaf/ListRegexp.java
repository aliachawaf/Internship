package aliachawaf;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListRegexp {

	private List<Regexp> listRegexp;

	public ListRegexp() {
		this.listRegexp = new ArrayList<Regexp>();
	}

	public List<Regexp> getListRegexp() {
		return listRegexp;
	}

	public void setListRegexp(String fileName) {

		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";
			String[] regexp;

			while ((line = b.readLine()) != null) {
				regexp = line.split(" ");

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

	public String getDefinitionByName(String name) {

		String def = null;

		for (Regexp re : this.listRegexp) {

			if (re.getName().matches(name.toUpperCase())) {
				def = re.getDefinition();
			}
		}

		return def;
	}
}