package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component           // spring bean
public class FileManagerService {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;   // 이미지 업로드 실패시 null 리턴
		}
		
		// 파일업로드 성공인 경우 웹이미지 url path를 리턴
		// 주소는 이렇ㄱ데 딜 것이다.(예상)
		// images/userLoginId_178945646/sun.png
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}

}
