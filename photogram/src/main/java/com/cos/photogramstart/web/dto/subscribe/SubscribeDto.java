package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

	private int id;
	private String userName;
	private String profileImageUrl;
	private Integer subscribeState;
	private Integer equalUserState;
	
}
