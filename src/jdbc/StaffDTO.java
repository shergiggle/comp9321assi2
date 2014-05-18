package jdbc;

public class StaffDTO {

	private String firstname = "";
	private String lastname = "";
	private String password = "";
	private String access = "";
	private int hotelid;
	
	public StaffDTO(String firstname, String lastname, String password, String access, int hotelid){
		setFirstname(firstname);
		setLastname(lastname);
		setPassword(password);
		setAccess(access);
		setHotelid(hotelid);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getHotelid() {
		return hotelid;
	}

	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}

}
