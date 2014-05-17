package jdbc;

public class OverallHotelsDTO {
	
	private String name = "";
	private int occupied;
	private int available;
	
	public OverallHotelsDTO(String name, int occupied, int available){
		setName(name);
		setOccupied(occupied);
		setAvailable(available);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOccupied() {
		return occupied;
	}

	public void setOccupied(int occupied) {
		this.occupied = occupied;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

}