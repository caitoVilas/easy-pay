package com.caito.waletservice.api.exceptios.customs;

/**
 * Custom exception class for Wallet-related errors.
 *
 * @author caito
 *
 */
public class WalletException extends RuntimeException {
    public WalletException(String message) {
        super(message);
    }
}
