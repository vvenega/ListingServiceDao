package com.example.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelAdapter {
	
	private static ExcelAdapter instance;
	
	private static final String PATH="/users/victorvenegas/desktop/testupload/";
	
	static {
		instance = new ExcelAdapter();
	}
	
	private ExcelAdapter() {
		
	}
	
	public static ExcelAdapter getInstance() {
		return instance;
	}
	
	public  String  uploadExcel(String username,String namefile) {
		
		
		String outputfile;
	    int index = namefile.indexOf('.');
	    outputfile=namefile.substring(0,index);
	    String ext = namefile.substring(index);
	    outputfile = outputfile+"_out"+ext;
	    
		/*CassandraAdapter.setFileLoadRecord(username,namefile, outputfile, "procesando",PATH);*/
		try (InputStream inp = new FileInputStream(PATH+namefile)) {
			
			//InputStream inp = new FileInputStream("workbook.xlsx");
			    Workbook wb = WorkbookFactory.create(inp);
			    Sheet sheet = wb.getSheetAt(0);
			    Iterator<Row>itr= sheet.rowIterator();
			    
			    while(itr.hasNext()) {
			    	boolean error = false;
			    	String errorDescripcion="";
			    	Row row = itr.next();
			    	int number = row.getRowNum();
			    	if(number==0)
			    		continue;
			    	
			    	ListingsDao dao = new ListingsDao();
			    	dao.setOwner(username);
			    	
			    	Cell name = row.getCell(0);
			    	Cell shortDescription = row.getCell(1);
			    	Cell thumbnailImage = row.getCell(2);
			    	Cell price = row.getCell(3);
			    	Cell manufacturer = row.getCell(4);
			    	Cell type = row.getCell(5);
			    	Cell image1=row.getCell(6);
			    	Cell image2=row.getCell(7);
			    	Cell image3=row.getCell(8);
			    	Cell image4=row.getCell(9);
			    	Cell priceRange = row.getCell(10);
			    	Cell categories = row.getCell(11);
			    	Cell overview = row.getCell(12);
			    	
			    	if(name.getStringCellValue()!=null && !name.getStringCellValue().equals(""))
			    	  dao.setName(name.getStringCellValue());
			    	else {
			    		error=true;
			    		errorDescripcion+=":Ingresar Nombre de Producto";
			    	}
			    	
			    	if(shortDescription.getStringCellValue()!=null && !shortDescription.getStringCellValue().equals("")) {
			    		dao.setShortdescription(shortDescription.getStringCellValue());
			    		dao.setLongDescription(dao.getShortdescription());
			    	}else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Descripcion del Producto";
				    	}
			    	
			    	if(thumbnailImage.getStringCellValue()!=null && !thumbnailImage.getStringCellValue().equals(""))
			    		dao.setThumbnailimage(thumbnailImage.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar thumbnail Imagen";
				    	}
			    	
			    	
			    		dao.setSaleprice(price.getNumericCellValue());
				    	
			    	
			    	if(manufacturer.getStringCellValue()!=null && !manufacturer.getStringCellValue().equals(""))
			    		dao.setManufacturer(manufacturer.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Fabricante del producto";
				    	}
			    	String strType = type.getStringCellValue();
			    	if(strType!=null && !strType.equals("")
			    			&& (strType.equals("Renta") || strType.equals("Donacion")
			    				|| strType.equals("Intercambio") || strType.equals("Listing"))) {
			    		
			    		if(strType.equals("Donacion"))
			    		    dao.setType("Donation");
			    		else if(strType.equals("Renta"))
			    			dao.setType("Rent");
			    		else if(strType.equals("Intercambio"))
			    			dao.setType("Exchange");
			    		else if(strType.equals("Listing"))
			    			dao.setType("Listing");
			    	}else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Tipo del Producto; usar Donacion, Intercambio, Renta o Listing";
				    	}
			    	List<String> lstImagen = new ArrayList<String>();
			    	if(image1.getStringCellValue()!=null && !image1.getStringCellValue().equals(""))
			    		lstImagen.add(image1.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Imagen1";
				    	}
			    	
			    	if(image2.getStringCellValue()!=null && !image2.getStringCellValue().equals(""))
			    		lstImagen.add(image2.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Imagen2";
				    	}
			    	
			    	if(image3.getStringCellValue()!=null && !image3.getStringCellValue().equals(""))
			    		lstImagen.add(image3.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Imagen3";
				    	}
			    	
			    	if(image4.getStringCellValue()!=null && !image4.getStringCellValue().equals(""))
			    		lstImagen.add(image4.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Imagen4";
				    	}
			    	
			    	if(priceRange.getStringCellValue()!=null && !priceRange.getStringCellValue().equals(""))
			    		dao.setSalespricerange(priceRange.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Rango de Precio del producto";
				    	}
			    	
			    	if(overview.getStringCellValue()!=null && !overview.getStringCellValue().equals(""))
			    		dao.setOverview(overview.getStringCellValue());
				    	else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Descripcion Detallada del producto";
				    	}
			    	
			    	List<String> lstCategories=new ArrayList<String>();
			    	if(categories.getStringCellValue()!=null && !categories.getStringCellValue().equals("")) {
			    		try {
			    		lstCategories = Arrays.asList(categories.getStringCellValue().split(","));
			    		}catch(Exception e) {
			    			e.printStackTrace();
			    			error=true;
				    		errorDescripcion+=":Ingresar Categorias del producto separadas por comas";
			    		}
			    	}else {
				    		error=true;
				    		errorDescripcion+=":Ingresar Categorias del producto separadas por comas";
				    	}
			    	
			    	String resultadoInsercion="";
			    	if(error == true) {
			    		resultadoInsercion=errorDescripcion;
			    		
			    	}else {
			    		dao.setImage(lstImagen);
			    		dao.setCategory(lstCategories);
			    		dao.setQuantity(1);
			    		dao.setViews(0);
			    		resultadoInsercion=JanusGraphAdapter.addListing(dao);
			    		
			    		
			    	}
			    	
			    	Cell result = row.createCell(13);
			    	result.setCellValue(resultadoInsercion);
			    	
			    	if(error) {
			    	Cell description = row.createCell(14);
			    	description.setCellValue(errorDescripcion);
			    	}
			    	
			    	
			    }
			    
			    // Write the output to a file
			    
			    try (OutputStream fileOut = new FileOutputStream(PATH+outputfile)) {
			        wb.write(fileOut);
			    }catch(Exception e) {
			    	e.printStackTrace();
			    	
			    }
			    
			    //CassandraAdapter.updateFileLoadRecord(namefile, username, "termino.");
	}catch(Exception e) {
		e.printStackTrace();
	}
		
		return outputfile;
	}
	
	

}
