package together.user.provider.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;

import together.user.provider.common.WebContent;
import together.user.provider.controller.BaseController;
import together.user.provider.redis.Cache;
import together.user.provider.util.CookieUtil;

public class UserAuthen implements HandlerInterceptor {
	@Resource
	private Cache loginUserCache;
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String sessionId=CookieUtil.getCookieValue(request,WebContent.USERLOGINSESSIONID);
		if(StringUtils.isBlank(sessionId)){
			
		}else{
			String userName=(String) loginUserCache.get(sessionId);
			if(!StringUtils.isBlank(userName)){
				
			}
		}
		return false;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
