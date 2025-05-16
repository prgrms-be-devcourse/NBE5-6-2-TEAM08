package com.grepp.team08.app.model.image.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    // 기존에 이런 로직 짜는 다른 부분은 지우자
    @Value("${upload.path}")
    private String uploadDir; // application.properties에서 설정한 경로

    public List<String> upload(List<MultipartFile> files) {
        List<String> savedPaths = new ArrayList<>();

        String editorDirPath = uploadDir + File.separator + "editor";
        File editorDir = new File(editorDirPath);
        if (!editorDir.exists()) {
            editorDir.mkdirs(); // 경로 없으면 생성
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String extension = getExtension(originalName);
            String renamed = UUID.randomUUID().toString() + "." + extension;

            File dest = new File(editorDir, renamed);

            try {
                file.transferTo(dest);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패: " + originalName, e);
            }

            // 웹에서 접근 가능한 경로 (WebMvcConfigurer에서 매핑된 것 기준)
            savedPaths.add("/images/editor/" + renamed);
        }

        return savedPaths;
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) return "jpg"; // 기본 확장자 fallback
        return fileName.substring(dotIndex + 1);
    }
}
