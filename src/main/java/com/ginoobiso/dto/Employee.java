package com.ginoobiso.dto;

import com.google.gson.annotations.SerializedName;

public class Employee {

	@SerializedName("id")
	private String id;
	
	@SerializedName("employee_name")
	private String name;
	
	@SerializedName("employee_salary")
	private String salary;

	@SerializedName("employee_age")
	private String age;

	@SerializedName("profile_image")
	private String profileImage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	public boolean isTenToThirty() {
		return Integer.parseInt(this.age) >= 10 && Integer.parseInt(this.age) <= 30;
	}
	public boolean isThirtyToSixty() {
		return Integer.parseInt(this.age) >= 30 && Integer.parseInt(this.age) <= 60;
	}
	public boolean isSixtyToHundred() {
		return Integer.parseInt(this.age) >= 60 && Integer.parseInt(this.age) <= 100;
	}
	
	
}
