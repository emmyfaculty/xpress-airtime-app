package com.xpressairtimeapp.serviceimpl;

import com.xpressairtimeapp.securityconfig.JwtTokenProvider;
import com.xpressairtimeapp.dto.*;
import com.xpressairtimeapp.entity.Role;
import com.xpressairtimeapp.entity.User;
import com.xpressairtimeapp.repository.UserRepository;
import com.xpressairtimeapp.service.UserService;
import com.xpressairtimeapp.utils.WalletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public AirtimeResponse createAccount(UserRequest userRequest) {
		/**
		 * Creating wallet - saving a new user into the db
		 * check if user already has wallet
		 */

		if (userRepository.existsByEmail(userRequest.getEmail())) {
			return AirtimeResponse.builder()
					.responseCode(WalletUtils.WALLET_EXISTS_CODE)
					.responseMessage(WalletUtils.WALLET_EXISTS_MESSAGE)
					.walletInfo(null)
					.build();
		}
		User newUser = User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.walletNumber(WalletUtils.generateWalletNumber())
				.walletBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.password(passwordEncoder.encode(userRequest.getPassword()))
				.role(Role.ROLE_USER)
				.phoneNumber(userRequest.getPhoneNumber())
				.build();

		User savedUser = userRepository.save(newUser);

		return AirtimeResponse.builder()
				.responseCode(WalletUtils.WALLET_CREATION_SUCCESS)
				.responseMessage(WalletUtils.WALLET_CREATION_MESSAGE)
				.walletInfo(WalletInfo.builder()
						.walletBalance(savedUser.getWalletBalance())
						.walletNumber(savedUser.getWalletNumber())
						.walletName(savedUser.getFirstName() + " " + savedUser.getOtherName() + " " + savedUser.getLastName())
						.phoneNumber(savedUser.getPhoneNumber())
						.build())
				.build();
	}

	public AirtimeResponse login(LoginDto loginDto) {
		Authentication authentication = null;
		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
		);

		return AirtimeResponse.builder()
				.responseCode("Login Success")
				.responseMessage(jwtTokenProvider.generateToken(authentication))
				.build();
	}

	@Override
	public AirtimeResponse fundWallet(FundWalletRequest fundWalletRequest) {
		//checking if Wallet exists
		boolean isWalletExist = userRepository.existsByWalletNumber(fundWalletRequest.getWalletNumber());
		if (!isWalletExist) {
			return AirtimeResponse.builder()
					.responseCode(WalletUtils.WALLET_NOT_EXIST_CODE)
					.responseMessage(WalletUtils.WALLET_NOT_EXIST_MESSAGE)
					.walletInfo(null)
					.build();
		}
		User userToFund = userRepository.findByWalletNumber(fundWalletRequest.getWalletNumber());
		userToFund.setWalletBalance(userToFund.getWalletBalance().add(fundWalletRequest.getAmount()));
		userRepository.save(userToFund);

		return AirtimeResponse.builder()
				.responseCode(WalletUtils.WALLET_CREDITED_SUCCESS)
				.responseMessage(WalletUtils.WALLET_CREDITED_SUCCESS_MESSAGE)
				.walletInfo(WalletInfo.builder()
						.walletBalance(userToFund.getWalletBalance())
						.walletNumber(fundWalletRequest.getWalletNumber())
						.walletName(userToFund.getFirstName() + " " + userToFund.getOtherName() + " " + userToFund.getLastName())
						.phoneNumber(userToFund.getPhoneNumber())
						.build())
				.build();
	}
}
