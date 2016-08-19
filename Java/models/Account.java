package models;

public class Account {
	private int customerID;
	private String username;
	private String password;
	private int roleID;
	private boolean isLoggedIn;
	private java.util.Date lastLoggedIn;
	private java.util.Date lastFailed;
	private boolean isExpired;
	private boolean isLocked;
	private int failedAttempts;

	public Account() {

	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public java.util.Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(java.util.Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public java.util.Date getLastFailed() {
		return lastFailed;
	}

	public void setLastFailed(java.util.Date lastFailed) {
		this.lastFailed = lastFailed;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
}
