package com.example.demo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraAdapter {
	
	private static CassandraAdapter instance;
	
	static {
		instance = new CassandraAdapter();
	}
	
	private CassandraAdapter() {
		
	}
	
	public static void saveListingsPredictToCassandra(List<ListingsDao> list,String username) {
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		
		try {
			
			Iterator<ListingsDao>itr =list.iterator();
			int cont=1;
			
			while(itr.hasNext()) {
				ListingsDao dao =itr.next();
				String query="Insert into "+TypeConstants.NAMESPACE+"Listings_Predict("+
				"owner,nameowner,name,shortdescription,thumbnailimage,saleprice,manufacturer,"+
						"image,salespricerange,type,longdescription,views,quantity,"+
				"objectid,user,record)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				  
				  PreparedStatement prepared = session.prepare(query);
		    	  BoundStatement bound = prepared.bind(dao.getOwner(),dao.getNameowner(),dao.getName(),
		    			  dao.getShortdescription(),dao.getThumbnailimage(),dao.getSaleprice(),
		    			  dao.getManufacturer(),dao.getImage(),dao.getSalespricerange(),
		    			  dao.getType(),dao.getLongDescription(),dao.getViews(),dao.getQuantity(),
		    			  dao.getObjectid(),username,cont);
	    			session.execute(bound);
	    			cont++;
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Error al salval record in listings_predict.");
		}finally {
			session.close();
		      client.close();
		}
	}
	
	public static void saveCategoriesPredict(String username, List<String> categories) {
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		
		try {
			
			
				
				String query="Insert into "+TypeConstants.NAMESPACE+"Listing_Category("+
				"category1,category2,category3,category4,user)values(?,?,?,?,?)";
				  
				  PreparedStatement prepared = session.prepare(query);
		    	  BoundStatement bound = prepared.bind(categories.get(0),categories.get(1),
		    			  categories.get(2),categories.get(3),username);
	    			session.execute(bound);
	    			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		      client.close();
		}
	}
	
	
	public static List<Object> getCategoriesPredict(String username) {
		
		ListingUserFeedCategoryDao dao = new ListingUserFeedCategoryDao();
		
		List<Object>list = new ArrayList<Object>();
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		
		try {
			

			  PreparedStatement prepared = session.prepare(
	    			  "select category1,category2,category3,category4 from "+TypeConstants.NAMESPACE+"Listing_Category"+
	    					  " where user=?");

	    			//BoundStatement bound = prepared.bind(airport);
			        
			  BoundStatement bound = prepared.bind(username);
  			ResultSet rset = session.execute(bound);
  			if(rset!=null) {
  				Iterator<Row> itr =rset.iterator();
  				
  				if(itr.hasNext() ) {
  					Row row = itr.next();
  					
  					dao.setCategory1(row.getString(0));
  					dao.setCategory2(row.getString(1));
  					dao.setCategory3(row.getString(2));
  					dao.setCategory4(row.getString(3));
  					list.add(true);
  					list.add(dao);
  					
  				}
  			}
	    
  			if(list.size()<=0) {
  				list.add(false);
  				list.add(dao);
  			}
	    			
		}catch(Exception e) {
			e.printStackTrace();
			dao = new ListingUserFeedCategoryDao();
			list = new ArrayList<Object>();
			list.add(false);
			list.add(dao);
			
		}finally {
			session.close();
		      client.close();
		}
		
		return list;
	}
	
	
	public static List<ListingsDao> getListingPredicted(String username) {
		
	
		List<ListingsDao> list = new ArrayList<ListingsDao>();
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		
		try{
			
			ListingsDao dao = new ListingsDao();
			
			
			
			  
			  PreparedStatement prepared = session.prepare(
	    			  "select owner,name,shortdescription,thumbnailimage,saleprice,"+
			  " manufacturer,salespricerange,type,objectid,nameowner from "
	    					  +TypeConstants.NAMESPACE+"Listings_Predict where user=?");

	    			//BoundStatement bound = prepared.bind(airport);
			
			  BoundStatement bound = prepared.bind(username);
	    			ResultSet rset = session.execute(bound);
	    			if(rset!=null) {
	    				Iterator<Row> itr =rset.iterator();
	    				
	    				//System.err.println("rset different than null");
	    				
	    				while(itr.hasNext()) {
	    					Row row = itr.next();
	    					dao.setOwner(row.getString(0));
	    					dao.setName(row.getString(1));
	    					dao.setShortdescription(row.getString(2));
	    					dao.setThumbnailimage(row.getString(3));
	    					dao.setSaleprice(row.getDouble(4));
	    					dao.setManufacturer(row.getString(5));
	    					dao.setSalespricerange(row.getString(6));
	    					dao.setType(row.getString(7));
	    					dao.setObjectid(row.getLong(8));
	    					dao.setNameowner(row.getString(9));
	    					list.add(new ListingsDao(dao));
	    					
	    				}
	    			}
			
		}catch(Exception e) {
			e.printStackTrace();
			list = new ArrayList<ListingsDao>();
			
		}finally {
			session.close();
		      client.close();
		}
		
		return list;
	}

	
	
}
