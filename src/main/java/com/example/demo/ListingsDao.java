package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListingsDao {
	
	String owner;
	String name;
	String shortdescription;
	String longDescription;
	String thumbnailimage;
	double saleprice;
	String manufacturer;
	List<String> image;
	String salespricerange;
	List<String> category;
	String type;
	long objectid;
	String overview;
	int views;
	int quantity;
	boolean sold;
	String dateSold;
	String nameowner;
	List<String>sharedby;
	int numshares;
	
	public ListingsDao() {
		setViews(0);
		setSold(false);
		setDateSold("N/A");
		sharedby=new ArrayList<String>();
	}
	
	
	public ListingsDao(ListingsDao dao) {
		
		setOwner(dao.getOwner());
		setName(dao.getName());
		setShortdescription(dao.getShortdescription());
		setLongDescription(dao.getLongDescription());
		setThumbnailimage(dao.getThumbnailimage());
		setSaleprice(dao.getSaleprice());
		setManufacturer(dao.getManufacturer());
		setImage(dao.getImage());
		setSalespricerange(dao.getSalespricerange());
		setCategory(dao.getCategory());
		setType(dao.getType());
		setObjectid(dao.getObjectid());
		setOverview(dao.getOverview());
		setViews(getViews());
		setQuantity(dao.getQuantity());
		setSold(dao.getSold());
		setDateSold(dao.getDateSold());
		setNameowner(dao.getNameowner());
		setSharedby(dao.getSharedby());
		setNumshares(dao.getNumshares());

		
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
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public String getSalespricerange() {
		return salespricerange;
	}
	public void setSalespricerange(String salespricerange) {
		this.salespricerange = salespricerange;
	}
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
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

	public String getOverview() {
		return overview;
	}


	public void setOverview(String overview) {
		this.overview = overview;
	}


	public int getViews() {
		return views;
	}


	public void setViews(int views) {
		this.views = views;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public boolean getSold() {
		return sold;
	}


	public void setSold(boolean sold) {
		this.sold = sold;
	}


	public String getDateSold() {
		return dateSold;
	}


	public void setDateSold(String dateSold) {
		this.dateSold = dateSold;
	}


	public String getNameowner() {
		return nameowner;
	}


	public void setNameowner(String nameowner) {
		this.nameowner = nameowner;
	}


	public List<String> getSharedby() {
		return sharedby;
	}


	public void setSharedby(List<String> sharedby) {
		this.sharedby = sharedby;
	}
	
	public void addSharedby(String name) {
		this.sharedby.add(name);
		setNumshares(getSharedby().size());
	}


	public int getNumshares() {
		return numshares;
	}


	public void setNumshares(int numshares) {
		this.numshares = numshares;
	}
	
	


}
