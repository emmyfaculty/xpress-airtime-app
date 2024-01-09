package com.xpressairtimeapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xpressairtimeapp.dto.AirtimeRequestDto;
import com.xpressairtimeapp.dto.AirtimeResponse;
import com.xpressairtimeapp.entity.User;
import com.xpressairtimeapp.service.AirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/airtime")
@RequiredArgsConstructor
public class AirtimeRequestController {

        private final AirtimeService airtimeService;

        @PostMapping("/purchase")
        public ResponseEntity<AirtimeResponse> purchaseAirtime(@RequestBody AirtimeRequestDto airtimeRequestDto) throws JsonProcessingException {

//            User user = new User();

//            BigDecimal amount = airtimeRequestDto.getAmount();
            AirtimeResponse airtimeResponse = airtimeService.purchaseAirtime(airtimeRequestDto);

            return ResponseEntity.ok(airtimeResponse);
        }

}
