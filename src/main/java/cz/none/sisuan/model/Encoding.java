package cz.none.sisuan.model;

public enum Encoding {
	UTF8("UTF8"), CP1250("Cp1250"), ISO88592("ISO8859_2");

	private String	name;

	private Encoding(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
