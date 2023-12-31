package com.memo.post.bo;

import java.util.Collection;
import java.util.Collections;
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
	
	private static final int POST_MAX_SIZE = 3;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	// input : userId   output : List<Post>
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId){
		// 게시글 번호 : 10 9 8| 7 6 5 | 4 3 2 | 1
		//만약 4 3 2 체이지에 있을 때
		// 1) 다음 : 2보다 작은 3개 DESC
		// 2) 이전 : 4보다 큰 3게 ASC(5 6 7)
		// 3) 첫페이지 이전, 다음 없음,DESC 3개
		String direction = null;    // 방향
		Integer standardId = null;  // 기준이 되는 postId
		if (prevId != null) {
			direction = "prev";
			standardId = prevId;
			
			List<Post> postList = postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
			
			Collections.reverse(postList);
			
			return postList;
			
		} else if (nextId != null) {
			direction = "next";
			standardId = nextId;
		}
		
		// 첫페이지 or 다음
		return postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
	}
	
	//이전 페이지의 마지막인지?
	public boolean isPrevLastpageByUserId(int prevId, int userId) {
		int postId = postMapper.selectPostIdByUserIdAndSort(userId, "DESC");
		return postId == prevId;  // 같으면 true, 아니면 false
	}
	
	//다음 페이지의 마지막인지?
	public boolean isNextLastPageByUserId(int nextId, int userId) {
		int postId = postMapper.selectPostIdByUserIdAndSort(userId, "ASC");
		return postId == nextId;
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
