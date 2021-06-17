package com.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisAdapter {
	private static JedisPool pool;
	private static RedisAdapter instance;
	private static Jedis jedis;
	
	static {
		instance = new RedisAdapter();
	}
	
	private RedisAdapter(){
		
		 pool = new JedisPool(new JedisPoolConfig(), "localhost");
		
	}
	
	public static void main(String[] args) {
		long value =RedisAdapter.getLongKeyNextValue("test");
		System.out.println("Redis:"+value);
	}
	
	private static Jedis getConnection() {
		
		jedis = pool.getResource();
		
		return jedis;
		
	}
	
	private static void closeDirectConnection() {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	private static void destroyPool() {

		// Close the connection
		if (jedis != null) {
			jedis.close();
		}
		
		// Destroy the pool
		if (pool != null) {
			pool.destroy();
		}
	}
	
	public static long getLongKeyNextValue(String property) {
		long lgproperty;
		
		getConnection();
		if(!jedis.exists(property)) {
			jedis.set(property, 2+"");
			lgproperty=1;
		}else {
			lgproperty=Long.parseLong(jedis.get(property));
			jedis.incr(property);
		}
		
		closeDirectConnection();
		return lgproperty;
	}
	

}
