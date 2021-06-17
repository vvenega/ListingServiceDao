package com.example.demo;

public class ListingUserFeedCategoryDao {
	
	private String user;
	private String category1;
	private String category2;
	private String category3;
	private String category4;
	
	private int numberCategory1;
	private int numberCategory2;
	private int numberCategory3;
	private int numberCategory4;
	
	private double minPrice;
	private double maxPrice;
	
	private int listings;
	private int donation;
	private int rent;
	private int exchange;
	
	ListingUserFeedCategoryDao(){}
	
	ListingUserFeedCategoryDao(ListingUserFeedCategoryDao dao){
		
		setUser(dao.getUser());
		setCategory1(dao.getCategory1());
		setCategory2(dao.getCategory2());
		setCategory3(dao.getCategory3());
		setCategory4(dao.getCategory4());
		setNumberCategory1(dao.getNumberCategory1());
		setNumberCategory2(dao.getNumberCategory2());
		setNumberCategory3(dao.getNumberCategory3());
		setNumberCategory4(dao.getNumberCategory4());
		setMinPrice(dao.getMinPrice());
		setMaxPrice(dao.getMaxPrice());
		setListings(dao.getListings());
		setDonation(dao.getDonation());
		setRent(dao.getRent());
		setExchange(dao.getExchange());
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getCategory4() {
		return category4;
	}

	public void setCategory4(String category4) {
		this.category4 = category4;
	}

	public int getNumberCategory1() {
		return numberCategory1;
	}

	public void setNumberCategory1(int numberCategory1) {
		this.numberCategory1 = numberCategory1;
	}

	public int getNumberCategory2() {
		return numberCategory2;
	}

	public void setNumberCategory2(int numberCategory2) {
		this.numberCategory2 = numberCategory2;
	}

	public int getNumberCategory3() {
		return numberCategory3;
	}

	public void setNumberCategory3(int numberCategory3) {
		this.numberCategory3 = numberCategory3;
	}

	public int getNumberCategory4() {
		return numberCategory4;
	}

	public void setNumberCategory4(int numberCategory4) {
		this.numberCategory4 = numberCategory4;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getListings() {
		return listings;
	}

	public void setListings(int listings) {
		this.listings = listings;
	}

	public int getDonation() {
		return donation;
	}

	public void setDonation(int donation) {
		this.donation = donation;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getExchange() {
		return exchange;
	}

	public void setExchange(int exchange) {
		this.exchange = exchange;
	}
	
	
	

}
