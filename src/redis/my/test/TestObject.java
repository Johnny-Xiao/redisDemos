package redis.my.test;

import redis.clients.jedis.Jedis;

public class TestObject {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.7.197", 6379);
		String keys = "name";
		// 删数据
		// jedis.del(keys);
		// 存数据
		jedis.set(keys, "zy");
		// 取数据
		String value = jedis.get(keys);
		System.out.println(value);

		// 存对象
		Person p = new Person(); // peson类记得实现序列化接口 Serializable
		p.setAge(20);
		p.setName("姚波");
		p.setId(1);
		jedis.set("person".getBytes(), SerializeUtil.serialize(p));
		byte[] byt = jedis.get("person".getBytes());
		Object obj = SerializeUtil.unserizlize(byt);
		if (obj instanceof Person) {
			System.out.println(((Person) obj).getAge());
			System.out.println(((Person) obj).getId());
			System.err.println(((Person) obj).getName());
		}
	}
}
