package jdbc;

import java.sql.Date;

public class CustomerbookingDTO {

	private int hotelid;
	private int roomtypeid;
	private Date startdate;
	private Date enddate;
	private String firstname = "";
	private String lastname = "";
	private String uniquestring = "";
	private String pin = "";
	
	public CustomerbookingDTO(int hotelid, int roomtypeid, Date startdate, Date enddate, String firstname, String lastname, String uniquestring, String pin){
		setHotelid(hotelid);
		setRoomtypeid(roomtypeid);
		setStartdate(startdate);
		setEnddate(enddate);
		setFirstname(firstname);
		setLastname(lastname);
		setUniquestring(uniquestring);
		setPin(pin);
	}

	public int getHotelid() {
		return hotelid;
	}

	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}

	public int getRoomtypeid() {
		return roomtypeid;
	}

	public void setRoomtypeid(int roomtypeid) {
		this.roomtypeid = roomtypeid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public String getUniquestring() {
		return uniquestring;
	}

	public void setUniquestring(String uniquestring) {
		this.uniquestring = uniquestring;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
