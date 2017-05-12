package together.user.provider.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.config.support.Parameter;

import together.user.provider.bean.UserInfo;
@Repository
public interface UserInfoMapper {
	UserInfo findUser(@Param("userName")String userName ,@Param("password")String password);
	
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}