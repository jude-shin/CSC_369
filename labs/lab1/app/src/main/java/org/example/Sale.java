package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

class Sale {
	public static int unique_id = 0;

	private int id;
	private LocalDate date;
	private LocalTime time;
	private int storeId;
	private int customerId;

	public Sale (LocalDate date, LocalTime time, int storeId, int customerId) {
		this.id = unique_id;
		unique_id++;
		this.date = date;
		this.time = time;
		this.storeId = storeId;
		this.customerId = customerId;
	}
	
	@Override
	public String toString(){
		// TODO: 
		return "";
	}
}
