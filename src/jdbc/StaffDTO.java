package jdbc;

public class StaffDTO {

	private String firstname = "";
	private String lastname = "";
	private String password = "";
	private String access = "";
	
	public StaffDTO(String firstname, String lastname, String password, String access){
		setFirstname(firstname);
		setLastname(lastname);
		setPassword(password);
		setAccess(access);
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

}
