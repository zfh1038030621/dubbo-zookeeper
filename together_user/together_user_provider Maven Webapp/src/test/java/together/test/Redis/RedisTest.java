package together.test.Redis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import together.user.provider.redis.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
public class RedisTest {
	@Resource
	private Cache testCache;
	@Test
	public void test(){ 
		Map<byte[],byte[]> map =new HashMap<byte[],byte[]>();
		map.put("name".getBytes(), "账房会1".getBytes());
		map.put("sex".getBytes(), "男".getBytes());
		//testCache.put("user1", map);
		//testCache.put("user3", map);
		//testCache.put("user3", map);
		//testCache.put("name", "zfh");
		testCache.putMap("user1", map);
		System.out.println(testCache.getMap("user1"));
		//System.out.println(testCache.get("name"));
		//System.out.println(testCache.getMap("user2"));
	}
	
}
