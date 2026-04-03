package org.example;

class LineItem {
	public static int unique_id = 0;

	private int id;
	private int salesId;
	private int productId;
	private int quantity;

	public LineItem (int salesId, int productId, int quantity) {
		this.id = unique_id;
		unique_id++;
		this.salesId = salesId;
		this.productId = productId;
		this.quantity = quantity;
	}

	@Override
	public String toString(){
		// TODO: 
		return "";
	}
}
