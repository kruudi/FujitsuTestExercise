package videoRental;

/**
 * API specification for the objects representing various films.
 */
public interface InterfaceFilm {
	
	/* All films have unique id in case there are multiple films with same name. */
	public int getID();
	
	/* Returns film's Name. */
	public String getName();
	
	/* Returns -1, 0 or 1 depending if the film is old, regular or new. */
	public int getType();
	
	/* Sets a new type for the film. */
	public void setType(int type);
	
	/* Returns true if movie is rented out, else false. */
	public boolean isRented();
	
	/* Sets whether the film is rented out or not */
	public void setRented(boolean rented);
}
