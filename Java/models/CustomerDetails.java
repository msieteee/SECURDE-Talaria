package models;

public class CustomerDetails {
	private String fname;
	private String mi;
	private String lname;
	private String email;
	private int ba_id;
	private int sa_id;
	
	public String getFname() {
		return fname;
	}
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getMi() {
		return mi;
	}
	
	public void setMi(String mi) {
		this.mi = mi;
	}
	
	public String getLname() {
		return lname;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public int getBa_id() {
		return ba_id;
	}

	public void setBa_id(int ba_id) {
		this.ba_id = ba_id;
	}

	public int getSa_id() {
		return sa_id;
	}

	public void setSa_id(int sa_id) {
		this.sa_id = sa_id;
	}
}
