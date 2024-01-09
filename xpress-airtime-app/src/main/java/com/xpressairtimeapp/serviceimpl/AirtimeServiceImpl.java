package com.xpressairtimeapp.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpressairtimeapp.dto.AirtimeApiResponse;
import com.xpressairtimeapp.dto.AirtimeRequestDto;
import com.xpressairtimeapp.dto.AirtimeResponse;
import com.xpressairtimeapp.service.AirtimeService;
import com.xpressairtimeapp.utils.WalletUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AirtimeServiceImpl implements AirtimeService {

    @Value("${airtime.api.url}")
    private String apiUrl;

    @Value("${airtime.public.key}")
    private String PUBLIC_KEY;

    @Value("${airtime.private.key}")
    private String privateKey;


    private final ObjectMapper objectMapper;

    public AirtimeResponse purchaseAirtime(AirtimeRequestDto airtimeRequestDto) throws JsonProcessingException {

        var toHash =  objectMapper.writeValueAsString(airtimeRequestDto);

        var GENERATED_HMAC = calculateHMAC512(toHash, privateKey );

        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + PUBLIC_KEY);
        headers.set("PaymentHash", GENERATED_HMAC);
        headers.set("Channel", "API");
        headers.set("Content-Type", "application/json");

        var requestEntity = new HttpEntity<>(airtimeRequestDto, headers);

        try {
            var responseEntity = new RestTemplate().exchange(
                    apiUrl, HttpMethod.POST, requestEntity, AirtimeApiResponse.class);

            var airtimeApiResponse = responseEntity.getBody();

            if (Objects.nonNull(airtimeApiResponse) && "00".equals(airtimeApiResponse.getResponseCode())) {
                return AirtimeResponse.builder()
                        .responseCode(airtimeApiResponse.getResponseCode())
                        .responseMessage(airtimeApiResponse.getResponseMessage())
                        .airtimeApiResponse(AirtimeApiResponse.builder()
                                .requestId(airtimeApiResponse.getRequestId())
                                .referenceId(airtimeApiResponse.getReferenceId())
                                .data(airtimeApiResponse.getData())
                                .build())
                        .build();
            } else {
                return AirtimeResponse.builder()
                        .responseCode(WalletUtils.FAILED_TRANSACTION_CODE)
                        .responseMessage(WalletUtils.FAILED_TRANSACTION_MESSAGE)
                        .airtimeApiResponse(null)
                        .build();
            }

        } catch (RestClientException e) {

            return new AirtimeResponse("ERROR", e.getMessage(), null,null);
        }
    }

    public static String calculateHMAC512(String data, String key) {

        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);

        Mac mac = null;

        try {

            mac = Mac.getInstance(HMAC_SHA512);

            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {

            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

        }

    }
}

