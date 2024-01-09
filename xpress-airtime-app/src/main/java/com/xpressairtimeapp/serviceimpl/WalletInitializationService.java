package com.xpressairtimeapp.serviceimpl;

import com.xpressairtimeapp.entity.CentralWallet;
import com.xpressairtimeapp.repository.CentralWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class WalletInitializationService {

    private CentralWalletRepository centralWalletRepository;

    public void initializeCentralWallet() {
        CentralWallet centralWallet = new CentralWallet();
        centralWallet.setBalance(BigDecimal.ZERO); // Set an initial balance
        centralWalletRepository.save(centralWallet);
    }
}
