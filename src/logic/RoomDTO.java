package logic;

public class RoomDTO {

	private int roomNumber;
	private String type = "";
	private String availability = "";
	private int hotelid;
	
	public RoomDTO(int roomNumber, String type, String availability, int hotelid){
		this.setRoomNumber(roomNumber);
		this.setType(type);
		this.setAvailability(availability);
		this.setHotelid(hotelid);
		
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public int getHotelid() {
		return hotelid;
	}

	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}
	
	
}
