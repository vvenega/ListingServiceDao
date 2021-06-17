package com.example.demo;

public class CategoryDao {
	
	private String category;
	private int number;
	
	public CategoryDao() {}
	
	public CategoryDao(CategoryDao dao) {
		setCategory(dao.getCategory());
		setNumber(dao.getNumber());
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
