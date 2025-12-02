package com.caito.merchantservice.services.impl;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.persistence.entities.MerchantUser;
import com.caito.merchantservice.persistence.entities.Role;
import com.caito.merchantservice.persistence.repositories.MerchantRepository;
import com.caito.merchantservice.persistence.repositories.MerchantUserRepository;
import com.caito.merchantservice.persistence.repositories.RoleRepository;
import com.caito.merchantservice.services.contracts.MerchantService;
import com.caito.merchantservice.utils.enums.MerchantStatus;
import com.caito.merchantservice.utils.mappers.MerchantMapper;
import com.caito.merchantservice.utils.mappers.UserMapper;
import com.pp.commonsservice.enums.RoleName;
import com.pp.commonsservice.exceptions.BadRequestException;
import com.pp.commonsservice.exceptions.NotFoundException;
import com.pp.commonsservice.helpers.ValidationHelper;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantUserRepository merchantUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MerchantResponse createMerchant(MerchantRequest request) {
        log.info(WriteLog.logInfo("--> Create Merchant Service"));
        this.validateMerchantRequest(request);
        var merchant = MerchantMapper.mapTOEntity(request);
        merchant.setStatus(MerchantStatus.PENDING_VERIFICATION);
        merchant.setApiKey(UUID.randomUUID().toString());
        List<MerchantUser> users = new ArrayList<>();
        var user = UserMapper.mapToEntity(request.getUser());
        List<Role> roles = new ArrayList<>();
        user.setPassword(passwordEncoder.encode(request.getUser().getPassword()));
        var rol = roleRepository.findByRole(RoleName.ROLE_ADMIN).orElseThrow(
                () -> new NotFoundException("Role ADMIN not found")
        );
        roles.add(rol);
        user.setRoles(roles);
        users.add(user);
        merchant.setUsers(users);

        return MerchantMapper.mapToDto(merchantRepository.save(merchant));
    }

    /*     * Validate Merchant Request
     *
     * @param request MerchantRequest
     * @throws BadRequestException if validation fails
     */
    private void validateMerchantRequest(MerchantRequest request) {
        log.info(WriteLog.logInfo("--> Validating Merchant..."));
        List<String> errors = new ArrayList<>();
        //validate admin user
        if (request.getUser() == null){
            errors.add("Merchant admin data is required.");
        }else {
            this.validateAdmin(request.getUser(), errors);
        }
        if (!errors.isEmpty()){
            log.error(WriteLog.logError("Validation errors: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }
        //validate merchant
        if (request.getBusinessName() == null || request.getBusinessName().isEmpty()){
            errors.add("Merchant business name is required.");
        }
        if (request.getTaxId() == null || request.getTaxId().isEmpty()) {
            errors.add("Merchant Tax ID is required.");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()){
            errors.add("Merchant email is required.");
        } else if (merchantRepository.existsByEmail(request.getEmail())) {
            errors.add("Merchant email already exists.");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Merchant email is invalid.");
        }
        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            errors.add("Merchant phone is required.");
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            errors.add("Merchant address is required.");
        }
        if (request.getCity() == null || request.getCity().isEmpty()) {
            errors.add("Merchant city is required.");
        }
        if (request.getState() == null || request.getState().isEmpty()) {
            errors.add("Merchant state is required.");
        }
        if (request.getZipCode() == null || request.getZipCode().isEmpty()) {
            errors.add("Merchant zip code is required.");
        }
        if (request.getCountry() == null || request.getCountry().isEmpty()) {
            errors.add("Merchant country is required.");
        }
        if (!errors.isEmpty()){
            log.error(WriteLog.logError("Validation errors: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }

    }

    /*     * Validate Merchant Admin User
     *
     * @param user   MerchantUserRequest
     * @param errors List<String>
     */
    private void validateAdmin(MerchantUserRequest user, List<String> errors) {
        log.info(WriteLog.logInfo("--> Validating Merchant Admin..."));
        if (user.getFullName() == null || user.getFullName().isEmpty()){
            errors.add("Merchant admin first name is required.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()){
            errors.add("Merchant admin email is required.");
        } else if (merchantUserRepository.existsByEmail(user.getEmail())) {
            errors.add("Merchant admin email already exists.");
        } else if (!ValidationHelper.validateEmail(user.getEmail())) {
            errors.add("Merchant admin email is invalid.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()){
            errors.add("Merchant admin password is required.");
        }else if (!ValidationHelper.validatePassword(user.getPassword())){
            errors.add("Invalid format Merchant admin password");
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()){
            errors.add("Merchant admin phone is required.");
        }
    }
}
