package redis.my.test;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTX {

	public boolean transMethod(){
		Jedis jedis = new Jedis("192.168.7.197", 6379);
		try {
			
			int balance;// 可用余额
			int debt;// 欠额
			int amtToSubtract = 10;// 实刷额度
			jedis.watch("balance");
			balance = Integer.parseInt(jedis.get("balance"));
			//Thread.sleep(6000);
			if (balance < amtToSubtract) {
				jedis.unwatch();
				System.out.println("motify");
				return false;
			} else {
				System.out.println("***********transaction");
				Transaction transaction = jedis.multi();
				transaction.decrBy("balance", amtToSubtract);
				transaction.incrBy("debt", amtToSubtract);
				List<Object> objects = transaction.exec();
				if(objects == null) {
					return false;
				}
				
				balance = Integer.parseInt(jedis.get("balance"));
				debt = Integer.parseInt(jedis.get("debt"));

				System.out.println("*******" + balance);
				System.out.println("*******" + debt);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jedis.close();
		}
		return false;
	}

	public static void main(String[] args){
		TestTX testTX = new TestTX();
		boolean flag = testTX.transMethod();
		System.out.println(flag);
	}
}
