package com.example.demo.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/print")
@CrossOrigin
public interface PrintApi {

  @GetMapping("/{planId}")
  public ResponseEntity<byte[]> printPlan(
      @PathVariable("planId") String planId
  );
}
