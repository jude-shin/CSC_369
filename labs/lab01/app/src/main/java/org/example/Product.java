package org.example;

import java.util.HashSet;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class Product {
	public static final String endpoint = "https://fakerapi.it/api/v2/products?_quantity=";
	public static int unique_id = 0;

	private int id;
	private String description;
	private double price;

	public Product(String description, double price) {
		this.id = unique_id;
		unique_id++;
		this.description = description.replaceAll("\\.", "");
		this.price = price;
	}
	
	public static HashSet<Product> getFakeProducts(int quantity) {
		HashSet<Product> products = new HashSet<>();
		JsonNode fakeProducts;

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

			fakeProducts = root.get("data");
		} 
		catch(Exception e) {
			return null;
		}

		// Parse Each Product 
		for (JsonNode product : fakeProducts) {
			// The item to add 
				Product p = new Product(
						product.get("name").asText(),
						product.get("net_price").asDouble()
						);

			// Add it to the set
			products.add(p);
		}

		return products;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString(){
		return String.format(
				"%d, %s, %.2f",
				id, 
				description, 
				price);
	}
}
