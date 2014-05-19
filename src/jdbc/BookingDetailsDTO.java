package jdbc;

import java.sql.Date;

public class BookingDetailsDTO {
private int id;
private String hotelname;
private String roomtype;
private Date startdate;
private Date enddate;
private String firstname;
private String lastname;


public BookingDetailsDTO(int id, String hotelname, String roomtype, Date startdate, Date enddate, String firstname, String lastname){
	setId(id);
	setHotelname(hotelname);
	setRoomtype(roomtype);
	setStartdate(startdate);
	setEnddate(enddate);
	setFirstname(firstname);
	setLastname(lastname);
}

public String getHotelname() {
	return hotelname;
}
public void setHotelname(String hotelname) {
	this.hotelname = hotelname;
}
public String getRoomtype() {
	return roomtype;
}
public void setRoomtype(String roomtype) {
	this.roomtype = roomtype;
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

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

	
	
}
