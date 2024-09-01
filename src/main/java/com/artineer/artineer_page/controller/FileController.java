package com.artineer.artineer_page.controller;

import com.artineer.artineer_page.entity.File;
import com.artineer.artineer_page.repository.FileRepository;
import com.artineer.artineer_page.service.FileService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FileController {
    private final FileService fileService;
    private final FileRepository fileRepository;

    // upload 페이지 이동
    @GetMapping("/upload")
    public String uploadForm() {
        System.out.println("upload page");
        return "file/upload";
    }


    // upload 동작
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files) throws IOException {
        fileService.saveFile(file);

        for(MultipartFile multipartFile : files) {
            fileService.saveFile(multipartFile);
        }

        return "redirect:/view";

    }


    // view 페이지 이동
    @GetMapping("/view")
    public String view(Model model) {
        List<File> files = fileRepository.findAll();
        model.addAttribute("allFiles", files);
        return "file/view";
    }


    // 이미지 출력
    @GetMapping("/images/{fileId}")
    @ResponseBody
    public Resource downlodeImage(@PathVariable("fileId") Long id, Model model) throws IOException{
        File file = fileRepository.findById(id).orElse(null);
        return new UrlResource("file:" + file.getSavedPath());
    }

    // 파일 다운로드
    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("id") Long id) throws MalformedURLException {
        File file = fileRepository.findById(id).orElse(null);

        assert file != null;
        UrlResource resource = new UrlResource("file:" + file.getSavedPath());


        String encodedFileName = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자 뜨도록 헤더 설정
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
