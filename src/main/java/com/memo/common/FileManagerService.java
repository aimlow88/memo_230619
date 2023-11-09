package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component           // spring bean
public class FileManagerService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//실제 업로드가 되는 이미지가 저장될 경로(서버)
	public static final String FILE_UPLOAD_PATH = "D:\\5_Project\\memo\\workspace\\images/";
	
	// input: userLoginId, file(이미지)             output: web imagePath
	public String saveFile(String loginId, MultipartFile file) {
		// 폴더 생성
		// 예: loginId_175839445
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName; 
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			// 폴더 생성 실패시
			return null;   // 이미지 경로 
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes();
			// ★★★ 한글 이름 이미지는 업로드할 수 없음. 나중에 영문자로 변경 ★★★
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename()); // 디렉토리 경로 + "/" + 파일명
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("[이미지 업로드] 이미지 업로드 실패 loginId:{} filePath:{}", loginId, filePath);
			return null;   // 이미지 업로드 실패시 null 리턴
		}
		
		// 파일업로드 성공인 경우 웹이미지 url path를 리턴
		// 주소는 이렇ㄱ데 딜 것이다.(예상)
		// images/userLoginId_178945646/sun.png
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	// input:imagePath                              output:X
	public void deleteFile(String imagePath) {
		// D:\\5_Project\\memo\\workspace\\images//images/aimlow_1698923969043/UtJtFmFLCiD.png
		// 위의 주소에서 겹치는 /images/를 지워야 한다.
		Path path  = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) { // 이미지가 존재하는가?
			
			// 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("[이미지 삭제] 파일 삭제 실패 imagePath:{}", imagePath);
				return;
			}
			
			// 폴더(디렉토리) 삭제
			path = path.getParent();  // 상위 폴더로 올라감
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("[폴더 삭제] 폴더 삭제 실패 imagePath:{}", imagePath);
				}
			}
		}
		
	}

}
