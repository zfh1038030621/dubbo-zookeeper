package together.user.provider.service;

import together.user.provider.bean.UserInfo;

public interface UserInfoService {
/**
 * 用户信息认证
 */
	public UserInfo authentification(UserInfo userInfo);
}
