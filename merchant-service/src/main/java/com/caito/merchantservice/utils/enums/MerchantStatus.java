package com.caito.merchantservice.utils.enums;

/*
* Enum representing the various statuses a merchant can have in the system.
* PENDING_VERIFICATION: The merchant has registered but is awaiting verification.
* ACTIVE: The merchant is verified and active in the system.
* SUSPENDED: The merchant's account is temporarily suspended.
* BLOCKED: The merchant's account is blocked due to violations or issues.
* CLOSED: The merchant's account is permanently closed.
*
* @author caito
*
*/
public enum MerchantStatus {
    PENDING_VERIFICATION,
    ACTIVE,
    SUSPENDED,
    BLOCKED,
    CLOSED
}
