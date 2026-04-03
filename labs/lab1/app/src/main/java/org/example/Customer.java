package org.example;

import java.time.LocalDate;

class Customer {
	public static int unique_id = 0;

	private int id;
	private String name;
	private LocalDate birthDate;
	private Address address;
	private Phone phoneNumber;

	public Customer (String name, LocalDate birthDate, Address address, Phone phoneNumber) {
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
