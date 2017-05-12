package together.user.provider.util;

/**
 * sessionID工具类
 * 
 * @author Robin 2014年6月27日 上午9:33:56
 */
public class SessionIDUtil {

    /**
     * 生成sessionID
     * @param request
     * @return
     */
    public static String generateSessionId() {
        return UUIDUtil.formatedUUID();
    }
}
