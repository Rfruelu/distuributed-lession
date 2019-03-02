package com.lujia;

import com.lujia.domain.User;
import com.lujia.domain.UserDto;
import com.lujia.mapper.UserConvertMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoMapStructApplicationTests {

	@Test
	public void contextLoads() {


		//Object userConvertMapper = SpringHelper.getBean("userConvertMapperImpl");
		//Object userConvertMapper = SpringHelper.getBean(UserConvertMapper.class);
		//log.info(userConvertMapper.toString());
		User user=new User();
		user.setAge(10);
		user.setName("lujia");
		user.setPassword("user");
		user.setDate(new Date());
		user.setPassword("userDto");

		UserDto userDto = UserConvertMapper.USER_CONVERT_MAPPER.userToUserDto(user);
		log.error(""+userDto);



		/*User user2 = UserConvertMapper.USER_CONVERT_MAPPER.userDtoToUser(userDto);
		log.info("user2:----"+user2);
		UserObject userObject = UserConvertMapper.USER_CONVERT_MAPPER.fromUserAndUserDto(user, userDto);
		log.info("userObejct:"+userObject);

		List<User> users=new ArrayList<>();
		User user1=new User();
		user1.setName("aaa");
		user1.setPassword("123456");
		user1.setAge(100);
		users.add(user);
		users.add(user1);
		List<UserDto> userDtos = UserConvertMapper.USER_CONVERT_MAPPER.usersToUserDtos(users);
		log.error(""+userDtos);*/



	}

}

