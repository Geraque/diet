package com.example.demo.controller;

import com.example.demo.api.ImageUploadApi;
import com.example.demo.entity.ImageItem;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.service.ImageUploadService;
import java.io.IOException;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class ImageUploadController implements ImageUploadApi {

  private ImageUploadService imageUploadService;

  @Override
  public ResponseEntity<MessageResponse> uploadImageToUser(MultipartFile file,
      Principal principal) throws IOException {

    imageUploadService.uploadImageToUser(file, principal);
    return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
  }

  @Override
  public ResponseEntity<ImageItem> getImageForUser(Principal principal) {
    ImageItem userImage = imageUploadService.getImageToUser(principal);
    return new ResponseEntity<>(userImage, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ImageItem> getImageForSearchUser(String username) {
    ImageItem userImage = imageUploadService.getImageToSearchUser(username);
    return new ResponseEntity<>(userImage, HttpStatus.OK);
  }

}
