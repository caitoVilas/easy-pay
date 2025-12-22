package com.caito.waletservice.utils.mappers;

import com.caito.waletservice.api.models.responses.WalletResponse;
import com.caito.waletservice.persistence.entities.Wallet;

/**
 * Mapper class for converting Wallet entities to WalletResponse DTOs.
 *
 * @author caito
 *
 */
public class WalletMapper {

    /**
     * Maps a Wallet entity to a WalletResponse DTO.
     *
     * @param wallet the Wallet entity to map
     * @return the corresponding WalletResponse DTO
     */
    public static WalletResponse mapToDto(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .userType(wallet.getUserType())
                .currency(wallet.getCurrency())
                .walletStatus(wallet.getWalletStatus())
                .balance(wallet.getBalance())
                .availableBalance(wallet.getAvailableBalance())
                .blockedBalance(wallet.getBlockedBalance())
                .build();
    }

    /**
     * Maps a WalletResponse DTO to a Wallet entity.
     *
     * @param walletResponse the WalletResponse DTO to map
     * @return the corresponding Wallet entity
     */
    public static Wallet mapToEntity(WalletResponse walletResponse) {
        return Wallet.builder()
                .id(walletResponse.getId())
                .userId(walletResponse.getUserId())
                .userType(walletResponse.getUserType())
                .currency(walletResponse.getCurrency())
                .walletStatus(walletResponse.getWalletStatus())
                .balance(walletResponse.getBalance())
                .availableBalance(walletResponse.getAvailableBalance())
                .blockedBalance(walletResponse.getBlockedBalance())
                .build();
    }
}
