package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikeRepository likeRepository;

	@Transactional
	public void 좋아요(int imageId, int principalId) {
		likeRepository.mLikes(imageId, principalId);
	}

	@Transactional
	public void 좋아요취소(int imageId, int principalId) {
		likeRepository.mUnLikes(imageId, principalId);
	}
	
	
	
}
