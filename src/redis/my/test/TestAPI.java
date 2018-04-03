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
		Set<String> keys = jedis.keys("*");//��ȡ���е�keys
		System.out.println("keys :");
		for (String key : keys) {
			System.out.print(" " + key);
		}
		System.out.println("k1 is exists? " + jedis.exists("k1"));//key�Ƿ����
		System.out.println("�ƶ�key����" + jedis.move("k2", 1));//��key�ƶ�����һ�����ݿ�
		System.out.println("Ϊ������key���ù���ʱ�䣺" + jedis.expire("k10", 10)); //Ϊ������key���ù���ʱ��
		System.out.println("�鿴���ж��������:" + jedis.ttl("k10"));//�鿴���ж�������ڣ�-1��ʾ�������ڣ�-2��ʾ�ѹ���
		System.out.println("�鿴key�����ͣ�" + jedis.type("k1"));//�鿴key������
		
		System.out.println("--------------------String---------------------------");
		//String
		jedis.set("test1", "test1");//����ָ��key��ֵ
		
		System.out.println(jedis.get("test1"));//��ȡkey��ֵ
		System.out.println(jedis.getrange("test1", 0, -1));//�����ַ����е����ַ�   ��ʼ������  -1 ��ʾ����βȫ������ 
		System.out.println(jedis.getSet("test1", "test11"));//���ؾ�ֵ��������ֵ    �ȷ��� �� ����   
		jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");//ͬʱ���� һ������ key-value ��ֵ��
		System.out.println(jedis.mget("str1", "str2", "str3"));//ͬʱ��ȡһ������ valueֵ 
		System.out.println(jedis.setex("test2", 10, "test2"));//����key��ֵͬʱ���ù���ʱ��   ����Ϊ��λ
		System.out.println(jedis.psetex("test3", 100, "test3"));//����key��ֵͬʱ���ù���ʱ��   �Ѻ���Ϊ��λ
		jedis.setnx("test2", "test22");//��û��keyֵʱ����key��ֵ
		System.out.println(jedis.get("test2"));
		jedis.setrange("test1", 1, "aa");//�޸��ַ�����ֵ �Ӹ�����λ�ÿ�ʼ
		System.out.println(jedis.get("test1"));
		System.out.println(jedis.strlen("test1"));//��ȡvalueֵ����
		jedis.msetnx("str1", "v11", "str2", "v22", "str3", "v33", "str4", "v44");//�����е�keyֵʱ��������ʱ��ͬʱ���� һ������ key-value ��ֵ��
		System.out.println(jedis.mget("str1", "str2", "str3", "str4"));
		System.out.println(jedis.incr("balance"));//��value�����Զ� �� +1
		System.out.println(jedis.decr("balance"));// ��value�����Զ��� -1
		System.out.println(jedis.incrBy("balance", 10));//��value���ּ�ָ������ֵ
		System.out.println(jedis.decrBy("balance", 10));//��vaule���ּ�ָ������ֵ
		
		System.out.println("--------------------List---------------------------");
		jedis.lpush("mylist_lpush", "v1", "v2", "v3", "v4", "v5");//��һ������ֵ�����б��ͷ��
		List<String> mylist_lpush = jedis.lrange("mylist_lpush", 0, -1);//ȡ��ָ�������ֵ  -1 ��ʾȫ��
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lpushx("mylist_lpush", "v6");//��ֵ�������е��б��ͷ�� ֻ�ܲ����Ѿ����ڵ�key,��һ��ֻ�ܲ���һ��
		for (String element : jedis.lrange("mylist_lpush", 0, -1)) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpush("myList_rpush", "v1", "v2", "v3", "v4", "v5");//��һ������ֵ�����б��β��
		List<String> myList_rpush = jedis.lrange("myList_rpush", 0, -1);
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpushx("myList_rpush", "v6");//��ֵ�������е��б��β��  ֻ�ܲ����Ѿ����ڵ�key,��һ��ֻ�ܲ���һ��
		for (String element : jedis.lrange("myList_rpush", 0, -1)) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lpop("mylist_lpush");//�Ƴ�����ȡ�б�ĵ�һ��Ԫ��
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpop("mylist_lpush");//�Ƴ�����ȡ�б�����һ��Ԫ��
		for (String element : mylist_lpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		System.out.println(jedis.lindex("mylist_lpush", 2));//��ȡָ�������ϵ�ֵ
		System.out.println(jedis.llen("mylist_lpush"));//��ȡ�б�ĳ���
		
		jedis.lpush("mylist_test", "v1", "v1", "v1", "v4", "v5");
		jedis.lrem("mylist_test", 0, "v1");//�Ƴ��б���ָ��������ֵ  0 ��ʾ�Ƴ�ȫ����ֵ
		List<String> mylist_test = jedis.lrange("mylist_test", 0, -1);
		for (String element : mylist_test) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.ltrim("mylist_test", 1, 2);//��ȡ�б�ָ����Χ��ֵ����ֵ��key
		List<String> mylist_test_ltrim = jedis.lrange("mylist_test", 0, -1);
		for (String element : mylist_test_ltrim) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.rpoplpush("mylist_test", "myList_rpush");//�Ƴ��б�����һ��Ԫ�أ������뵽��һ���б�ĵ�һ��Ԫ��
		for (String element : mylist_test) {
			System.out.print(" " + element);
		}
		System.out.println();
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.lset("myList_rpush", 0, "v11");//�����б���ĳ��λ�õ�ֵ
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.linsert("myList_rpush", LIST_POSITION.BEFORE, "v3", "oracle");//���б��ָ��Ԫ��ǰ�����һ��ֵ ������յ�pivot�����ڲ����롣����ж��pivot�������ͷ�����Ϊ׼
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.linsert("myList_rpush", LIST_POSITION.AFTER, "v3", "mysql");//���б��ָ��Ԫ�غ������һ��ֵ ������յ�pivot�����ڲ����롣����ж��pivot�������ͷ�����Ϊ׼
		for (String element : myList_rpush) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println("--------------------Set---------------------------");
		
		jedis.sadd("sadd_test", "s1", "s2", "s3");//���һ������Ԫ��
		System.out.println(jedis.scard("sadd_test"));//��ȡ���ϵ�����
		jedis.sadd("sadd_test2", "s2", "s3", "s4");
		Set<String> sdiffs = jedis.sdiff("sadd_test", "sadd_test2");//��ö�����ϲ
		for (String element : sdiffs) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.sdiffstore("sadd_test3", "sadd_test", "sadd_test2");//��ȡ������ϵĲ�����浽��һ��������
		Set<String> sdiffstores = jedis.smembers("sadd_test3");//��ȡ�����е�����Ԫ��
		for (String element : sdiffstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<String> sunions = jedis.sunion("sadd_test", "sadd_test2");//��ȡ���ϵĲ���
		for (String element : sunions) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.sunionstore("sadd_test4", "sadd_test", "sadd_test3");//��ȡ���ϵĲ��������浽��һ��������
		Set<String> sunionstores = jedis.smembers("sadd_test4");
		for (String element : sunionstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<String> sinters = jedis.sinter("sadd_test", "sadd_test3");//��ȡ���ϵĽ���
		for (String element : sinters) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		jedis.sinterstore("sadd_test5", "sadd_test", "sadd_test3");//��ȡ���ϵĽ��������浽��һ��������
		Set<String> sinterstores = jedis.smembers("sadd_test5");
		for (String element : sinterstores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println(jedis.sismember("sadd_test3", "s1"));
		jedis.smove("sadd_test", "sadd_test3", "s2");//��ȡ�����е�ĳ��Ԫ���ƶ�����һ�������У� ԭ�����е�Ԫ��ɾ��
		System.out.println(jedis.spop("sadd_test3"));//�Ƴ������ؼ����е�һ�����Ԫ��
		for (String element : jedis.smembers("sadd_test3")) {
			System.out.print(" " + element);
		}
		System.out.println();
		List<String> standmembers = jedis.srandmember("sadd_test3", 2);//���ؼ��ϵ�һ���������Ԫ��
		for (String element : standmembers) {
			System.out.print(" " + element);
		}
		System.out.println();
		jedis.srem("sadd_test", "s1", "s2");//ɾ�������е�һ������Ԫ��
		for (String element : jedis.smembers("sadd_test")) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		System.out.println("--------------------Hash---------------------------");
		jedis.hset("hset_test", "k1", "1"); //���ü��ϵ�key-valueֵ
		System.out.println(jedis.hget("hset_test", "k1"));//��ȡ������ĳ��key��ֵ
		Map<String, String> hmset_map = new HashMap<String, String>();
		hmset_map.put("telphone", "13811814763");
		hmset_map.put("address", "atguigu");
		hmset_map.put("email", "abc@163.com");
		jedis.hmset("hmset_test", hmset_map);//���ü�����һ������key��ֵ
		List<String> hmgets = jedis.hmget("hmset_test", "telphone", "address", "email");//��ȡ������һ������key��ֵ
		for (String elemnt : hmgets) {
			System.out.print(" " + elemnt);
		}
		System.out.println();
		System.out.println(jedis.hlen("hmset_test"));//��ȡ���ϵĳ���
		System.out.println(jedis.hexists("hmset_test", "email"));//�жϼ������Ƿ����keyֵ
		System.out.println(jedis.hkeys("hmset_test"));//��ȡ���������е�keyֵ
		System.out.println(jedis.hvals("hmset_test"));//��ȡ���������е�valueֵ
		System.out.println(jedis.hincrBy("hset_test", "k1", 10));//������ĳ����ֵ����ָ������ֵ
		System.out.println(jedis.hincrByFloat("hset_test", "k1", 10.0));//������ĳ����ֵ��ȥָ������ֵ
		jedis.hsetnx("hmset_test", "k1", "111");//��������в��������ֵ�����ü�����ĳһ��keyֵ
		System.out.println(jedis.hget("hmset_test", "k1"));
		jedis.hdel("hmset_test", "k1");//ɾ��������ĳ��keyֵ
		System.out.println(jedis.hexists("hmset_test", "k1"));
		
		System.out.println("--------------------ZSet---------------------------");
		jedis.zadd("zadd_test", 10, "v1");//�򼯺������Ԫ��
		jedis.zadd("zadd_test", 30, "v3");
		jedis.zadd("zadd_test", 20, "v2");
		Set<String> zranges = jedis.zrange("zadd_test", 0, -1);//��ȡ���ϵ�Ԫ��
		for (String element : zranges) {
			System.out.print(" " + element);
		}
		System.out.println();
		Set<Tuple> zrangeWithScores = jedis.zrangeWithScores("zadd_test", 0, -1);//��ȡ���ϵ�Ԫ�غͷ���
		for (Tuple element : zrangeWithScores) {
			System.out.print(" " + element.getElement() + ":" + element.getScore());
		}
		System.out.println();
		
		Set<String> zrangeByScores = jedis.zrangeByScore("zadd_test", 0, 10);//ͨ��������ȡ���ϵ�Ԫ��
		for (String element : zrangeByScores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		Set<String> zrevrangeByScores = jedis.zrevrangeByScore("zadd_test", 10, 0);//ͨ��������ȡ���ϵ�Ԫ������
		for (String element : zrevrangeByScores) {
			System.out.print(" " + element);
		}
		System.out.println();
		
		Set<Tuple> zrangeByScoreWithScores = jedis.zrangeByScoreWithScores("zadd_test", 0, 20);//ͨ��������ȡ���ϵ�Ԫ�غͷ���
		for (Tuple element : zrangeByScoreWithScores) {
			System.out.print(" " + element.getElement() + ":" + element.getScore());
		}
		System.out.println();
		
		Set<Tuple> zrevrangeByScoreWithScores = jedis.zrangeByScoreWithScores("zadd_test", 20, 0);//ͨ��������ȡ���ϵ�Ԫ�غͷ�������
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
		Set<String> zrevrange = jedis.zrevrange("zadd_test", 0, -1);//��ȡ���ϵ�Ԫ������
		for (String element : zrevrange) {
			System.out.print(" " + element);
		}
		System.out.println();
		System.out.println(jedis.zcard("zadd_test"));//��ȡԪ�صĸ���
		System.out.println(jedis.zcount("zadd_test", 10, 20));//��ȡָ��������Χ�ڵ�Ԫ�ظ���
		System.out.println(jedis.zrank("zadd_test", "v1"));//��ȡԪ�ص�λ��
		System.out.println(jedis.zrevrank("zadd_test", "v1"));//��ȡԪ�ص�λ������
		System.out.println(jedis.zscore("zadd_test", "v2"));//��ȡԪ�صķ���
		
	}
}
