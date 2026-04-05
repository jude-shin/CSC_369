package org.example;

import java.util.HashSet;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class Store {
	public static final String endpoint = "https://fakerapi.it/api/v2/companies?_quantity=";
	public static int unique_id = 0;

	private int id;
	private String name;
	private Address address;
	private Phone phoneNumber;

	public Store(String name, Address address, Phone phoneNumber) {
		this.id = unique_id;
		unique_id++;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public static HashSet<Store> getFakeData(int quantity) {

		HashSet<Store> stores = new HashSet<>();
		JsonNode fakeStores;

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

			fakeStores = root.get("data");
		} 
		catch(Exception e) {
			return null;
		}

		// Parse Each Store 
		for (JsonNode store : fakeStores) {
			// The item to add 
				Store s = new Store(
						store.get("name").asText(),
						new Address(store.get("addresses").get(0)),
						new Phone(store.get("phone"))
						);

			// Add it to the set
			stores.add(s);
		}

		return stores;
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
