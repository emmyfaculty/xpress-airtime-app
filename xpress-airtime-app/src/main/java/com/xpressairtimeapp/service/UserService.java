package com.xpressairtimeapp.service;

import com.xpressairtimeapp.dto.AirtimeResponse;
import com.xpressairtimeapp.dto.FundWalletRequest;
import com.xpressairtimeapp.dto.LoginDto;
import com.xpressairtimeapp.dto.UserRequest;

public interface UserService {
	AirtimeResponse createAccount(UserRequest userRequest);
	AirtimeResponse fundWallet(FundWalletRequest fundWalletRequest);
	AirtimeResponse login(LoginDto loginDto);

}
