package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service // IoC
public class PrincipalDetailsService implements UserDetailsService{

	// db에서 데이터를 가져와 아이디가있는지 비밀번호가 일치하는지 확인해야하니 UserRepository를 가져옴
	private final UserRepository userRepository;
	
	// 1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
	// 2. 리턴이 잘 되면 자동으로 UserDetails 세션을 만든다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 왜 이름만 가져오냐면은 비밀번호는 알아서 Ioc에서 처리해준다 일치하는지
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		}else {
			return new PrincipalDetails(userEntity);
		}
	}
}
