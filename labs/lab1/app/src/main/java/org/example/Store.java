package org.example;

import java.time.LocalDate;

class Store {
	public static int unique_id = 0;

	private int id;
	private String name;
	private LocalDate birthDate;
	private String address; // address, city, zip, state
	private String phoneNumber;	// phoneNumber

	public Store (String name, LocalDate birthDate, String address, String phoneNumber) {
		this.id = unique_id;
		unique_id++;
		this.name = name;
		this.birthDate = birthDate;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString(){
		// TODO: 
		return "";
	}
}
