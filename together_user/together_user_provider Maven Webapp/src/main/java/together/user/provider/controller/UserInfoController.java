package together.user.provider.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import together.user.provider.bean.UserInfo;
import together.user.provider.common.WebContent;
import together.user.provider.redis.Cache;
import together.user.provider.service.UserInfoService;
import together.user.provider.util.CookieUtil;
import together.user.provider.util.SessionIDUtil;
import together.user.provider.util.UUIDUtil;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
@Resource
private UserInfoService userInfoService;
@Resource
private Cache loginUserCache;
/**
 * 用户登录页面
 */
	
	@RequestMapping("/aut")
	public String aut(UserInfo userInfo,HttpServletRequest request,HttpServletResponse response){
		userInfo.setUserName("zfh");
		userInfo.setPassword("123456");
		UserInfo loginUser=userInfoService.authentification(userInfo);
		if(loginUser!=null){
			String sessionId=UUIDUtil.formatedUUID();
			CookieUtil.setCookie(response,WebContent.USERLOGINSESSIONID , sessionId);
			loginUserCache.put(sessionId, loginUser.getUserName());
		}
		return "";
		
	}
	
	
}
