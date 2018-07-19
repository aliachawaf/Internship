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

	/*
	 * setter : we read the file of regular expressions entered in parameter,
	 * collect the regex found (name + def), and then form the list of regex
	 */
	public void setListRegexp(String fileName) {

		try {
			File file = new File(fileName);

			BufferedReader b = new BufferedReader(new FileReader(file));

			String line = "";
			String[] regexp;

			// while we haven't reached the end of file
			while ((line = b.readLine()) != null) {

				// analyse the line only if it is not empty and not a comment
				if (!line.isEmpty() && !line.startsWith("#")) {
					// we split the line in 2 to have the name and the def of the regex
					// (split depending on a tab or a space)
					regexp = line.split("\t| ", 2);

					/* regexp[0] is the name 
					 * regexp[1] is the definition
					 */
											
					// create the regex found with the couple name/def
					Regexp re = new Regexp(regexp[0], regexp[1]);

					// add the regex to the list
					this.listRegexp.add(re);
				}
			}

			b.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// methods
	public String getDefinitionByName(String name) {

		// read the list of regex and returns the def corresponding to the name in
		// parameter
		String def = null;

		for (Regexp re : this.listRegexp) {

			if (re.getName().matches(name.toUpperCase())) {
				def = re.getDefinition();
			}
		}
		return def;
	}
	
	// replace all %{NAME} by their regex definition	
	public void replaceNamesByDef() {
		
		String defToReplace, nameToBeReplaced, name; 
		int indexEndName;
		
		for (Regexp re : this.listRegexp) {
			
			while (re.getDefinition().indexOf("%{")!=-1) {
				
				indexEndName = re.getDefinition().indexOf("%{")+1;
				
				while (re.getDefinition().charAt(indexEndName)!='}') {
					indexEndName++;
				}
				
				nameToBeReplaced = re.getDefinition().substring(re.getDefinition().indexOf("%{"), indexEndName+1);

				name = nameToBeReplaced.substring(2, nameToBeReplaced.length()-1);
				
				defToReplace = this.getDefinitionByName(name.split(":")[0]);

				re.setDefinition(re.getDefinition().replace(nameToBeReplaced, defToReplace));
			}	
		}	
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