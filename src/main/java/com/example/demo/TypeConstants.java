package com.example.demo;

public class TypeConstants {
	
	private final static String CASSANDRA_SERVER_VAR="CASSANDRA_SERVER";
	private final static String CASSANDRA_PORT_VAR="CASSANDRA_PORT";
	private final static String CASSANDRA_USER_VAR="CASSANDRA_USER";
	private final static String CASSANDRA_PWD_VAR="CASSANDRA_PWD";
	private final static String NAMESPACE_VAR="NAMESPACE";
	private final static String PATH_DIRECTORY_VAR="PATH_DIRECTORY";
	
	
	public final static  String CASSANDRA_SERVER=Config.getProperty(CASSANDRA_SERVER_VAR);
	public final static int CASSANDRA_PORT=Integer.parseInt(Config.getProperty(CASSANDRA_PORT_VAR));
	public final static String CASSANDRA_USER=Config.getProperty(CASSANDRA_USER_VAR);
	public final static String CASSANDRA_PWD=Config.getProperty(CASSANDRA_PWD_VAR);
	public final static String NAMESPACE=Config.getProperty(NAMESPACE_VAR);
	public final static String PRODUCT_REQUESTED_VIEW="PRODUCT_REQUESTED_VIEWED";
	public final static String PRODUCT_REQUESTED_SOLD="PRODUCT_REQUESTED_SOLD";
	public final static String PRODUCT_REQUESTED_RED="PRODUCT_REQUESTED_RED";
	public final static String PATH_DIRECTORY=Config.getProperty(PATH_DIRECTORY_VAR);
	

}
