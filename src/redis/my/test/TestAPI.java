package redis.my.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class TestAPI {
	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("192.168.7.197", 6379);
		
		//key
		System.out.println("--------------------key---------------------------");
		Set<String> keys = jedis.keys("*");//获取所有的keys
		System.out.println("keys :");
		for (String key : keys) {
			System.out.print(" " + key);
		}
		System.out.println("k1 is exists? " + jedis.exists("k1"));//key是否存在
		System.out.println("移动key到：" + jedis.move("k2", 1));//将key移动到另一个数据库
		System.out.println("为给定的key设置过期时间：" + jedis.expire("k10", 10)); //为给定的key设置过期时间
		System.out.println("查看还有多少秒过期:" + jedis.ttl("k10"));//查看还有多少秒过期，-1表示永不过期，-2表示已过期
		System.out.println("查看key的类型：" + jedis.type("k1"));//查看key的类型
		
		System.out.println("--------------------String---------------------------");
		//String
		jedis.set("test1", "test1");//设置指定key的值
		
		System.out.println(jedis.get("test1"));//获取key的值
		System.out.println(jedis.getrange("test1", 0, -1));//返回字符串中的子字符   开始到结束  -1 表示到结尾全部返回 
		System.out.println(jedis.getSet("test1", "test11"));//返回旧值并设置新值    先返回 在 设置   
		jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");//同时设置 一个或多个 key-value 键值对
		System.out.println(jedis.mget("str1", "str2", "str3"));//同时获取一个或多个 value值 
		System.out.println(jedis.setex("test2", 10, "test2"));//设置key的值同时设置过期时间   已秒为单位
		System.out.println(jedis.psetex("test3", 100, "test3"));//设置key的值同时设置过期时间   已毫秒为单位
		jedis.setnx("test2", "test22");//当没有key值时设置key的值
		System.out.println(jedis.get("test2"));
		jedis.setrange("test1", 1, "aa");//修改字符串的值 从给定的位置开始
		System.out.println(jedis.get("test1"));
		System.out.println(jedis.strlen("test1"));//获取value值长度
		jedis.msetnx("str1", "v11", "str2", "v22", "str3", "v33", "str4", "v44");//当所有的key值时都不存在时，同时设置 一个或多个 key-value 键值对
		System.out.println(jedis.mget("str1", "str2", "str3", "str4"));
		System.out.println(jedis.incr("balance"));//将value数字自动 的 +1
		System.out.println(jedis.decr("balance"));// 将value数字自动的 -1
		System.out.println(jedis.incrBy("balance", 10));//将value数字加指定的数值
		System.out.println(jedis.decrBy("balance", 10));//将vaule数字减指定的数值
		
		System.out.println("--------------------List---------------------------");
		jedis.lpush("mylist_lpush", "v1", "v2", "v3", "v4", "v5");//将一个或多个值插入列表的头部
		List<String> mylist_lpush = jedis.lrange("mylist_lpush", 0, -1);//取出指定区域的值  -1 表示全部
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lpushx("mylist_lpush", "v6");//将值插入已有的列表的头部 只能插入已经存在的key,且一次只能插入一次
		for (String element : jedis.lrange("mylist_lpush", 0, -1)) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpush("myList_rpush", "v1", "v2", "v3", "v4", "v5");//将一个或多个值插入列表的尾部
		List<String> myList_rpush = jedis.lrange("myList_rpush", 0, -1);
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpushx("myList_rpush", "v6");//将值插入已有的列表的尾部  只能插入已经存在的key,且一次只能插入一次
		for (String element : jedis.lrange("myList_rpush", 0, -1)) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lpop("mylist_lpush");//移除并获取列表的第一个元素
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpop("mylist_lpush");//移除并获取列表的最后一个元素
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		System.out.println(jedis.lindex("mylist_lpush", 2));//获取指定索引上的值
		System.out.println(jedis.llen("mylist_lpush"));//获取列表的长度
		
		jedis.lpush("mylist_test", "v1", "v1", "v1", "v4", "v5");
		jedis.lrem("mylist_test", 0, "v1");//移除列表中指定数量的值  0 表示移除全部的值
		List<String> mylist_test = jedis.lrange("mylist_test", 0, -1);
		for (String element : mylist_test) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.ltrim("mylist_test", 1, 2);//截取列表指定范围的值并赋值给key
		List<String> mylist_test_ltrim = jedis.lrange("mylist_test", 0, -1);
		for (String element : mylist_test_ltrim) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpoplpush("mylist_test", "myList_rpush");//移除列表的最后一个元素，并加入到另一个列表的第一个元素
		for (String element : mylist_test) {
			System.out.print(" " + element);
		}
		System.out.println();
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lset("myList_rpush", 0, "v11");//设置列表中某个位置的值
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.linsert("myList_rpush", LIST_POSITION.BEFORE, "v3", "oracle");//在列表的指定元素前面插入一个值 如果参照点pivot不存在不插入。如果有多个pivot，以离表头最近的为准
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.linsert("myList_rpush", LIST_POSITION.AFTER, "v3", "mysql");//在列表的指定元素后面插入一个值 如果参照点pivot不存在不插入。如果有多个pivot，以离表头最近的为准
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println("--------------------Set---------------------------");
		
		jedis.sadd("sadd_test", "s1", "s2", "s3");//添加一个或多个元素
		System.out.println(jedis.scard("sadd_test"));//获取集合的数量
		jedis.sadd("sadd_test2", "s2", "s3", "s4");
		Set<String> sdiffs = jedis.sdiff("sadd_test", "sadd_test2");//获得多个集合差集
		for (String element : sdiffs) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.sdiffstore("sadd_test3", "sadd_test", "sadd_test2");//获取多个集合的差集并保存到另一个集合中
		Set<String> sdiffstores = jedis.smembers("sadd_test3");//获取集合中的所有元素
		for (String element : sdiffstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<String> sunions = jedis.sunion("sadd_test", "sadd_test2");//获取集合的并集
		for (String element : sunions) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.sunionstore("sadd_test4", "sadd_test", "sadd_test3");//获取集合的并集并保存到另一个集合中
		Set<String> sunionstores = jedis.smembers("sadd_test4");
		for (String element : sunionstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<String> sinters = jedis.sinter("sadd_test", "sadd_test3");//获取集合的交集
		for (String element : sinters) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		jedis.sinterstore("sadd_test5", "sadd_test", "sadd_test3");//获取集合的交集并保存到另一个集合中
		Set<String> sinterstores = jedis.smembers("sadd_test5");
		for (String element : sinterstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println(jedis.sismember("sadd_test3", "s1"));
		jedis.smove("sadd_test", "sadd_test3", "s2");//获取集合中的某个元素移动到另一个集合中， 原集合中的元素删除
		System.out.println(jedis.spop("sadd_test3"));//移除并返回集合中的一个随机元素
		for (String element : jedis.smembers("sadd_test3")) {
			System.out.print(" " + element);
		}
		System.out.println();
		List<String> standmembers = jedis.srandmember("sadd_test3", 2);//返回集合的一个或多个随机元素
		for (String element : standmembers) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.srem("sadd_test", "s1", "s2");//删除集合中的一个或多个元素
		for (String element : jedis.smembers("sadd_test")) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println("--------------------Hash---------------------------");
		jedis.hset("hset_test", "k1", "1"); //设置集合的key-value值
		System.out.println(jedis.hget("hset_test", "k1"));//获取集合中某个key的值
		Map<String, String> hmset_map = new HashMap<String, String>();
		hmset_map.put("telphone", "13811814763");
		hmset_map.put("address", "atguigu");
		hmset_map.put("email", "abc@163.com");
		jedis.hmset("hmset_test", hmset_map);//设置集合中一个或多个key的值
		List<String> hmgets = jedis.hmget("hmset_test", "telphone", "address", "email");//获取集合中一个或多个key的值
		for (String elemnt : hmgets) {
			System.out.print(" " + elemnt);
		}
		System.out.println();
		System.out.println(jedis.hlen("hmset_test"));//获取集合的长度
		System.out.println(jedis.hexists("hmset_test", "email"));//判断集合中是否存在key值
		System.out.println(jedis.hkeys("hmset_test"));//获取集合中所有的key值
		System.out.println(jedis.hvals("hmset_test"));//获取集合中所有的value值
		System.out.println(jedis.hincrBy("hset_test", "k1", 10));//集合中某个数值加上指定的数值
		System.out.println(jedis.hincrByFloat("hset_test", "k1", 10.0));//集合中某个数值减去指定的数值
		jedis.hsetnx("hmset_test", "k1", "111");//如果集合中不存在这个值，设置集合中某一个key值
		System.out.println(jedis.hget("hmset_test", "k1"));
		jedis.hdel("hmset_test", "k1");//删除集合中某个key值
		System.out.println(jedis.hexists("hmset_test", "k1"));
		
		System.out.println("--------------------ZSet---------------------------");
		jedis.zadd("zadd_test", 10, "v1");//向集合中添加元素
		jedis.zadd("zadd_test", 30, "v3");
		jedis.zadd("zadd_test", 20, "v2");
		Set<String> zranges = jedis.zrange("zadd_test", 0, -1);//获取集合的元素
		for (String element : zranges) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<Tuple> zrangeWithScores = jedis.zrangeWithScores("zadd_test", 0, -1);//获取集合的元素和分数
		for (Tuple element : zrangeWithScores) {
			System.out.print(" " + element.getElement() + ":" + element.getScore());
		}
		System.out.println();
		
		Set<String> zrangeByScores = jedis.zrangeByScore("zadd_test", 0, 10);//通过分数获取集合的元素
		for (String element : zrangeByScores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		Set<String> zrevrangeByScores = jedis.zrevrangeByScore("zadd_test", 10, 0);//通过分数获取集合的元素逆序
		for (String element : zrevrangeByScores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		Set<Tuple> zrangeByScoreWithScores = jedis.zrangeByScoreWithScores("zadd_test", 0, 20);//通过分数获取集合的元素和分数
		for (Tuple element : zrangeByScoreWithScores) {
			System.out.print(" " + element.getElement() + ":" + element.getScore());
		}
		System.out.println();
		
		Set<Tuple> zrevrangeByScoreWithScores = jedis.zrangeByScoreWithScores("zadd_test", 20, 0);//通过分数获取集合的元素和分数逆序
		for (Tuple element : zrevrangeByScoreWithScores) {
			System.out.print(" " + element.getElement() + ":" + element.getScore());
		}
		System.out.println();
		
		jedis.zrem("zadd_test", "v3");
		Set<String> zranges2 = jedis.zrange("zadd_test", 0, -1);
		for (String element : zranges2) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<String> zrevrange = jedis.zrevrange("zadd_test", 0, -1);//获取集合的元素逆序
		for (String element : zrevrange) {
			System.out.print(" " + element);
		}
		System.out.println();
		System.out.println(jedis.zcard("zadd_test"));//获取元素的个数
		System.out.println(jedis.zcount("zadd_test", 10, 20));//获取指定分数范围内的元素个数
		System.out.println(jedis.zrank("zadd_test", "v1"));//获取元素的位置
		System.out.println(jedis.zrevrank("zadd_test", "v1"));//获取元素的位置逆序
		System.out.println(jedis.zscore("zadd_test", "v2"));//获取元素的分数
		
	}
}
