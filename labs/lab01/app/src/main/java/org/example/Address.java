package org.example;

import com.fasterxml.jackson.databind.JsonNode;

class Address {
	private String address;
	private String city;
	private String zip;
	private String state;

	public Address (String address, String city, String zip, String state) {
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.state = state;
	}

	// fakerapi.it
	public Address(JsonNode address) {
		this.address = address.get("street").asText();
		this.city = address.get("city").asText();
		this.zip = address.get("zipcode").asText().substring(0, 5);
		this.state = address.get("country_code").asText();
	}
	
	@Override
	public String toString(){
		return String.format(
				"%s, %s, %s, %s",
				address, 
				city,
				zip,
				state
				);
	}
}
