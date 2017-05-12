package together.user.provider.redis;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;



/**
 * 缓存默认实现模板
 * 
 * @author zfh 2015年12月28日 
 */
public class RedisCacheTemplate implements Cache, InitializingBean{
    // 日志对象
    protected static Logger       log     = LoggerFactory.getLogger(RedisCacheTemplate.class);
    private String                prefix;                                                     // key的前缀，加前缀是为了防止key的重复或者生成新的缓存，该值在整个应用里面不能重复
    private long                  expiry;                                                     // 缓存过期时间(秒)
    RedisTemplate<String, Object> cacheTemplate;
    //edis Select(cacheTemplate.select()) 命令用于切换到指定的数据库，数据库索引号 index 用数字值指定，以 0 作为起始索引值。
    
    
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public long getExpiry() {
		return expiry;
	}
	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}
	public RedisTemplate<String, Object> getCacheTemplate() {
		return cacheTemplate;
	}
	public void setCacheTemplate(RedisTemplate<String, Object> cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}
	public Object get(final String keyv) {
		 return cacheTemplate.execute(new RedisCallback<Object>() {  
             public Object doInRedis(RedisConnection connection)  
                     throws DataAccessException {  
                 Object object=null;
                 byte[] key = cacheTemplate.getStringSerializer().serialize(rebuildKey(keyv)); 
                 if (connection.exists(key)) { 
                     //建议使用connection.exists(key)判别键值是否存在，避免无用功
                     byte[] value = connection.get(key);  
                   /*  object = cacheTemplate.getStringSerializer()  
                             .deserialize(value);  */
                     object = cacheTemplate.getDefaultSerializer()  
                             .deserialize(value); 
                    
                 }  
                 return object;  
             }  
         });  
	}
	public boolean put(final String key, final Object value) {
		return (Boolean) cacheTemplate.execute(new RedisCallback<Object>() {  
	           @SuppressWarnings({ "rawtypes", "unchecked" })
	           //上面是去除警告作用
	                    public Boolean doInRedis(RedisConnection connection)  
	                            throws DataAccessException {  
	                        /*RedisSerializer serializer = cacheTemplate.getStringSerializer();*/
	        	   RedisSerializer serializer = cacheTemplate.getDefaultSerializer();
	                        byte[] valueBytes = serializer.serialize(value);
	                        byte[] keyBytes = rebuildKey(key).getBytes();
	                        connection.setEx(keyBytes, expiry, valueBytes);
	                        return true;  
	                    }  
	                });  
	}
	public boolean remove(final String keyv) {
		  return (Boolean) cacheTemplate.execute(new RedisCallback<Object>() {  
              public Object doInRedis(RedisConnection connection) {
                 byte[] key= cacheTemplate.getStringSerializer().serialize(rebuildKey(keyv));   
                  if (connection.exists(key)) { 
                      connection.del(); 
                  }
                  else{
                      log.info("The key may be not exists!remove cache fail : key = " + key);
                      return false;
                  }
                   
                   return true;  
               }  
           });  
	}
	public boolean putMap(final String key, final Map<byte[],byte[]> value) {
		return (Boolean) cacheTemplate.execute(new RedisCallback<Object>() {  
	           @SuppressWarnings({ "rawtypes", "unchecked" })
	           //上面是去除警告作用
	                    public Boolean doInRedis(RedisConnection connection)  
	                            throws DataAccessException {  
	                        /*RedisSerializer serializer = cacheTemplate.getStringSerializer();*/
	        	   //RedisSerializer serializer = cacheTemplate.getDefaultSerializer();
	                       // byte[] valueBytes = serializer.serialize(value);
	                        byte[] keyBytes = rebuildKey(key).getBytes();
	                        
	                        
	                        
	                        
	                      /*  目前了解的状态就是有三种方式可以直接放map
	                       	  方式就是下面三种，前面两种是可以直接放map但好像不能设置失效时间
	                       	  而最后一种可以设置失效时间，但好像要将map的key。value都换成字节，这有违背当初做这个直接放map的初衷
	                       	  所以这也需要后期解决的
	                       *
	                       */
	                        
	                        
	                     //cacheTemplate.boundHashOps(key).putAll(value);
	                       // cacheTemplate.opsForHash().putAll(key, value);
	                      connection.hMSet(keyBytes, value);
	                        connection.expire(keyBytes, expiry);
	                        return true;  
	                    }  
	                });  
	}
	public boolean edit(String key, Map value) {
		// TODO Auto-generated method stub
		return false;
	}
	public Map getMap(final String keyv) {
		 return (Map) cacheTemplate.execute(new RedisCallback<Object>() {  
             public Object doInRedis(RedisConnection connection)  
                     throws DataAccessException {  
                 Map object=null;
                 byte[] key = cacheTemplate.getStringSerializer().serialize(rebuildKey(keyv));
                 //object=connection.hGetAll(key);
                 //object= cacheTemplate.opsForHash().entries(keyv);
                 if (connection.exists(key)) { 
                     //建议使用connection.exists(key)判别键值是否存在，避免无用功
                	 
                	 //网上操作数据有两种方式，
                	 //object=connection.hGetAll(key);
                	 System.out.println("1243");
                	 //object=connection.hGetAll(key);
                	object= cacheTemplate.opsForHash().entries(keyv);

                	 }
                    
                 return object;  
             }
         });  
	}
	
	
	public Object getValByKey(final String keyv, final String field) {
		 return  cacheTemplate.execute(new RedisCallback<Object>() {  
             public Object doInRedis(RedisConnection connection)  
                     throws DataAccessException {  
                 Map object=null;
                 byte[] key = cacheTemplate.getStringSerializer().serialize(rebuildKey(keyv));  
                 byte[] fieldBt = cacheTemplate.getStringSerializer().serialize(rebuildKey(field));  
                 if (connection.exists(key)) { 
                     //建议使用connection.exists(key)判别键值是否存在，避免无用功
                	// connection.hMGet(key, fieldBt);
                	 //object= cacheTemplate.opsForHash().entries(keyv);
                	 object= (Map) cacheTemplate.opsForHash().get(keyv, fieldBt);

                	 }
                    
                 return object;  
             }
         });
	}
	
	public boolean del(String key, String field) {
		// TODO Auto-generated method stub
		return false;
	}
    
	/**
     * 重新生成key，加入前缀,避免与其它重复
     * 
     * @param key
     * @return
     */
    private String rebuildKey(String key) {
        return prefix + "_" + key;
}
}
