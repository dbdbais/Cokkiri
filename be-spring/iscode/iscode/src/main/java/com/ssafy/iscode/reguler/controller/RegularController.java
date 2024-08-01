package com.ssafy.iscode.reguler.controller;

import com.ssafy.iscode.reguler.model.dto.RegularRequestDto;
import com.ssafy.iscode.reguler.model.dto.RegularResponseDto;
import com.ssafy.iscode.reguler.service.RegularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regular")
public class RegularController {

    @Autowired
    private RegularService regularService;

    @PostMapping("/create-regular")
    public ResponseEntity<Long> createRegular(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            Long sessionId = regularService.createRegular(regularRequestDto);
            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
