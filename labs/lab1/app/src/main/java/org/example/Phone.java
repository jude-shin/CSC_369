package org.example;

import com.fasterxml.jackson.databind.JsonNode;

class Phone {
	private int number;

	public Phone (int number) {
		this.number = number;
	}

	// fakerapi.it
	public Phone (JsonNode phone) {
		// "+1xxxxxxxxxx"
		this.number = Integer.parseInt(phone.asText().substring(phone.asText().length()-10));
	}
	
	@Override
	public String toString(){
		String s = String.format("%010d", number);

		return String.format(
				"%s %s %s", 
				s.substring(0, 3),
				s.substring(3, 6),
				s.substring(6, 10)
				);
	}
}
