package com.example.idp.cross_site_script.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Slf4j
public class AllDataController {

  @GetMapping("/getAll")
  public void getDataAll(@RequestParam("data") String data) {
    log.info(data);
  }
}
