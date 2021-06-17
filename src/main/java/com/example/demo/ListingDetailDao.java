package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ListingDetailDao {
	
	String owner;
	String name;
	String shortdescription;
	String thumbnailimage;
	double saleprice;
	String manufacturer;
	List<String> image;
	String salespricerange;
	List<String> category;
	String type;
	long objectid;
	String longdescription;
	int views;
	int wishlist;
	String overview;
	int quantity;
	int size;
	int orders;
	String user;
	String nameowner;
	
	public ListingDetailDao() {
		category = new ArrayList<String>();
		image = new ArrayList<String>();
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortdescription() {
		return shortdescription;
	}
	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}
	public String getThumbnailimage() {
		return thumbnailimage;
	}
	public void setThumbnailimage(String thumbnailimage) {
		this.thumbnailimage = thumbnailimage;
	}
	public double getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSalespricerange() {
		return salespricerange;
	}
	public void setSalespricerange(String salespricerange) {
		this.salespricerange = salespricerange;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getObjectid() {
		return objectid;
	}
	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}
	public String getLongdescription() {
		return longdescription;
	}
	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getWishlist() {
		return wishlist;
	}
	public void setWishlist(int wishlist) {
		this.wishlist = wishlist;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getNameowner() {
		return nameowner;
	}
	public void setNameowner(String nameowner) {
		this.nameowner = nameowner;
	}
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getImage() {
		return image;
	}

	public void setImage(List<String> image) {
		this.image = image;
	}
	
	

}
