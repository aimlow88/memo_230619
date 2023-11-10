package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value="prevId", required = false) Integer prevIdParam,
			@RequestParam(value="nextId", required = false) Integer nextIdParam,
			Model model, HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 비로그인이면 로그인페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int nextId = 0;
		int prevId = 0;
		if (postList.isEmpty() == false) {
			// postList가 비어 있을 때 오류를 방지하기 위함. myBatis는
			nextId = postList.get(postList.size() - 1).getId(); // 가져온 리스트의 가장 끝 값의 ID
			prevId = postList.get(0).getId(); // 가져온 리스트의 첫번째 값의 ID
			
			//이전 방향의 끝인가?
			// prevId와 post테이블의 가장 큰 id값과 같다면 이전 페이지 X
			if (postBO.isPrevLastpageByUserId(prevId, userId)) {
				prevId = 0;
			}
			
			//다음 방향의 끝인가?
			// nextId와 post테이블의 가장 작은 id값과 같다면 다음 페이지 X
			if (postBO.isNextLastPageByUserId(nextId, userId)) {
				nextId = 0;
			}
		}
		
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/postList");
		return "template/layout";
	}
	
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		
		model.addAttribute("viewName", "post/postCreate");
		
		return "template/layout";
	}
	
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		
		int userId = (int)session.getAttribute("userId");
		
		// DB Select
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}

}
