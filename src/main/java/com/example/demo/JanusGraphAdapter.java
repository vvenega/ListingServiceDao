package com.example.demo;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceVertex;

import org.springframework.web.bind.annotation.PathVariable;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class JanusGraphAdapter {
	
	
private static JanusGraphAdapter instance;
	
	private static Graph graph;
	private static GraphTraversalSource g;
	
	static {
		instance=new JanusGraphAdapter();
	}
	
	
	private JanusGraphAdapter() {
		try {
		graph = EmptyGraph.instance();
	    g = graph.traversal().withRemote("remote-graph.properties");
	    System.err.println("returned of graph traversal:"+g);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static Vertex addCategory(String category) {
		Vertex cat=null;
		
		try {
		cat = g.addV("Category").property("name", category).next();
		}catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return cat;
		
	}
	
	public static Vertex addImage(String image) {
		Vertex cat=null;
		try {
		cat = g.addV("Image").property("name", image).next();
		}catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return cat;
		
	}
	
	
	public static Vertex addType(String type) {
		Vertex cat=null;
		try {
		cat = g.addV("Type").property("name", type).next();
		}catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return cat;
		
	}
	
	public static Vertex addManufacturer(String manufacturer) {
		Vertex cat=null;
		try {
		cat = g.addV("Manufacturer").property("name", checkForEspecialCharacters(manufacturer)).next();
		}catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return cat;
		
	}
	
	
	
	public static List<Vertex> getCategories(ListingsDao dao) {
		
		boolean exists = false;
		List<Vertex> list = new ArrayList<Vertex>();
		Vertex vertex=null;
		
		try {

			Iterator<String> itr = dao.getCategory().iterator();
			
			while(itr.hasNext()) {
				String category = checkForEspecialCharacters(itr.next());
				GraphTraversal<Vertex, Vertex> tv = g.V().hasLabel("Category").has("name",category);
				exists =tv.hasNext();
				
				if(exists) {
					vertex = tv.next();
					list.add(vertex);
				}else {
					vertex = addCategory(category);
					list.add(vertex);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	


public static List<Object> existsType(ListingsDao dao) {
	
	boolean exists = false;
	List<Object> list = new ArrayList<Object>();
	
	try {

    //List<List<Vertex>> v = g.V().has("Organization","name",user.getOrganization()).fold().toList();
    //System.out.println("Size:"+v.size());
    
	GraphTraversal<Vertex, Vertex> tv=g.V().hasLabel("Type").has("name",dao.getType());
    exists = tv.hasNext();
	list.add(exists);
	
	if(exists) {
		Vertex vertex = tv.next();
		list.add(vertex);
	}
   
    
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	
	return list;
	
}
	

public static List<Object> existsManufacturer(ListingsDao dao) {
	
	boolean exists = false;
	List<Object> list = new ArrayList<Object>();
	
	try {

    
	GraphTraversal<Vertex, Vertex> tv=g.V().hasLabel("Manufacturer").has("name",checkForEspecialCharacters(dao.getManufacturer()));
    exists = tv.hasNext();
	list.add(exists);
	
	if(exists) {
		Vertex vertex = tv.next();
		list.add(vertex);
	}
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return list;
	
}




public static Vertex  getUser(ListingsDao dao) {
	
	Vertex user=null;
	
	try {

    
		GraphTraversal<Vertex, Vertex> tv = g.V().hasLabel("User").has("username",dao.getOwner());
		
		if(tv.hasNext()) {
			user = tv.next();
		}
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return user;
	
}


public static boolean existsObjectid(ListingsDao dao) {
	
	boolean exists = false;
	
	try {

    
    exists = g.V().hasLabel("Listing").has("objectid",dao.getObjectid()).hasNext();
    
    
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return exists;
	
}


public static boolean wasSharedbyUser(String username,long objectid) {
	
	boolean exists = false;
	
	try {

    
    exists = g.V().has("User","username",username).out("shared").has("Listing","objectid",objectid).hasNext();
    
    
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return exists;
	
}


private static Vertex getObjectid(String objectid) {
	
	Vertex listing=null;
	
	try {

    
      GraphTraversal<Vertex, Vertex> tv = g.V().hasLabel("Listing").has("objectid",objectid);
		
		if(tv.hasNext()) {
			listing = tv.next();
		}
    
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return listing;
	
}


private static Vertex getVertexObjectid(String objectid) {
	
	Vertex listing=null;
	
	try {

    
      GraphTraversal<Vertex, Vertex> tv = g.V().hasLabel("Listing").has("objectid",objectid);
		
		if(tv.hasNext()) {
			listing = tv.next();
		}
    
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return listing;
	
}


private static Vertex getVertexUser(String username) {
	
	Vertex user=null;
	
	try {

    
      GraphTraversal<Vertex, Vertex> tv = g.V().hasLabel("User").has("username",username);
		
		if(tv.hasNext()) {
			user = tv.next();
		}
    
   
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return user;
	
}

public static boolean sharePost(String objectid,String username) {
	
	boolean result=false;
	
	try {
		
		Vertex listing = getVertexObjectid(objectid);
		
		if(listing!=null) {
			Vertex user = getVertexUser(username);
			
			if(user!=null) {
				g.addE("shared").from(user).to(listing).next();
			}
		}
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return result;
}



public static String addListing(ListingsDao dao) {
	
	String result = "";
	try {
    
		Vertex category;
		Vertex type;
		Vertex manufacturer;
		
		
		List<Vertex> listCategory = getCategories(dao);
		List<Object> listType = existsType(dao);
		List<Object> listManufacturer = existsManufacturer(dao);
		boolean existsType = (boolean)listType.get(0);
		boolean existsManufacturer = (boolean)listManufacturer.get(0);
		
		
		if(existsType)
			type = (Vertex)listType.get(1);
		else
			type = addType(dao.getType());
		
		if(existsManufacturer)
			manufacturer = (Vertex)listManufacturer.get(1);
		else
			manufacturer = addManufacturer(dao.getManufacturer());
		
		long objectid=RedisAdapter.getLongKeyNextValue("objectid");
		dao.setObjectid(objectid);
		if(!existsObjectid(dao)) {
			
		   Vertex owner = getUser(dao);
		   
		   if(owner!=null) {
			   Vertex listing = g.addV("Listing").property("ownerusername", dao.getOwner())
					   .property("product",checkForEspecialCharacters(dao.getName()))
					   .property("shortdescription", checkForEspecialCharacters(dao.getShortdescription()))
					   .property("thumbnailimage",dao.getImage().get(0))
					   .property("saleprice",dao.getSaleprice())
					   .property("salepricerange", dao.getSalespricerange())
					   .property("longdescription", checkForEspecialCharacters(dao.getLongDescription()))
					   .property("views",dao.getViews())
					   .property("overview",checkForEspecialCharacters(dao.getOverview()))
					   .property("quantity",dao.getQuantity())
					   .property("objectid",dao.getObjectid())
					   .property("sold",dao.getSold() )
					   .property("datesold", dao.getDateSold()).next();
	          
			   Iterator<Vertex> itrCat = listCategory.iterator();
			   
			   while(itrCat.hasNext()) {
				   category=itrCat.next();
				   g.addE("iscategory").from(listing).to(category).next();
			   }

	           g.addE("istype").from(listing).to(type).next();
	           g.addE("manufacturedby").from(listing).to(manufacturer).next();
	           
	           List<String>lstImage = dao.getImage();
	           Iterator<String> itr = lstImage.iterator();
	           
	           while(itr.hasNext()) {
	        	   String strImage = itr.next();
	        	   Vertex image = addImage(strImage);
	        	   g.addE("isfrom").from(image).to(listing).next();
	           }
	           
	           
	           g.addE("post").from(owner).to(listing).next();
	           
	           result = "Posteado.";
		   }else {
			   System.err.println("Error: User not Found in Graph:"+dao.getOwner());
			   result = "Usuario no Encontrado.";
		   }
			   
		}else {
			System.err.println("Object Id:"+dao.getObjectid()+" already exits in the Graph");
			result="Objectid Existe en la base de datos";
		}
    
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		
	}
	
	return result;
	
}


private static String checkForEspecialCharacters(String cad) {
	
	String replacedstring =  cad.replace("/", "-");
	replacedstring = replacedstring.replace("\\", "-");
	
	return replacedstring;
}

public static List<ListingsDao> getSampleListings(String username, List<String> ecommunities,
		@PathVariable List<String> types) {
	
	List<ListingsDao> lstDao = new ArrayList<ListingsDao>();
	
	try {

		
		List<Map<Object,Object>> lst=g.V()
		.has("Ecommunity","name",P.within((Collection)ecommunities))
		.in("iscommunity")
		.not(has("username",username))
		.out("post").as("lst")
		.out("iscategory").dedup()
		.sample(5).in("iscategory")
		.as("lst").in("post")
		.not(has("username",username))
		.out("iscommunity").has("Ecommunity","name",P.within((Collection)ecommunities))
		.sample(20).select("lst").valueMap("ownerusername","product","shortdescription",
				"thumbnailimage","saleprice","salepricerange","views",
				"quantity","objectid","sold","datesold").toList();
		
		Iterator<Map<Object,Object>>itr = lst.iterator();
		
		while(itr.hasNext()) {
			Map<Object,Object>map =itr.next();
			Iterator<Entry<Object,Object>> itr2 =map.entrySet().iterator();
			ListingsDao dao = new ListingsDao();
			
			while(itr2.hasNext()) {
				Entry<Object,Object> entry=itr2.next();
				
				String key = (String)entry.getKey();
				
				if(key.equals("ownerusername"))
					dao.setOwner((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("product"))
					dao.setName((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("shortdescription"))
					dao.setShortdescription((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("thumbnailimage"))
					dao.setThumbnailimage((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("saleprice"))
					dao.setSaleprice((double)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("salepricerange"))
					dao.setSalespricerange((String)((ArrayList)entry.getValue()).get(0));
				//else if(key.equals("longdescription"))
				//	dao.setLongDescription((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("views"))
					dao.setViews((int)((ArrayList)entry.getValue()).get(0));
				//else if(key.equals("overview"))
				//	dao.setOverview((String)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("quantity"))
					dao.setQuantity((int)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("objectid"))
					dao.setObjectid((long)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("sold"))
					dao.setSold((boolean)((ArrayList)entry.getValue()).get(0));
				else if(key.equals("datesold"))
					dao.setDateSold((String)((ArrayList)entry.getValue()).get(0));
				
				
			}
			
			dao = setListingEdges(dao);
			lstDao.add(dao);
		}
		
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return lstDao;
}


private static ListingDetailDao setListingDetailEdges(ListingDetailDao dao) {
	try {
		List<Map<String,Object>> lstE=g.V().has("Listing","objectid",dao.getObjectid()).as("lst")
				.both()
				.as("label")
				.valueMap()
				.as("edge")
				.select("label","edge")
				.toList();
				
				Iterator<Map<String,Object>> itrE = lstE.iterator();
				List<String> lstCategory = new ArrayList<String>();
				List<String> lstImage = new ArrayList<String>();
				
				while(itrE.hasNext()) {
					Map<String,Object> mapE =itrE.next();
					Iterator<Entry<String,Object>> itrME = mapE.entrySet().iterator();
					ReferenceVertex v;
					String label="";
					String edgeLabel="";
				    String username="";
					while(itrME.hasNext()) {
						Entry<String,Object> entryME=itrME.next();
						String key = entryME.getKey();
						
						if(key.equals("label")) {
							v=(ReferenceVertex)entryME.getValue();
							label=v.label();
						}else if(key.equals("edge")) {
							Map<String,List<String>> mE =(Map<String,List<String>>)entryME.getValue();
							Set<Entry<String,List<String>>>set=  mE.entrySet();
							Iterator<Entry<String,List<String>>>itrSet =set.iterator();
							while(itrSet.hasNext()) {
								Entry<String,List<String>> entry =itrSet.next();
								String value = entry.getValue().get(0);
								
								if(label.equalsIgnoreCase("Manufacturer"))
									dao.setManufacturer(value);
								else if(label.equalsIgnoreCase("Type"))
									dao.setType(value);
								else if(label.equalsIgnoreCase("Category"))
									lstCategory.add(value);
								else if(label.equalsIgnoreCase("Image"))
									lstImage.add(value);
								else if(label.equalsIgnoreCase("User")) {
									String valKey = entry.getKey();
								    
									  if(valKey.equals("name")) {
										  
										dao.setNameowner(value);
									  }
								    
								}
							}
							
							
						}else if(key.equals("edgelabel")) {
							edgeLabel = (String)entryME.getValue();
							
							
						}
					}
				}
				
				dao.setCategory(lstCategory);
				dao.setImage(lstImage);
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	return dao;
}


private static ListingsDao setListingEdges(ListingsDao dao) {
	try {
		List<Map<String,Object>> lstE=g.V().has("Listing","objectid",dao.getObjectid()).as("lst")
				.both()
				.as("label")
				.valueMap()
				.as("edge")
				.select("label","edge")
				.toList();
				
				Iterator<Map<String,Object>> itrE = lstE.iterator();
				List<String> lstCategory = new ArrayList<String>();
				//List<String> lstImage = new ArrayList<String>();
				
				while(itrE.hasNext()) {
					Map<String,Object> mapE =itrE.next();
					Iterator<Entry<String,Object>> itrME = mapE.entrySet().iterator();
					ReferenceVertex v;
					String label="";
					String edgeLabel="";
				    String username="";
					while(itrME.hasNext()) {
						Entry<String,Object> entryME=itrME.next();
						String key = entryME.getKey();
						
						if(key.equals("label")) {
							v=(ReferenceVertex)entryME.getValue();
							label=v.label();
						}else if(key.equals("edge")) {
							Map<String,List<String>> mE =(Map<String,List<String>>)entryME.getValue();
							Set<Entry<String,List<String>>>set=  mE.entrySet();
							Iterator<Entry<String,List<String>>>itrSet =set.iterator();
							while(itrSet.hasNext()) {
								Entry<String,List<String>> entry =itrSet.next();
								String value = entry.getValue().get(0);
								
								if(label.equalsIgnoreCase("Manufacturer"))
									dao.setManufacturer(value);
								else if(label.equalsIgnoreCase("Type"))
									dao.setType(value);
								else if(label.equalsIgnoreCase("Category"))
									lstCategory.add(value);
								//else if(label.equalsIgnoreCase("Image"))
								//	lstImage.add(value);
								else if(label.equalsIgnoreCase("User")) {
									String valKey = entry.getKey();
								    
									  if(valKey.equals("username")) {
										  username=value;
										  
									  }else if(valKey.equals("name")) {
										  if(wasSharedbyUser(username,dao.getObjectid()))
											     dao.addSharedby(value);
											  else
												  dao.setNameowner(value);
									  }
								    
								}
							}
							
							
						}else if(key.equals("edgelabel")) {
							edgeLabel = (String)entryME.getValue();
							
							
						}
					}
				}
				
				dao.setCategory(lstCategory);
				//dao.setImage(lstImage);
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return dao;
}

public static List<ListingsDao> getCategoriesPerObjectId(List<ListingsDao>list) {
	
	List<ListingsDao> newList=new ArrayList<ListingsDao>();
	
	try {
		GraphTraversal<Vertex,Map<Object,Object>> vGraphTraversal;
		
		Iterator<ListingsDao>itr = list.iterator();
		
		while(itr.hasNext()) {
			ListingsDao dao = itr.next();
			
			List<Map<Object,Object>> lst= g.V().has("Listing","objectid",dao.getObjectid())
					.out("iscategory")
					.valueMap()
					.dedup().toList();
					
					Iterator<Map<Object,Object>> itr2 = lst.iterator();
					
					List<String> categoryList = new ArrayList<String>();
					while(itr2.hasNext()) {
						Map<Object,Object> mp= itr2.next();
						
						Set<Entry<Object,Object>> set = mp.entrySet();
						Iterator<Entry<Object,Object>> itrE=set.iterator();
						while(itrE.hasNext()) {
							Entry<Object,Object> entry =itrE.next();
							String category = (String)((ArrayList)entry.getValue()).get(0);
							categoryList.add(category);
							
						}
						
						
					}
					
					dao.setCategory(categoryList);
					newList.add(dao);
		}
		
	
	
	
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		newList = new ArrayList<ListingsDao>();
	}
	
	return newList;
}

public static List<CategoryDao> getCategoriesPerUser(String username,List<String> ecommunities,
		 List<String> types) {
	
	System.err.println("username:"+username);
	System.err.println("username:"+ecommunities);
	System.err.println("username:"+types);

	ArrayList<CategoryDao> list = new ArrayList<CategoryDao>();
	
	try {
		GraphTraversal<Vertex,Map<Object,Object>> vGraphTraversal;
		
		
	List<Map<Object,Object>> lst= g.V()
	.has("Ecommunity","name",P.within((Collection)ecommunities))
	.in("iscommunity")
	.not(has("username",username))
	.out("post","shared").as("lst")
	.out("istype").has("Type","name",P.within((Collection)types)).select("lst").out("iscategory")
	.valueMap()
	.dedup().toList();
	
	System.err.println(lst);
	
	Iterator<Map<Object,Object>> itr = lst.iterator();
	
	
	while(itr.hasNext()) {
		Map<Object,Object> mp= itr.next();
		
		Set<Entry<Object,Object>> set = mp.entrySet();
		Iterator<Entry<Object,Object>> itrE=set.iterator();
		while(itrE.hasNext()) {
			Entry<Object,Object> entry =itrE.next();
			String category = (String)((ArrayList)entry.getValue()).get(0);
			CategoryDao dao = new CategoryDao();
			dao.setCategory(category);
			dao.setNumber(0);
			list.add(new CategoryDao(dao));
		}
		
		
	}
	
	
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		list = new ArrayList<CategoryDao>();
	}
	
	return list;
}


public static List<CategoryDao> getUserCategories(String username,List<String> ecommunities,
		@PathVariable List<String> types) {
	
	ArrayList<CategoryDao> list = new ArrayList<CategoryDao>();
	
	try {
		GraphTraversal<Vertex,Map<Object,Object>> vGraphTraversal;
		
		
	List<Map<Object,Object>> lst= g.V()
	.has("Ecommunity","name",P.within((Collection)ecommunities))
	.in("iscommunity")
	.has("username",username)
	.out("post","shared").as("lst")
	.out("istype").has("Type","name",P.within((Collection)types)).select("lst").out("iscategory")
	.valueMap()
	.dedup().toList();
	
	Iterator<Map<Object,Object>> itr = lst.iterator();
	
	
	while(itr.hasNext()) {
		Map<Object,Object> mp= itr.next();
		
		Set<Entry<Object,Object>> set = mp.entrySet();
		Iterator<Entry<Object,Object>> itrE=set.iterator();
		while(itrE.hasNext()) {
			Entry<Object,Object> entry =itrE.next();
			String category = (String)((ArrayList)entry.getValue()).get(0);
			CategoryDao dao = new CategoryDao();
			dao.setCategory(category);
			dao.setNumber(0);
			list.add(new CategoryDao(dao));
		}
		
		
	}
	
	
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		list = new ArrayList<CategoryDao>();
	}
	
	return list;
}


public static ListingUserFeedCategoryDao  getCategoriesNumber(String username,List<String> ecommunities,
		 List<String> types,List<String> categories,ListingUserFeedCategoryDao feed) {
	
	
	
	try {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   
		System.err.println(LocalDateTime.now()+" Categories Start................. query numbers");
		
		
		List<Object> lst=g.V().has("Category" ,"name",P.within((Collection)categories))
		.as("cat").in("iscategory").as("lst").out("istype")
		.has("Type" ,"name",P.within((Collection)types))
		.select("lst").in("post")
		.not(has("User","username",username))
		.out("iscommunity")
		.has("Ecommunity","name",P.within((Collection)ecommunities))
		.select("cat").group().by(values("name").fold()).unfold().toList();
		System.err.println(LocalDateTime.now()+" Finish................. query numbers");
		
	Iterator<Object> itr = lst.iterator();
	
	int cont =0;
	
	int number1=0,number2=0,number3=0,number4=0;
	
	while(itr.hasNext()) {
		Entry<Object,Object> entry =(Entry<Object,Object>)itr.next();
		
		String category =(String)((ArrayList)entry.getKey()).get(0);
		int number = ((ArrayList)entry.getValue()).size();

		if(feed.getCategory1().equals(category))
			number1=number;
		else if(feed.getCategory2().equals(category))
			number2=number;
		else if(feed.getCategory3().equals(category))
			number3=number;
		else if(feed.getCategory4().equals(category))
			number4=number;

	}
	
	feed.setNumberCategory1(number1);
	feed.setNumberCategory2(number2);
	feed.setNumberCategory3(number3);
	feed.setNumberCategory4(number4);
	
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		feed = new ListingUserFeedCategoryDao();
	}
	
	return feed;
}

public static ListingUserFeedCategoryDao  getSampleCategories(String username,List<String> ecommunities,
		 List<String> types) {
	
	ListingUserFeedCategoryDao feed = new ListingUserFeedCategoryDao();
	
	try {
		List<Object> lst=g.V().has("Ecommunity","name",P.within((Collection)ecommunities))
	.in("iscommunity")
	.not(has("username",username))
	.out("post").as("lst")
	.out("istype").has("Type","name",P.within((Collection)types)).select("lst").out("iscategory")
	.group()
	.by(values("name")
	.fold()).unfold().sample(4).toList();
		
	Iterator<Object> itr = lst.iterator();
	
	int cont =0;
	
	while(itr.hasNext()) {
		Entry<Object,Object> entry =(Entry<Object,Object>)itr.next();
		
		String category =(String)((ArrayList)entry.getKey()).get(0);
		int number = ((ArrayList)entry.getValue()).size();

		if(cont==0) {
			feed.setCategory1(category);
			feed.setNumberCategory1(number);
			cont++;
		}else if(cont==1) {
			
			feed.setCategory2(category);
			feed.setNumberCategory2(number);
			cont++;
		}else if(cont==2) {
			feed.setCategory3(category);
			feed.setNumberCategory3(number);
			cont++;
		}else if(cont==3) {
			feed.setCategory4(category);
			feed.setNumberCategory4(number);
			cont++;
		}
				

	}
	
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		feed = new ListingUserFeedCategoryDao();
	}
	
	return feed;
}

public static ListingUserFeedCategoryDao getFeedCategoryType(String username,List<String> ecommunities,
		ListingUserFeedCategoryDao feed, List<String> types) {
	
	try {
		
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		 System.err.println(LocalDateTime.now()+" Types Start................. query numbers");
		 
		 List<Map<Object,Long>>lst=g.V().has("Type","name",P.within((Collection)types))
		 .as("type").in("istype")
		 .in("post").not(has("User","username",username))
		 .out("iscommunity").has("Ecommunity","name",P.within((Collection)ecommunities))
		 .select("type").groupCount().by("name").toList();
		 
		 Iterator<Map<Object,Long>> itr=lst.iterator();
		 
		 while(itr.hasNext()) {
			 Map<Object,Long>map=itr.next();
			 Iterator<Entry<Object,Long>> itr2= map.entrySet().iterator();
			 
			 while(itr2.hasNext()) {
				 Entry<Object,Long> entry =itr2.next();
				 String key =(String)entry.getKey();
				 if(key.equals("Rent"))
					 feed.setRent(entry.getValue().intValue());
				 else if(key.equals("Listing"))
					 feed.setListings(entry.getValue().intValue());
				 else if(key.equals("Donation"))
					 feed.setDonation(entry.getValue().intValue());
				 else if(key.equals("Exchange"))
					 feed.setExchange(entry.getValue().intValue());
			 }
		 }
		 
		
		
		System.err.println(LocalDateTime.now()+" Types Finish................. query numbers");
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return feed;
}


public static ListingsDao getListingbyObjectid(String objectid) {
	
	ListingsDao dao = new ListingsDao();
	
	try {
		
		System.err.println("Listing Detail 2:"+objectid);
		List<Map<Object,Object>> lst = g.V().has("Listing","objectid",objectid)
		.valueMap("ownerusername","product","shortdescription",
				"thumbnailimage","saleprice","salepricerange","views",
				"quantity","objectid","overview").toList();
		
		
		
		
		
		Iterator<Map<Object,Object>> itr = lst.iterator();
		
		while(itr.hasNext()) {
			Map<Object,Object> map =itr.next();
			Set<Entry<Object,Object>> entry=map.entrySet();
			Iterator<Entry<Object,Object>> itr2=entry.iterator();
			
			while(itr2.hasNext()) {
				Entry<Object,Object> entry2 =itr2.next();
				
				
				String key =(String)entry2.getKey();
				

				
				if(key.equals("ownerusername"))
					dao.setOwner((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("product"))
					dao.setName((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("shortdescription"))
					dao.setShortdescription((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("thumbnailimage"))
					dao.setThumbnailimage((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("saleprice"))
					dao.setSaleprice((double)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("salepricerange"))
					dao.setSalespricerange((String)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("views"))
					dao.setViews((int)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("quantity"))
					dao.setQuantity((int)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("objectid"))
					dao.setObjectid((long)((ArrayList)entry2.getValue()).get(0));

				else if(key.equals("overview"))	
					dao.setOverview((String)((ArrayList)entry2.getValue()).get(0));
			}
			
			
		}
		
		dao=setListingEdges(dao);
		System.err.println("shortdes:"+dao.getShortdescription());
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return dao;
}

public static ListingDetailDao getListingDetail( String user, String objectid) {
	
	ListingDetailDao dao = new ListingDetailDao();
	
	try {
		
		System.err.println("Listing Detail 2:"+user+" objectid:"+objectid);
		List<Map<Object,Object>> lst = g.V().has("Listing","objectid",objectid)
		.valueMap("ownerusername","product","shortdescription",
				"thumbnailimage","saleprice","salepricerange","views",
				"quantity","objectid","overview").toList();
		
		
		
		
		
		Iterator<Map<Object,Object>> itr = lst.iterator();
		
		while(itr.hasNext()) {
			Map<Object,Object> map =itr.next();
			Set<Entry<Object,Object>> entry=map.entrySet();
			Iterator<Entry<Object,Object>> itr2=entry.iterator();
			
			while(itr2.hasNext()) {
				Entry<Object,Object> entry2 =itr2.next();
				
				
				String key =(String)entry2.getKey();
				

				
				if(key.equals("ownerusername"))
					dao.setOwner((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("product"))
					dao.setName((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("shortdescription"))
					dao.setShortdescription((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("thumbnailimage"))
					dao.setThumbnailimage((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("saleprice"))
					dao.setSaleprice((double)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("salepricerange"))
					dao.setSalespricerange((String)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("views"))
					dao.setViews((int)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("quantity"))
					dao.setQuantity((int)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("objectid"))
					dao.setObjectid((long)((ArrayList)entry2.getValue()).get(0));

				else if(key.equals("overview"))	
					dao.setOverview((String)((ArrayList)entry2.getValue()).get(0));
			}
			
			
		}
		
		
		
		dao=setListingDetailEdges(dao);
		System.err.println("short Des:"+dao.getShortdescription());
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	
	return dao;
}

public static List<ListingsDao> getListingsCategory(String username,List<String> ecommunities,
		String category, List<String> types) {
	ArrayList<ListingsDao> list = new ArrayList<ListingsDao>();
	try {
		List<Map<Object,Object>> lst = g.V().has("Category","name",category)
		.in("iscategory").as("lst")
		.in("post","shared")
		.not(has("User","username",username))
		.out("iscommunity")
		.has("Ecommunity","name",P.within((Collection)ecommunities))
		.select("lst")
		.out("istype").has("Type","name",P.within((Collection)types))
		.select("lst")
		.valueMap("ownerusername","product","shortdescription",
				"thumbnailimage","saleprice","salepricerange","views",
				"quantity","objectid","sold","datesold").toList();
		
		
		
		
		Iterator<Map<Object,Object>> itr = lst.iterator();
		
		while(itr.hasNext()) {
			Map<Object,Object> map =itr.next();
			Set<Entry<Object,Object>> entry=map.entrySet();
			Iterator<Entry<Object,Object>> itr2=entry.iterator();
			ListingsDao dao = new ListingsDao();
			while(itr2.hasNext()) {
				Entry<Object,Object> entry2 =itr2.next();
				
				
				String key =(String)entry2.getKey();
				
				
				
				if(key.equals("ownerusername"))
					dao.setOwner((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("product"))
					dao.setName((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("shortdescription"))
					dao.setShortdescription((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("thumbnailimage"))
					dao.setThumbnailimage((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("saleprice"))
					dao.setSaleprice((double)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("salepricerange"))
					dao.setSalespricerange((String)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("views"))
					dao.setViews((int)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("quantity"))
					dao.setQuantity((int)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("objectid"))
					dao.setObjectid((long)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("sold"))
					dao.setSold((boolean)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("datesold"))
					dao.setDateSold((String)((ArrayList)entry2.getValue()).get(0));
					/*}
					
					
				}*/
			}
			
			dao = setListingEdges(dao);
			list.add(new ListingsDao(dao));
		}
		
		
		
		//System.err.println(lst);
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return list;
}

public static List<ListingsDao> getListingsUserCategory(String username,List<String> ecommunities,
		String category, List<String> types) {
	ArrayList<ListingsDao> list = new ArrayList<ListingsDao>();
	try {
		List<Map<Object,Object>> lst = g.V().has("Category","name",category)
		.in("iscategory").as("lst")
		.in("post","shared")
		.has("User","username",username)
		.out("iscommunity")
		.has("Ecommunity","name",P.within((Collection)ecommunities))
		.select("lst")
		.out("istype").has("Type","name",P.within((Collection)types))
		.select("lst")
		.dedup()
		.valueMap("ownerusername","product","shortdescription",
				"thumbnailimage","saleprice","salepricerange","views",
				"quantity","objectid","sold","datesold").toList();
		
		
		
		
		Iterator<Map<Object,Object>> itr = lst.iterator();
		
		while(itr.hasNext()) {
			Map<Object,Object> map =itr.next();
			Set<Entry<Object,Object>> entry=map.entrySet();
			Iterator<Entry<Object,Object>> itr2=entry.iterator();
			ListingsDao dao = new ListingsDao();
			while(itr2.hasNext()) {
				Entry<Object,Object> entry2 =itr2.next();
				
				
				String key =(String)entry2.getKey();
				
				
				
				if(key.equals("ownerusername"))
					dao.setOwner((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("product"))
					dao.setName((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("shortdescription"))
					dao.setShortdescription((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("thumbnailimage"))
					dao.setThumbnailimage((String)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("saleprice"))
					dao.setSaleprice((double)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("salepricerange"))
					dao.setSalespricerange((String)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("views"))
					dao.setViews((int)((ArrayList)entry2.getValue()).get(0));
				
				else if(key.equals("quantity"))
					dao.setQuantity((int)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("objectid"))
					dao.setObjectid((long)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("sold"))
					dao.setSold((boolean)((ArrayList)entry2.getValue()).get(0));
				else if(key.equals("datesold"))
					dao.setDateSold((String)((ArrayList)entry2.getValue()).get(0));
					/*}
					
					
				}*/
			}
			
			dao = setListingEdges(dao);
			list.add(new ListingsDao(dao));
		}
		
		
		
		//System.err.println(lst);
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	
	return list;
}

public static double getMaxPrice(String username,List<String> ecommunities,List<String>types) {
	
	double maxSalePrice =0.0;
	
	try {
 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 
		 System.err.println(LocalDateTime.now()+" Max Start................. query numbers");
		 System.err.println("username:"+username);
		 System.err.println("ecommunities:"+ecommunities);
		 System.err.println("types:"+types);
		Comparable comp= g.V().has("Ecommunity","name",P.within((Collection)ecommunities))
		.in("iscommunity").not(has("username",username))
		.out("post").as("lst")
		.out("istype").has("Type","name",P.within((Collection)types)).select("lst")
		.values("saleprice")
		.max()
		.next();
		
		System.err.println(LocalDateTime.now()+" Max Finish................. query numbers");
		
		maxSalePrice=(double)comp;
		
		/*Iterator<Comparable> itr = lst.iterator();
		
		while(itr.hasNext()) {
			Comparable comp =itr.next();
			maxSalePrice=(double)comp;
			
		}*/
		
	}catch(Exception e) {
		System.err.println(e.getLocalizedMessage());
		maxSalePrice=0;
	}
	
	return maxSalePrice;
	
}

}
