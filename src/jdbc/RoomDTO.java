package jdbc;

public class RoomDTO {

	private int roomNumber;
	private int typeid;
	private String availability = "";
	private int hotelid;
	
	public RoomDTO(int roomNumber, int typeid, String availability, int hotelid){
		this.setRoomNumber(roomNumber);
		this.setType(typeid);
		this.setAvailability(availability);
		this.setHotelid(hotelid);
		
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getType() {
		return typeid;
	}

	public void setType(int typeid) {
		this.typeid = typeid;
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
