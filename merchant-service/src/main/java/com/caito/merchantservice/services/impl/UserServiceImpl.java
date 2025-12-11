package com.caito.merchantservice.services.impl;

import com.caito.merchantservice.api.models.requests.CreatePasswordRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserUpdateRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
import com.caito.merchantservice.persistence.entities.Merchant;
import com.caito.merchantservice.persistence.entities.MerchantUser;
import com.caito.merchantservice.persistence.entities.Role;
import com.caito.merchantservice.persistence.repositories.MerchantRepository;
import com.caito.merchantservice.persistence.repositories.MerchantUserRepository;
import com.caito.merchantservice.persistence.repositories.RoleRepository;
import com.caito.merchantservice.services.contracts.UserService;
import com.caito.merchantservice.utils.mappers.UserMapper;
import com.pp.commonsservice.enums.RoleName;
import com.pp.commonsservice.exceptions.BadRequestException;
import com.pp.commonsservice.exceptions.NotFoundException;
import com.pp.commonsservice.exceptions.UnauthorizedException;
import com.pp.commonsservice.helpers.ValidationHelper;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*
 * UserServiceImpl provides the implementation for user-related operations.
 *
 * @author Caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final MerchantUserRepository merchantUserRepository;
    private final RoleRepository roleRepository;
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * Creates a new user associated with a merchant.
     *
     * @param merchantId The ID of the merchant.
     * @param request    The user creation request data.
     * @return The created user's response data.
     */
    @Override
    @Transactional
    public MerchantUserResponse createUser(Long merchantId, MerchantUserRequest request) {
        log.info(WriteLog.logInfo("--> Creating user"));
        var merchant = this.getMerchant(merchantId);
        if (!this.permission(merchant)){;
            log.error(WriteLog.logError("Permission denied to create user for merchant ID: " + merchantId));
            throw new UnauthorizedException("Unauthorized to create user for this merchant");
        }
        this.validateUser(request);
        var user = UserMapper.mapToEntity(request);
        var rol = roleRepository.findByRole(RoleName.ROLE_USER).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("Role ROLE_USER not found"));
                    return new NotFoundException("Role ROLE_USER not found");
                }
        );
        List<Role> roles = new ArrayList<>();
        roles.add(rol);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMerchant(merchant);
        return UserMapper.mapToDto(merchantUserRepository.save(user));
    }

    /*
     * Retrieves a user by ID associated with a merchant.
     *
     * @param userId     The ID of the user.
     * @param merchantId The ID of the merchant.
     * @return The user's response data.
     */
    @Override
    @Transactional(readOnly = true)
    public MerchantUserResponse getUserById(Long userId, Long merchantId) {
        log.info(WriteLog.logInfo("--> Get user by id"));
        var merchant = this.getMerchant(merchantId);
        if (!this.permission(merchant)){;
            log.error(WriteLog.logError("Permission denied to create user for merchant ID: " + merchantId));
            throw new UnauthorizedException("Unauthorized to create user for this merchant");
        }
        return UserMapper.mapToDto(merchantUserRepository.findById(userId).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User with ID " + userId + " not found"));
                    return new NotFoundException("User with ID " + userId + " not found");
                }
        ));
    }

    /*
     * Updates an existing user associated with a merchant.
     *
     * @param userId  The ID of the user.
     * @param request The user update request data.
     * @return The updated user's response data.
     */
    @Override
    public MerchantUserResponse updateUser(Long userId, MerchantUserUpdateRequest request) {
        log.info(WriteLog.logInfo("--> Update user"));
        var merchant = this.getMerchant(request.getMerchantId());
        if (!this.permission(merchant)){;
            log.error(WriteLog.logError("Permission denied to create user for merchant ID: " + request.getMerchantId()));
            throw new UnauthorizedException("Unauthorized to create user for this merchant");
        }
        var user = merchantUserRepository.findById(userId).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User with ID " + userId + " not found"));
                    return new NotFoundException("User with ID " + userId + " not found");
                }
        );
        if (request.getFullName() != null && !request.getFullName().isEmpty()) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (merchantUserRepository.findByEmailAndNotId(userId, request.getEmail()) != null) {
                log.error(WriteLog.logError("Email is already in use."));
                throw new BadRequestException(List.of("Email is already in use."));
            } else if (!ValidationHelper.validateEmail(request.getEmail())) {
                log.error(WriteLog.logError("Email is not valid."));
                throw new BadRequestException(List.of("Email is not valid."));
            }
            user.setEmail(request.getEmail());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            user.setAddress(request.getAddress());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        return UserMapper.mapToDto(merchantUserRepository.save(user));
    }

    /*
     * Deletes a user by ID associated with a merchant.
     *
     * @param userId     The ID of the user.
     * @param merchantId The ID of the merchant.
     */
    @Override
    @Transactional
    public void deleteUser(Long userId, Long merchantId) {
        log.info(WriteLog.logInfo("--> Delete user by id"));
        var merchant = this.getMerchant(merchantId);
        if (!this.permission(merchant)){;
            log.error(WriteLog.logError("Permission denied to create user for merchant ID: " + merchantId));
            throw new UnauthorizedException("Unauthorized to create user for this merchant");
        }
        var user = merchantUserRepository.findById(userId).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User with ID " + userId + " not found"));
                    return new NotFoundException("User with ID " + userId + " not found");
                }
        );
        merchantUserRepository.delete(user);
    }

    /*
     * Creates or updates a password for a user.
     *
     * @param request The password creation request data.
     */
    @Override
    @Transactional
    public void createPassword(CreatePasswordRequest request) {
        log.info(WriteLog.logInfo("--> Creating password"));
        var merchant = this.getMerchant(request.getMerchantId());
        if (!this.permission(merchant)){;
            log.error(WriteLog.logError("Permission denied to create user for merchant ID: " + request.getMerchantId()));
            throw new UnauthorizedException("Unauthorized to create user for this merchant");
        }
        var user = merchantUserRepository.findById(request.getUserId()).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User with ID " + request.getUserId() + " not found"));
                    return new NotFoundException("User with ID " + request.getUserId() + " not found");
                }
        );
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            log.error(WriteLog.logError("Password is required."));
            throw new BadRequestException(List.of("Password is required."));
        }
        if (request.getConfirmPassword() == null || request.getConfirmPassword().isEmpty()) {
            log.error(WriteLog.logError("Confirm password is required."));
            throw new BadRequestException(List.of("Confirm password is required."));
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.error(WriteLog.logError("Passwords do not match."));
            throw new BadRequestException(List.of("Passwords do not match."));
        } else if (!ValidationHelper.validatePassword(request.getPassword())) {
            log.error(WriteLog.logError("Invalid password format."));
            throw new BadRequestException(List.of("Invalid password format."));
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        merchantUserRepository.save(user);
    }


    /*
     * Fetches a merchant by ID.
     *
     * @param merchantId The ID of the merchant.
     * @return The merchant entity.
     */
    private Merchant getMerchant(Long merchantId) {
        log.info(WriteLog.logInfo("--> Fetching merchant with ID: " + merchantId));
        return merchantRepository.findById(merchantId).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("Merchant with ID " + merchantId + " not found"));
                    return new RuntimeException("Merchant with ID " + merchantId + " not found");
                }
        );
    }

    /*
     * Validates the user creation request data.
     *
     * @param request The user creation request data.
     */
    private void validateUser(MerchantUserRequest request) {
        log.info(WriteLog.logInfo("--> Validating user data"));
        List<String> errors = new ArrayList<>();
        if (request.getFullName() == null || request.getFullName().isEmpty()) {
            errors.add("Full name is required.");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required.");
        } else if (merchantUserRepository.existsByEmail(request.getEmail())) {
            errors.add("Email is already in use.");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Email is not valid.");
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            errors.add("Address is required.");
        }
        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            errors.add("Phone number is required.");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required.");
        } else if (!ValidationHelper.validatePassword(request.getPassword())) {
            errors.add("Password is not valid.");
        }
        if (!errors.isEmpty()){
            log.error(WriteLog.logError("User validation failed: " + String.join(", ", errors)));
            throw new BadRequestException( errors);
        }
    }

    /*     * Check Permission
     *
     * @param merchant Merchant
     * @return boolean
     */
    private boolean permission(Merchant merchant){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        MerchantUser principal = (MerchantUser) authentication.getPrincipal();
        MerchantUser user = merchantUserRepository.findById(principal.getId()).orElseThrow(
                () -> new NotFoundException("--> not fund")
        );
        boolean isSupervisor = principal.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(RoleName.ROLE_SUPERVISOR));
        String apiKeyPricipal = user.getMerchant().getApiKey();
        if (isSupervisor){
            return true;
        }else return apiKeyPricipal.equals(merchant.getApiKey());
    }
}
