package aliachawaf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;

public class Test {

	 public static void main(String[] args) throws FileNotFoundException {

		 
//			// JSON config
//			Map<String, Object> config = new HashMap<String, Object>();
//			JsonBuilderFactory factory = Json.createBuilderFactory(config);
//			JsonObject json = factory.createObjectBuilder()
//					.add("regexps", "expandedREDefs.txt")
//					.add("patterns", "LogPatterns.txt")
//					.add("inputLog", "snort.csv")
//					.add("delimiter", ",")
//					.add("startLine", -1)
//					.add("finishLine", -1)
//					.add("infosNonMatching", "infosNonMatching")
//					.build();
//
//			// JSON new file
//			try (FileWriter file = new FileWriter("jsooooon.json")) {
//				file.write(json.toString());
//				System.out.println("Successfully Copied JSON Object to File...");
//				System.out.println("\nJSON Object: " + json);
//			} catch (IOException e) {
//				System.out.println("oups");
//			}
//
//			
//			// open and read
//		 InputStream fis = new FileInputStream("jsooooon.json");
//
//	        JsonReader reader = Json.createReader(fis);
//
//	        JsonObject personObject = reader.readObject();
//
//	        reader.close();
//
//	        System.out.println("regexps   : " + personObject.getString("regexps"));
//
////	        JsonObject addressObject = personObject.getJsonObject("address");
////	        System.out.println("Address: ");
////	        System.out.println(addressObject.getString("street"));
////	        System.out.println(addressObject.getString("zipCode"));
////
////	        System.out.println("Phone  : ");
////	        JsonArray phoneNumbersArray = personObject.getJsonArray("phoneNumbers");
////
////	        for (JsonValue jsonValue : phoneNumbersArray) {
////	            System.out.println(jsonValue.toString());
////	        }		 
		 
		// create list of regular expressions
			ListRegexp listRegexp = new ListRegexp();
			listRegexp.setListRegexp("regexp-patterns.txt");
			listRegexp.replaceNamesByDef();
			
			for (Regexp re : listRegexp.getListRegexp()) {
				System.out.println(re);
			}
			
	 }
}
