package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

class LineItem {
	public static int unique_id = 0;

	private int id;
	private int salesId;
	private int productId;
	private int quantity;

	public static int QUANTITY_MAX = 100;

	public LineItem(int salesId, int productId, int quantity) {
		this.id = unique_id;
		unique_id++;
		this.salesId = salesId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public static HashSet<LineItem> getFakeLineItems(
			int quantity, 
			HashSet<Sale> sales,
			HashSet<Product> products) {
		HashSet<LineItem> lineItems = new HashSet<>();

		// Start off my including the first number of stores and customers
		int i = sales.size();

		if (quantity < i) {
			// Throw an error because there must be at least one lineItem for every 
			// sale 
			throw new IllegalArgumentException("Quantity must be at least as large as the sales set.");
		}
		
		// Include every sale 
		for (Sale s : sales) {
			// Grab a random store
			Product p = products.stream()
				.skip(new Random().nextInt(products.size()))
				.findFirst()
				.orElse(null);

			LineItem lineItem = new LineItem(
					s.getId(),
					p.getId(),
					new Random().nextInt(LineItem.QUANTITY_MAX));

			lineItems.add(lineItem);
		}

		for (; i < quantity; i++) {
			// Grab a random customer
			Sale s = sales.stream()
				.skip(new Random().nextInt(sales.size()))
				.findFirst()
				.orElse(null);

			// Grab a random store
			Product p = products.stream()
				.skip(new Random().nextInt(products.size()))
				.findFirst()
				.orElse(null);
			
			LineItem lineItem = new LineItem(
					s.getId(),
					p.getId(),
					new Random().nextInt(LineItem.QUANTITY_MAX));
		
			lineItems.add(lineItem);
		}

		return lineItems;
	}


	@Override
	public String toString(){
		return String.format(
				"%d, %d, %d, %d",
				id, 
				salesId, 
				productId, 
				quantity);
	}
}
