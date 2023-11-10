package com.memo.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.domain.Post;

@Repository
public interface PostMapper {
	
	public List<Post> selectPostListByUserId(
			@Param("userId") int userId,
			@Param("direction") String direction,
			@Param("standardId") Integer standardId,
			@Param("limit") int limit
			);
	
	public int selectPostIdByUserIdAndSort(
			@Param("userId") int userId,
			@Param("sort") String sort
			);
	
	public void insertPost(Post post);
	
	public Post selectPostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId );
	
	public void upadtePostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public void deletePostByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);

}
