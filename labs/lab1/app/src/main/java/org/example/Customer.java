package org.example;

import java.time.LocalDate;
import java.util.HashSet;

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

	public HashSet<Customer> fakeData(int quantity) {
		HashSet<Customer> customers = new HashSet<>();

		// TODO: query quantity number of fake data from the endpoint

		for (int i=0; i < quantity; i++) {
			// The item to add 
			Customer c;

			// Populate that item
			// TODO:

			while (!customers.contains(c)) {
				// c = [new customer]
				// TODO:
			}
		
			// Add it to the set
			customers.add(c);
		}
	
		return customers;
	}

	@Override
	public String toString(){
		return String.format(
				"%d, %s, %s, %s, %s",
				id,
				name, 
				birthDate.toString().replaceAll("-", "/"),
				address.toString(),
				phoneNumber.toString());
	}
}
