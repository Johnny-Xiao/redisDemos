package redis.my.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestPool {
	
	public static void main(String[] args) {
		JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("k10", "v10");
			System.out.println(jedis.get("k10"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JedisPoolUtil.release(jedisPool, jedis);
		}
	}
}
