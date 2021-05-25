package com.example.snowball.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService {


  public void uploadFile(MultipartFile file) throws IOException {
    File convertedFile = convert(file);
    PDDocument loadedPdf = PDDocument.load(convertedFile);
    int numberOfPages = loadedPdf.getNumberOfPages();

    //Retrieving the page
    PDPage page = loadedPdf.getPage(0);

    //Creating PDImageXObject object
    PDImageXObject pdImage = PDImageXObject.createFromFile("/Users/brwnsugr/Documents/my_logo_210502.png", loadedPdf);

    //Creating the PDPageContentStream object
    PDPageContentStream contents = new PDPageContentStream(loadedPdf, page, AppendMode.APPEND, true);

    //Drawing the image in the PDF document
    contents.drawImage(pdImage, 1, 1,1,1);

    System.out.println("Image Inserted");

    contents.close();

    loadedPdf.save(file.getOriginalFilename());
    loadedPdf.close();
    System.out.println("ddd");
  }

  private File convert(MultipartFile multipartFile) throws IOException {
    File convFile = new File(multipartFile.getOriginalFilename());
    convFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(multipartFile.getBytes());
    fos.close();
    return convFile;
  }

}
