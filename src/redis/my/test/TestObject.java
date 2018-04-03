package redis.my.test;

import redis.clients.jedis.Jedis;

public class TestObject {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.7.197", 6379);
		String keys = "name";
		// ɾ����
		// jedis.del(keys);
		// ������
		jedis.set(keys, "zy");
		// ȡ����
		String value = jedis.get(keys);
		System.out.println(value);

		// �����
		Person p = new Person(); // peson��ǵ�ʵ�����л��ӿ� Serializable
		p.setAge(20);
		p.setName("Ҧ��");
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
