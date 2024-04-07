package com.example.demo.service;

import com.example.demo.entity.ImageItem;
import com.example.demo.entity.UserItem;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class ImageUploadService {

  private ImageRepository imageRepository;
  private UserRepository userRepository;

  public ImageItem uploadImageToUser(
      MultipartFile file, Principal principal) throws IOException {
    UserItem user = getUserByPrincipal(principal);
    log.info("Uploading image profile to User {}", user.getUsername());

    ImageItem userProfileImage = imageRepository.findByUserId(user.getUserId()).orElse(null);
    if (!ObjectUtils.isEmpty(userProfileImage)) {
      imageRepository.delete(userProfileImage);
    }

    ImageItem imageModel = new ImageItem();
    imageModel.setUserId(user.getUserId());
    imageModel.setImageBytes(compressBytes(file.getBytes()));
    imageModel.setName(file.getOriginalFilename());
    return imageRepository.save(imageModel);
  }

  public ImageItem getImageToUser(Principal principal) {
    UserItem user = getUserByPrincipal(principal);

    ImageItem imageModel = imageRepository.findByUserId(user.getUserId()).orElse(null);
    if (!ObjectUtils.isEmpty(imageModel)) {
      imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
    }
    return imageModel;
  }

  public ImageItem getImageToSearchUser(String username) {
    UserItem user = getUserByUsername(username);

    ImageItem imageModel = imageRepository.findByUserId(user.getUserId()).orElse(null);
    if (!ObjectUtils.isEmpty(imageModel)) {
      imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
    }
    return imageModel;
  }

  private byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
      log.error("Cannot compress Bytes");
    }
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
    return outputStream.toByteArray();
  }

  private static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException | DataFormatException e) {
      log.error("Cannot decompress Bytes");
    }
    return outputStream.toByteArray();
  }

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));

  }

  private UserItem getUserByUsername(String username) {
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));

  }

  private <T> Collector<T, ?, T> toSingleRecipeCollector() {
    return Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
          if (list.size() != 1) {
            throw new IllegalStateException();
          }
          return list.get(0);
        }
    );
  }
}
