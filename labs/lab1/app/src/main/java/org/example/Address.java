package org.example;

class Address {
	private String address;
	private String city;
	private int zip;
	private String state;

	public Address (String address, String city, int zip, String state) {
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.state = state;
	}
	
	@Override
	public String toString(){
		return String.format("%s, %s, %05d, %s",
				address, 
				city,
				zip,
				state
				);
	}
}
