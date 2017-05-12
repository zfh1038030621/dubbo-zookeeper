package together.user.provider.redis;

import java.util.List;
import java.util.Map;
/**
 * cache操作接口
 * 
 * @author zfh
 */
public interface Cache {
    /**
     * 从缓存获取单条数据
     * 
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 向缓存put数据
     * 
     * @param key
     * @param value
     * @return
     */
    boolean put(String key, Object value);

    /**
     * remove数据
     * 
     * @param key
     * @return
     */
    boolean remove(String key);
    
    /**
     * 向緩存put map
     */
      boolean putMap(String key, Map<byte[],byte[]> value);
      /**
       * 修改緩存 map
       */
      boolean edit(String key, Map value);
       /**
        * 获取緩存整个 map
        */
      Map getMap(String key);
      
      /**
       * 获取緩存整个 map
       */
     Object getValByKey(String key, String field);
      
      /**
       * 删除緩存 map 某个属性
       */
     boolean del(String key, String field);
}
