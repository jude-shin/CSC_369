package org.example;

class Product {
	public static int unique_id = 0;

	private int id;
	private String description;
	private float price;

	public Product (String description, float price) {
		this.id = unique_id;
		unique_id++;
		this.description = description;
		this.price = price;
	}
	
	@Override
	public String toString(){
		// TODO: 
		return "";
	}
}
