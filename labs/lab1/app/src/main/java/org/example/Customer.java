package org.example;

import java.time.LocalDate;
import java.util.HashSet;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class Customer {
	public static final String endpoint = "https://fakerapi.it/api/v2/persons?_quantity=";
	public static int unique_id = 0;

	private int id;
	private String name;
	private LocalDate birthDate;
	private Address address;
	private Phone phoneNumber;

	public Customer(String name, LocalDate birthDate, Address address, Phone phoneNumber) {
		this.id = unique_id;
		unique_id++;
		this.name = name;
		this.birthDate = birthDate;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public static HashSet<Customer> getFakeData(int quantity) {
		HashSet<Customer> customers = new HashSet<>();
		JsonNode fakePersons;

		try {
			// Query quantity number of fake data from the endpoint
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(String.format("%s%d", endpoint, quantity)))
				.header("Accept", "application/json")
				.GET()
				.build();
			HttpResponse<String> response = 
				client.send(request, HttpResponse.BodyHandlers.ofString());

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.body());

			fakePersons = root.get("data");
		} 
		catch(Exception e) {
			return null;
		}

		// Parse Each Person
		for (JsonNode person : fakePersons) {
			// The item to add 
				Customer c = new Customer(
						String.format(
							"%s %s", 
							person.get("firstname").asText(), 
							person.get("lastname").asText()),
						LocalDate.parse(person.get("birthday").asText()),
						new Address(person.get("address")),
						new Phone(person.get("phone"))
						);

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
