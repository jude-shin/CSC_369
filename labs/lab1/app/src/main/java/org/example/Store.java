package org.example;

class Store {
	public static int unique_id = 0;

	private int id;
	private String name;
	private Address address;
	private Phone phoneNumber;

	public Store (String name, Address address, Phone phoneNumber) {
		this.id = unique_id;
		unique_id++;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString(){
		return String.format(
				"%d, %s, %s, %s",
				id, 
				name, 
				address.toString(), 
				phoneNumber.toString());
	}
}
