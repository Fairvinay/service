package com.sort.employee.domain;

public class Employee {

	public String firstName;
	public String lastName; 
	public String age; 
	public String country;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		
		return "Employee " + getFirstName() + " "+ getLastName() + " " + getAge() + " " +getCountry();
		
	}
}
