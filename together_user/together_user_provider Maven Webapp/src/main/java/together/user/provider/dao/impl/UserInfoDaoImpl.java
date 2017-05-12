package together.user.provider.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import together.user.provider.bean.UserInfo;
import together.user.provider.dao.UserInfoDao;
import together.user.provider.mapper.UserInfoMapper;
@Component
public class UserInfoDaoImpl implements UserInfoDao {
@Resource
private UserInfoMapper userInfoMapper;
	public UserInfo findUser(UserInfo userInfo) {
		return userInfoMapper.findUser(userInfo.getUserName(),userInfo.getPassword());
	}

}
