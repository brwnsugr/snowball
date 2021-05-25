package com.example.snowball.web;

import com.example.snowball.service.FileService;
import com.example.snowball.web.dto.PostsSaveRequestDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class FileController {

  private final FileService fileService;

//  @PostMapping("/upload-file")
//  public String save(@RequestBody MultipartFile file) throws IOException {
//    System.out.println(file);
//    fileService.uploadFile(file);
//    System.out.println("this line is after fileService");
//    return "index";
//  }


}
