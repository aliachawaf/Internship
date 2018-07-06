package aliachawaf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class test {
	
	private static final String SAMPLE_CSV_FILE_PATH = "./ne2.csv";

	public static void main(String[] args) throws IOException {
		/*try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			for (CSVRecord csvRecord : csvParser) {
				
				System.out.println("Line n°" + csvRecord.getRecordNumber());
				for (String s : csvRecord) {
					
					System.out.println(s);
					
				}
			}
					
		}*/
		
		
		
		
	}
}
