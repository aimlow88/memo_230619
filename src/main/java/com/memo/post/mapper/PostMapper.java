package com.memo.post.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.memo.post.domain.Post;

@Repository
public interface PostMapper {
	
	public List<Post> selectPostListByUserId(int userId);
	
	public void insertPost(Post post);

}
