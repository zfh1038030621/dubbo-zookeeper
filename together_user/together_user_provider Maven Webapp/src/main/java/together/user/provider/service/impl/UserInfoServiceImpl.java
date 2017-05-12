package together.user.provider.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import together.user.provider.bean.UserInfo;
import together.user.provider.dao.UserInfoDao;
import together.user.provider.service.UserInfoService;
@Service
public class UserInfoServiceImpl implements UserInfoService {
@Resource
private UserInfoDao userInfoDao;

public UserInfo authentification(UserInfo userInfo) {
	return userInfoDao.findUser(userInfo);
}
	}

