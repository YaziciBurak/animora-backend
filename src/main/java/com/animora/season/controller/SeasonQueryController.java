package com.animora.season.controller;

import com.animora.season.dto.SeasonDetailResponse;
import com.animora.season.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonQueryController {

    private final SeasonService seasonService;

    @GetMapping("/{id}")
    public ResponseEntity<SeasonDetailResponse> getSeasonDetail(@PathVariable Long id) {
        return ResponseEntity.ok(seasonService.getSeasonDetail(id));
    }
}
