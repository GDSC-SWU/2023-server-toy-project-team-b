package com.gdsc_teamb.servertoyproject.controller;

import com.gdsc_teamb.servertoyproject.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    //좋아요 설정
    //@PostMapping

    // 좋아요 취소
    //@DeleteMapping
}
