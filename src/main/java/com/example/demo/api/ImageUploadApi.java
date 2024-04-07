package com.example.demo.api;

import com.example.demo.entity.ImageItem;
import com.example.demo.payload.response.MessageResponse;
import java.io.IOException;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@RequestMapping("api/image")
@CrossOrigin
public interface ImageUploadApi {

  @PostMapping("/upload")
  public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
      Principal principal) throws IOException;

  @CrossOrigin
  @GetMapping("/profileImage")
  public ResponseEntity<ImageItem> getImageForUser(Principal principal);

  @PostMapping("/searchUser/profileImage/{username}")
  public ResponseEntity<ImageItem> getImageForSearchUser(@PathVariable("username") String username);
}
