package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체생성자
@NoArgsConstructor // 빈생성자 
@Data
public class UserProfileDto {

	private boolean PageOwnerState;
	private User user;
	private boolean subscribeState;
	private int subscribeCount;
	private int imageCount;
}
