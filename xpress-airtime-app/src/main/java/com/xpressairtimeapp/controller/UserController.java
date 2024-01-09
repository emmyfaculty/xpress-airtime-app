package com.xpressairtimeapp.controller;

import com.xpressairtimeapp.dto.AirtimeResponse;
import com.xpressairtimeapp.dto.FundWalletRequest;
import com.xpressairtimeapp.dto.LoginDto;
import com.xpressairtimeapp.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xpressairtimeapp.service.UserService;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs", description = "The user API")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	@Operation(summary = "Create a new user account", description = "This API creates a new user account and assigned wallet number to the user"
			+ "The wallet number is a 10 digit number")
	@ApiResponse(responseCode = "201",
			description = "Http status code 201 is returned when a new user account is created successfully"
			+ "The response body contains the wallet number of the user created")
	public AirtimeResponse createAccount(@RequestBody UserRequest userRequest) {
		return userService.createAccount(userRequest);
	}

	@PostMapping("/login")
	public  AirtimeResponse login(@RequestBody LoginDto loginDto) {
		return userService.login(loginDto);
	}
	@PostMapping("/fundWallet")
	@Operation(summary = "Fund Wallet",
			description = "Given a wallet number and amount, this API credits the user wallet with the amount")
	@ApiResponse(responseCode = "201",  description = "Http status code 201 is returned and wallet credited if the wallet exist successfully"
			+ "The response body contains the wallet balance of the user")
	public AirtimeResponse creditAccount(@RequestBody FundWalletRequest request) {
		return userService.fundWallet(request);
	}

//	@PostMapping("/debitAccount")
//	@Operation(summary = "Debit Account",
//			description = "Given an account number and amount, this API debits the user account with the amount")
//	@ApiResponse(responseCode = "201",  description = "Http status code 201 is returned and account debited if the account exist successfully"
//			+ "The response body contains the account balance of the user")
//	public BankResponse debitAccount(@RequestBody CreditDebitRequest debitRequest) {
//		return userService.debitAccount(debitRequest);
//	}

//	@PostMapping("/transfer")
//	@Operation(summary = "Transfer",
//			description = "Given an account number and amount, this API transfers the amount from the user account to the beneficiary account")
//	@ApiResponse(responseCode = "201",  description = "Http status code 201 is returned and account debited if the account exist successfully"
//			+ "The response body contains the account balance of the user")
//	public BankResponse transfer(@RequestBody TransferRequest transferRequest) {
//		return userService.transfer(transferRequest);
//	}

}
