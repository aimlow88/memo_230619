package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	// input : userId   output : List<Post>
	public List<Post> getPostListByUserId(int userId){
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input : postId, userId    output:post
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
   	
	public void addPost(Post post, String userLoginId, MultipartFile file) {
		
		String imagePath = null;
		
		if (file != null) {
			post.setImagePath(fileManager.saveFile(userLoginId, file));
		}
		
		postMapper.insertPost(post);
	}
	
	public void updatePost(int userId, String userLoginId,
			int postId, String subject, String content, MultipartFile file) {
		
		// 기존 글을 가져온다 (이유)
		// 1. 이미지 교체시 삭제를 위해
		// 2. 업데이트 대상이 존재하는지 확인
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.error("[글 수정] post id null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// 파일(이미지)이 있다면
		// 1. 신규 이미지 업로드한다
		// 2. 신규 이미지 업로드 성공시 기존 이미지 제거 (기존 이미지가 있을 때)
		String imagePath = null;
		if (file != null) {
			// 업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			// 업로드 성공시 기존 이미지 제거(있으면)
			if (imagePath != null && post.getImagePath() != null) {
				// 업로드가 성공을 했고, 기존 이미지가 존재를 한다면 => 삭제
				// 이미지 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB 글 update
		postMapper.upadtePostByPostIdUserId(postId, userId, subject, content, imagePath);
		
	}
	
	public void deletePost(int postId, int userId) {
		
		// 삭제 대상 post 가져오기
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		
		if (post != null) {
			String imagePath = post.getImagePath();
			if (imagePath != null) {
				fileManager.deleteFile(imagePath);
			}
			postMapper.deletePostByPostIdOrUserId(postId, userId);
			
		} else {
			logger.error("[글 삭제] post id null. postId:{}, userId:{}", postId, userId);
			return;
		}
	}

}
