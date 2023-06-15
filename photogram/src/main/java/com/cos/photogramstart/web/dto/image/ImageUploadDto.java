package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;


@Data
public class ImageUploadDto {

	// Byte형태는 @NotBlak를 사용할 수 없다.(사진파일 같은)
	
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl,User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
