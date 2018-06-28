package aliachawaf;

public class Main {

	public static void main(String[] args) {

		ListRegexp list = new ListRegexp();
		list.setListRegexp("src/aliachawaf/expandedREDefs.txt");
		System.out.println(list.getListRegexp());
		System.out.println(list.getDefinitionByName("MONTHDAY"));
	}

}
