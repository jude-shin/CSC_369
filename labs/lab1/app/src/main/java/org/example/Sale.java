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

class Sale {
	public static int unique_id = 0;

	private int id;
	private LocalDate date;
	private LocalTime time;
	private int storeId;
	private int customerId;

	public Sale(LocalDate date, LocalTime time, int storeId, int customerId) {
		this.id = unique_id;
		unique_id++;
		this.date = date;
		this.time = time;
		this.storeId = storeId;
		this.customerId = customerId;
	}

	public static HashSet<Sale> getFakeSales(
			int quantity, 
			HashSet<Customer> customers,
			HashSet<Store> stores) {

		HashSet<Sale> sales = new HashSet<>();

		List<LocalDate> uniqueDates = getUniqueRandomDates(LocalDate.of(1900, 1, 1), LocalDate.now(), quantity);
		List<LocalTime> uniqueTimes = getUniqueRandomTimes(LocalTime.MIN, LocalTime.MAX, quantity);

		for (int i=0; i < quantity; i++) {
			// Grab a random customer
			Customer c = customers.stream()
				.skip(new Random().nextInt(customers.size()))
				.findFirst()
				.orElse(null);

			// Grab a random store
			Store s = stores.stream()
				.skip(new Random().nextInt(stores.size()))
				.findFirst()
				.orElse(null);
			
			Sale sale = new Sale(
					uniqueDates.get(i),
					uniqueTimes.get(i),
					c.getId(),
					s.getId());
		
			sales.add(sale);
		}

		return sales;
	}

	private static List<LocalDate> getUniqueRandomDates(LocalDate start, LocalDate end, int n) {
		long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;

		if (n > daysBetween) {
			throw new IllegalArgumentException("Requested more unique dates than available in range");
		}

		return ThreadLocalRandom.current()
			.ints(0, (int) daysBetween)
			.distinct()
			.limit(n)
			.mapToObj(start::plusDays)
			.sorted()
			.collect(Collectors.toList());
	}

	private static List<LocalTime> getUniqueRandomTimes(LocalTime start, LocalTime end, int n) {
		long secondsBetween = Duration.between(start, end).toSeconds();

		if (n > Math.abs(secondsBetween)) {
			throw new IllegalArgumentException("Requested more unique times than seconds in range");
		}

		return ThreadLocalRandom.current()
			.longs(0, secondsBetween)
			.distinct()
			.limit(n)
			.mapToObj(start::plusSeconds)
			.sorted()
			.collect(Collectors.toList());
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString(){
		return String.format(
				"%d, %s, %s, %d, %d",
				id,
				date.toString().replaceAll("-", "/"),
				time.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
				storeId, 
				customerId);
	}
}
