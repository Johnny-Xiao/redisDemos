package redis.my.test;

import redis.clients.jedis.Jedis;

public class TestPing {
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.7.197", 6379);
		System.out.println(jedis.ping());
		jedis.set("k1", "v1");
		String k1 = jedis.get("k1");
		System.out.println(k1);
		jedis.close();
	}
}
