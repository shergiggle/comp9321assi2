package jdbc;

public class SearchDTO {

	private int id;
	private String city;
	private String hotelName;
	private int price;
	private String roomtype;
	private int count;
	private boolean discounted;
	private boolean peak;
	
	
	public SearchDTO(int id, String city, String hotelName, int price, String roomtype, int count){
		this.setId(id);
		this.setCity(city);
		this.setHotelName(hotelName);
		this.setRoomtype(roomtype);
		this.setPrice(price);
		this.setCount(count);
		setDiscounted(false);
		setPeak(false);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getHotelName() {
		return hotelName;
	}


	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public boolean isDiscounted() {
		return discounted;
	}


	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}


	public boolean isPeak() {
		return peak;
	}


	public void setPeak(boolean peak) {
		this.peak = peak;
	}
}