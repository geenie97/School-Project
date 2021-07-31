package com.daniel.model;


public class User {

	private String userid;
	private String password;
	private String Name;
	private String address;
	private String phone;
	private String email;
	
	public String getUserId() {
		return userid;
	}
	public void setUserId(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password
				+ ", Name=" + Name + ", address=" + address +",phone="+phone +", email="
				+ email + "]";
	}
	

}
