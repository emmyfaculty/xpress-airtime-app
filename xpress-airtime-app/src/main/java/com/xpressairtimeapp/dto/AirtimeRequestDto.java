package com.xpressairtimeapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeRequestDto {

	private String requestId;
	private String uniqueCode;
	private DetailsRequestDto details;

}
