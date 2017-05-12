package together.user.provider.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * cookie相关工具类
 * 
 * @author Robin 2014年6月27日 下午5:06:19
 */
public class CookieUtil {

    private final static String defEncode      = "UTF-8";

    private static final Log    log            = LogFactory.getLog(CookieUtil.class);

    public final static String  LOCAL_LANGUAGE = "gsmeeting.language.locale";

    public static int           maxAge         = 30 * 60;

    /**
     * 根据名称取Cookie
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        return getCookieValue(request, name, defEncode);
    }

    /**
     * 根据名称取得请求中的cookie对象
     * 
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null && null != name) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    /**
     * 取得cookie值，经过编码转换
     * 
     * @param request
     * @param name
     * @param encode
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name, String encode) {
        if (encode == null || "".equals(encode.trim())) {
            encode = defEncode; // 默认"UTF-8"
        }
        String val = getCookiePriValue(request, name);
        if (val == null) return null;
        try {
            return URLDecoder.decode(val, encode);
        } catch (UnsupportedEncodingException e) {
            log.error("decode cookie value failed, val=" + val, e);
            return val;
        }
    }

    /**
     * 获取未做编码转换的cookie原始值(不推荐使用)
     */
    public static String getCookiePriValue(HttpServletRequest request, String name) {
        Cookie sCookie = getCookie(request, name);
        if (sCookie == null) return null;
        return sCookie.getValue();
    }

    /**
     * 清除Cookie(清空Cookie值，使立即生效)
     * 
     * @param response
     * @param name
     */
    public static void clearCookie(HttpServletResponse response, String name) {
        setCookie(response, name, "");
    }

    /**
     * 删除Cookie(设置成空串，使立即清空)
     * 
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 删除Cookie(设置成空串，使立即清空)
     * 
     * @param response
     * @param name
     * @param domain
     * @param path
     */
    public static void removeCookie(HttpServletResponse response, String name, String domain, String path) {
        Cookie killMyCookie = new Cookie(name, "");
        killMyCookie.setMaxAge(0);
        killMyCookie.setDomain(domain);
        killMyCookie.setPath(path);
        response.addCookie(killMyCookie);
    }

    /**
     * 删除Cookie(设置成空串，使立即清空)
     * 
     * @param response
     * @param name
     * @param path
     */
    public static void removeCookie(HttpServletResponse response, String name, String path) {
        Cookie killMyCookie = new Cookie(name, "");
        killMyCookie.setMaxAge(0);
        killMyCookie.setPath(path);
        response.addCookie(killMyCookie);
    }

    /**
     * 删除所有Cookie
     * 
     * @param request
     * @param response
     */
    public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || cookies.length <= 0) return;
        for (int i = 0; i < cookies.length; i++) {
            Cookie sCookie = cookies[i];
            sCookie.setValue(null);
            sCookie.setPath("/");
            sCookie.setMaxAge(0);
            response.addCookie(sCookie);
        }
    }

    /**
     * 设置cookie的值
     * 
     * @param name
     * @param value
     * @return
     */
    public static Cookie cookie(String name, String value) {
        Cookie _cookie;
        try {
            _cookie = new Cookie(name, URLEncoder.encode(value, defEncode));
        } catch (UnsupportedEncodingException e) {
            _cookie = new Cookie(name, value);
            log.error("decode cookie value failed, value=" + value, e);
        }
        return _cookie;
    }

    /**
     * 设置为负值，为浏览器进程Cookie(内存中保存)，关闭浏览器就失效 设置session时需要
     * 
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, -1, false);
    }

    /**
     * 置为负值，为浏览器进程Cookie(内存中保存)，关闭浏览器就失效 设置session时需要
     * 
     * @param response
     * @param name
     * @param value
     * @param secure 是否只支持https
     */
    public static void setCookie(HttpServletResponse response, String name, String value, boolean secure) {
        setCookie(response, name, value, -1, secure);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int expiry, boolean secure) {
        Cookie _cookie = cookie(name, value);
        _cookie.setMaxAge(expiry);// how many seconds
        _cookie.setPath("/");
        response.addCookie(_cookie);
    }

    /**
     * 设置为负值，为浏览器进程Cookie(内存中保存)，关闭浏览器就失效 设置session时需要
     * 
     * @param response
     * @param name
     * @param value
     * @param expiry
     * @param domain
     * @param path
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int expiry, String domain,
                                 String path) {
        Cookie _cookie = cookie(name, value);
        _cookie.setMaxAge(expiry);// how many seconds
        _cookie.setDomain(domain);
        _cookie.setPath(path);
        response.addCookie(_cookie);
    }

    /**
     * 只支持https的cookie设置
     * 
     * @param response
     * @param name
     * @param value
     * @param expiry
     * @param domain
     * @param path
     */
    public static void setSecureCookie(HttpServletResponse response, String name, String value, int expiry,
                                       String domain, String path) {
        Cookie _cookie = cookie(name, value);
        _cookie.setMaxAge(expiry);// how many seconds
        _cookie.setDomain(domain);
        _cookie.setPath(path);
        _cookie.setSecure(true);
        response.addCookie(_cookie);
    }

}
