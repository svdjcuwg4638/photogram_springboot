package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹 flush(반영) x
	public Page<Image> 이미스토리(int principalId,Pageable pageable){
		Page<Image> images = imageRepository.mStrory(principalId,pageable);
		
		// 2(cos) 로그인
		// images에 좋아요 상태 담기
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) { // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
	@Value("${file.path}")
	private String uploadForder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // uuid
		String imageFileName= uuid +"_"+imageUploadDto.getFile().getOriginalFilename(); 
//		 실제 파일 이름이 담기게 된다.
//		 파일을 저장할때 똑같은 파일명이 들어오게 된다면 
//		 똑같은 이름으로 또 들어와 원래있던 사진에 덮어 씌우게 된다
//		 이를 방지하기 위해 UUID를 사용하자
//		 하지만 UUID라도 랜덤으로 값을 뽑기때문에 같은 값을 뽑을 확률이 몇십억분의 일이지만
//		 그것마저도 방지하기위해서 uuid뒤에 원래 이미지의 이름까지 더해주게된다.
//		 그렇다면 99.9999999%는 위와 같은일이 없을것이다.
		
		Path imageFilePath = Paths.get(uploadForder+imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있다.
		// runtime시에만 발견된다 그래서 무조건 위와같은 것을 실행할땐 예외처리를 무조건 해주어야 한다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		imageRepository.save(image);
	}

	@Transactional(readOnly = true)
	public List<Image> 인기사진() {
		
		return imageRepository.mPopular();
	}
	
}
