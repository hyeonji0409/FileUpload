package com.artineer.artineer_page.service;

import com.artineer.artineer_page.entity.File;
import com.artineer.artineer_page.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public Long saveFile(MultipartFile files) throws IOException {

        if(files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String originalFiename = files.getOriginalFilename();

        // 파일 이름 -> UUID 설정
        String uuid = UUID.randomUUID().toString();

        // 확장자
        // 사진.jpg -> 사진의 값을 uuid로 변환, .jpg는 그대로 살리는
        String extension = originalFiename.substring(originalFiename.lastIndexOf("."));

        String savedFileName = uuid + extension;

        String savedFilePath = fileDir + savedFileName;

        // Entity
        File file = File.builder()
                .originalFilename(originalFiename)
                .savedFilename(savedFileName)
                .savedPath(savedFilePath)
                .build();

        files.transferTo(new java.io.File(savedFilePath));

        File savedFile = fileRepository.save(file);

        return savedFile.getId();

    }
}
