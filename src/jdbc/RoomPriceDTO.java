package jdbc;

public class RoomPriceDTO {

	private int price;
	private String roomtype;
	
	public RoomPriceDTO(int price, String roomtype){
		setPrice(price);
		setRoomtype(roomtype);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	
}
