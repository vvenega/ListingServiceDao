package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;



@RestController
public class ListingServiceDaoController {
	
	
	@GetMapping("/ListingsDAO/{user}/{ecommunities}/{types}")
	public List<ListingsDao> getListings(@PathVariable String user,@PathVariable List<String>ecommunities,
			@PathVariable List<String> types) {
		
		       List<ListingsDao> list = new ArrayList<ListingsDao>();
		       
		       list = CassandraAdapter.getListingPredicted(user);
    			
    			if(list==null || (list!=null && list.size()<=0)){
    				
    				list=JanusGraphAdapter.getSampleListings(user, ecommunities,types);
    				CassandraAdapter.saveListingsPredictToCassandra(list, user);
    				
    				   
    		}else {
    			list = JanusGraphAdapter.getCategoriesPerObjectId(list);
    		}
    			
    			
    			
		return list;
		
	}
	
	
	
	
	@GetMapping("/ListingDaoObjectID/{objectid}")
	public ListingsDao getListingObjectid(@PathVariable String objectid) {
		
		ListingsDao dao = JanusGraphAdapter.getListingbyObjectid(objectid);
		
		
		
    			
		return dao;
		
	}
	

	@GetMapping("/CategoriesDAO/{user}/{ecommunities}/{types}")
	public List<CategoryDao> getCategories(@PathVariable String user,@PathVariable List<String>ecommunities,
			@PathVariable List<String> types) {
        

		List<CategoryDao> list= JanusGraphAdapter.getCategoriesPerUser(user, ecommunities,types);
		
    			
		return list;
		
	}
	
	@GetMapping("/UserCategoriesDAO/{user}/{ecommunities}/{types}")
	public List<CategoryDao> getUserCategories(@PathVariable String user,@PathVariable List<String>ecommunities,
			@PathVariable List<String> types) {

		List<CategoryDao> list= JanusGraphAdapter.getUserCategories(user, ecommunities,types);
		
    			
		return list;
		
	}
	
	@GetMapping("/ListingsCategoryDAO/{user}/{category}/{ecommunities}/{types}")
	public List<ListingsDao> getListingsCategory(@PathVariable String user,@PathVariable String category,
			@PathVariable List<String> ecommunities,@PathVariable List<String> types) {
		
		
		List<ListingsDao> list = new ArrayList<ListingsDao>();
		
		list = JanusGraphAdapter.getListingsCategory(user, ecommunities, category,types);
		
		
		System.err.println("Size:"+list.size());
		return list;
		
	}
	
	@GetMapping("/ListingsUserCategoryDAO/{user}/{category}/{ecommunities}/{types}")
	public List<ListingsDao> getListingsUserCategory(@PathVariable String user,@PathVariable String category,
			@PathVariable List<String> ecommunities,@PathVariable List<String> types) {
		
		
		List<ListingsDao> list = new ArrayList<ListingsDao>();
		
		list = JanusGraphAdapter.getListingsUserCategory(user, ecommunities, category,types);
		
		
		System.err.println("Size:"+list.size());
		return list;
		
	}
	
	@GetMapping("/ListingsPriceDAO/{user}/{price}")
	public List<ListingsDao> getListingsPrice(@PathVariable String user,@PathVariable double price) {
		
	
		ArrayList<ListingsDao> list = new ArrayList<ListingsDao>();
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		  
		  String pricerange = "";
		  
		  
		  ///Configure in a table or class per organization
		  if(price>=1 && price <=50)
			  pricerange ="1 - 50";
		  else if(price>=51 && price <=200)
			  pricerange ="101 - 200";
		  else if(price>=201 && price <=500)
			  pricerange ="201 - 500";
		  else if(price>=501 && price <=2000)
			  pricerange ="501 - 2000";
		  else if(price>=2001 && price <=5000)
			  pricerange ="2001 - 5000";
		  else if(price>5000)
			  pricerange="> 5000";
		  
		  
		  PreparedStatement prepared = session.prepare(
    			  "select owner,name,shortdescription,thumbnailimage,saleprice,"+
		  " manufacturer,image,salespricerange,category,type,objectid,nameowner from "+TypeConstants.NAMESPACE+"Listings "+
    					  "where salespricerange=?");

    			//BoundStatement bound = prepared.bind(airport);
		        
		  BoundStatement bound = prepared.bind(pricerange);
    			ResultSet rset = session.execute(bound);
    			if(rset!=null) {
    				Iterator<Row> itr =rset.iterator();
    				
    				int count = 0; //it should be changed by artificial intelligence
    				
    				while(itr.hasNext() && count<30) {
    					Row row = itr.next();
    					
    					String owner = row.getString(0);
    					
    					if(owner!=null && !owner.equalsIgnoreCase(user)) {
    						ListingsDao dao = new ListingsDao();
	    					dao.setOwner(owner);
	    					dao.setName(row.getString(1));
	    					dao.setShortdescription(row.getString(2));
	    					dao.setThumbnailimage(row.getString(3));
	    					dao.setSaleprice(row.getDouble(4));
	    					dao.setManufacturer(row.getString(5));
	    					//dao.setImage(row.getString(6));
	    					dao.setSalespricerange(row.getString(7));
	    					//dao.setCategory(row.getString(8));
	    					dao.setType(row.getString(9));
	    					dao.setObjectid(row.getLong(10));
	    					//dao.setNameowner(row.getString(11));
	    					list.add(new ListingsDao(dao));
	    					
	    					System.out.println(dao);
	    					count ++;
    					}
    				}
    			}
    			
    			session.close();
  		      client.close();
    			
		return list;
		
	}
	
	@GetMapping("/ListingDetailDAO/{user}/{objectid}")
	public ListingDetailDao getListingDetail(@PathVariable String user,@PathVariable String objectid) {
		
		System.err.println("Listing Detail 1");
		
		ListingDetailDao dao =JanusGraphAdapter.getListingDetail(user, objectid);
		
		
		
    			
		return dao;
		
	}
	
	@GetMapping("/ListingContactDAO/{owner}/{requester}/{namerequester}/{email}/{message}/{mobile}/{product}/{price}/{objectid}/{nameowner}")
	public void setContactRecord(@PathVariable String owner, @PathVariable String requester,
			@PathVariable String namerequester,@PathVariable String email,@PathVariable String message,
			@PathVariable String mobile,@PathVariable String product,@PathVariable double price,
			@PathVariable String objectid,@PathVariable String nameowner) {
		
		
		CassandraConnector client = new CassandraConnector();
		  client.connect(TypeConstants.CASSANDRA_SERVER, TypeConstants.CASSANDRA_PORT);
		  Session session = client.getSession();
		  
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
          LocalDateTime now = LocalDateTime.now();
          
		  
		  PreparedStatement prepared = session.prepare(
  			  "Insert into "+TypeConstants.NAMESPACE+"product_requested(owner,requester,namerequester,"+
		  "email,message,mobile,product,price,"+
  	  "objectid,daterequested,requested,nameowner)values(?,?,?,?,?,?,?,?,?,?,?,?)");
  	  BoundStatement bound = prepared.bind(owner,requester,namerequester,email,message,mobile,
  			  product,price,Long.parseLong(objectid),dtf.format(now),true,nameowner);
			session.execute(bound);
			
			
			
			session.close();
		      client.close();
		
	}
	
	@GetMapping("/ListingUserCategoriesFeedDAO/{user}/{ecommunities}/{types}")
	public ListingUserFeedCategoryDao getListingFeedUser(@PathVariable String user,@PathVariable List<String> ecommunities,
			@PathVariable List<String> types) {
		
		List<Object> list=CassandraAdapter.getCategoriesPredict(user);
		
		boolean success = (boolean)list.get(0);
		ListingUserFeedCategoryDao dao=null;
		
		List<String> categories=new ArrayList<String>();

    				if(success) {
    				
    					 dao=(ListingUserFeedCategoryDao)list.get(1);
    					 
    					 categories.add(dao.getCategory1());
    					 categories.add(dao.getCategory2());
    					 categories.add(dao.getCategory3());
    					 categories.add(dao.getCategory4());
    					 dao=JanusGraphAdapter.getCategoriesNumber(user, ecommunities, types, categories,dao);
    					 
    			}else {
    				
    				dao=JanusGraphAdapter.getSampleCategories(user, ecommunities,types);
    				
    				categories.add(dao.getCategory1());
					categories.add(dao.getCategory2());
					categories.add(dao.getCategory3());
					categories.add(dao.getCategory4());
    				CassandraAdapter.saveCategoriesPredict(user, categories);

    			}
    				
		dao=JanusGraphAdapter.getFeedCategoryType(user,ecommunities,dao,types);
		dao.setMinPrice(0.0);
		dao.setMaxPrice(JanusGraphAdapter.getMaxPrice(user, ecommunities,types));
		//dao.setMaxPrice(0.0);
    	dao.setUser(user);			
		return dao;
		
	}
	
	
	@GetMapping("/ListingSharePostDao/{username}/{objectid}")
	public boolean sharePost(@PathVariable String username, @PathVariable String objectid) {
		
		boolean result=false;
		
		try {
			System.err.println("Shared Post Dao...");
			result = JanusGraphAdapter.sharePost(objectid, username);
		}catch(Exception e) {
			
			e.printStackTrace();
			result=false;
			
		}
		
		return result;
		
	}
	
	
	@GetMapping("/ListingPostExcelDao/{username}/{namefile}")
	public String postExcelDao(@PathVariable String username, @PathVariable String namefile) {
		
		String outputfile=null;
		
		
		try {
			System.err.println("Upload Excel Post Dao...");
			outputfile =ExcelAdapter.getInstance().uploadExcel(username, namefile);
		}catch(Exception e) {
			
			e.printStackTrace();
			outputfile=null;
			
		}
		
		return outputfile;
		
	}
	
	
	

}
