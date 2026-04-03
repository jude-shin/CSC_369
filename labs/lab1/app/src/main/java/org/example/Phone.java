package org.example;

class Phone {
	private int number;

	public Phone (int number) {
		this.number = number;
	}
	
	@Override
	public String toString(){
		String s = String.format("%010d", number);

		return String.format("%s %s %s", 
				s.substring(0, 3),
				s.substring(3, 6),
				s.substring(6, 10)
				);
	}
}
