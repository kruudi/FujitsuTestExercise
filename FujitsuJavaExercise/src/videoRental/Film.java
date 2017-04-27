package videoRental;

public class Film implements InterfaceFilm {
	
	/* Types */
	public static final int NEW_RELEASE = 1;
	public static final int REGULAR_FILM = 0;
	public static final int OLD_FILM = -1;
	
	private int ID, type;
	private String name;
	private boolean rented;
	
	public Film (int ID, String name, int type, boolean rented) {
		this.ID = ID;
		this.type = type;
		this.name = name;
		this.rented = rented;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public boolean isRented() {
		return rented;
	}
	
	@Override
	public void setRented(boolean rented) {
		this.rented = rented;
	}
	
	/* Returns custom string for the information about the film*/
	@Override
	public String toString() {
		String type = "error";
		if (this.type == -1) {
			type = "Old film";
		} else if (this.type == 0) {
			type = "Regular rental";
		} else if (this.type == 1) {
			type = "New release";
		}
		return "Film [ID=" + this.ID + ", type=" + type + ", name=" + this.name + 
				", is rented=" + this.rented + "]";
	}
}
