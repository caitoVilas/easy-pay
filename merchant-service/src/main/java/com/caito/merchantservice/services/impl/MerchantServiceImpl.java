package com.caito.merchantservice.services.impl;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.requests.MerchantUpdateRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Merchant Service Implementation
 *
 * @author Caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantUserRepository merchantUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /*     * Create Merchant
     *
     * @param request MerchantRequest
     * @return MerchantResponse
     * @throws BadRequestException if validation fails
     */
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
        var newMwechant = merchantRepository.save(merchant);
        user.setMerchant(newMwechant);
        merchantUserRepository.save(user);

        return MerchantMapper.mapToDto(merchantRepository.save(merchant));
    }

    /*     * Get All Merchants
     *
     * @return List<MerchantResponse>
     */
    @Override
    @Transactional(readOnly = true)
    public List<MerchantResponse> getAllMerchants() {
        log.info(WriteLog.logInfo("--> Get All Merchants Service"));
        return merchantRepository.findAll().stream().map(MerchantMapper::mapToDto).toList();
    }

    /*     * Get All Users by Merchant Id
     *
     * @param merchantId Long
     * @return List<MerchantUserResponse>
     * @throws NotFoundException if merchant not found
     */
    @Override
    @Transactional(readOnly = true)
    public List<MerchantUserResponse> getAllUsersbyMerchantId(Long merchantId) {
        log.info(WriteLog.logInfo("--> Get All Users for Merchants Service"));
        var merchant = merchantRepository.findById(merchantId).orElseThrow(
                () -> new NotFoundException("Merchant with id " + merchantId + " not found")
        );
        return merchant.getUsers().stream().map(UserMapper::mapToDto).toList();
    }

    /*     * Get Merchant by Id
     *
     * @param merchantId Long
     * @return MerchantResponse
     * @throws NotFoundException if merchant not found
     */
    @Override
    @Transactional(readOnly = true)
    public MerchantResponse getMerchantById(Long merchantId) {
        log.info(WriteLog.logInfo("--> Get Merchant Service"));
        return MerchantMapper.mapToDto(merchantRepository.findById(merchantId).orElseThrow(
                () -> {
                    log.warn(WriteLog.logWarning("Merchant with id " + merchantId + " not found"));
                    return new NotFoundException("Merchant with id " + merchantId + " not found");}
        ));
    }

    /*      Update Merchant
     *
     * @param merchantId Long
     * @param request    MerchantUpdateRequest
     * @return MerchantResponse
     * @throws NotFoundException  if merchant not found
     * @throws BadRequestException if validation fails
     */
    @Override
    @Transactional
    public MerchantResponse updateMerchant(Long merchantId, MerchantUpdateRequest request) {
        log.info(WriteLog.logInfo("--> Update Merchant Service"));
        var oldMerchant = merchantRepository.findById(merchantId).orElseThrow(
                () -> {
                    log.warn(WriteLog.logWarning("--> Merchant not found"));
                    return new NotFoundException("Merchant with id " + merchantId + " not found");
                }
        );
        if (request.getBusinessName() != null && !request.getBusinessName().isEmpty()){
            oldMerchant.setBusinessName(request.getBusinessName());
        }
        if (request.getTaxId() != null && !request.getTaxId().isEmpty()){
            if (merchantRepository.findByTaxIdAndNotId(merchantId, request.getTaxId()) != null){
                log.warn(WriteLog.logWarning("--> Tax ID already exists"));
                throw new BadRequestException(List.of("Tax ID already exists"));
            }
            oldMerchant.setTaxId(request.getTaxId());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()){
            if (merchantRepository.findByEmailAndNotId(merchantId, request.getEmail()) != null){
                log.warn(WriteLog.logWarning("--> Email already exists"));
                throw new BadRequestException(List.of("Email already exists"));
            } else if (!ValidationHelper.validateEmail(request.getEmail())) {
                log.warn(WriteLog.logWarning("--> Invalid email"));
                throw new BadRequestException(List.of("Invalid email"));
            }
            oldMerchant.setEmail(request.getEmail());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()){
            oldMerchant.setPhone(request.getPhone());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()){
            oldMerchant.setAddress(request.getAddress());
        }
        if (request.getCity() != null && !request.getCity().isEmpty()){
            oldMerchant.setCity(request.getCity());
        }
        if (request.getState() != null && !request.getState().isEmpty()){
            oldMerchant.setState(request.getState());
        }
        if (request.getZipCode() != null && !request.getZipCode().isEmpty()) {
            oldMerchant.setZipCode(request.getZipCode());
        }
        if (request.getCountry() != null && !request.getCountry().isEmpty()){
            oldMerchant.setCountry(request.getCountry());
        }
        if (request.getBusinessType() != null){
            oldMerchant.setBusinessType(request.getBusinessType());
        }
        if (request.getWebhookUrl() != null && !request.getWebhookUrl().isEmpty()){
            oldMerchant.setWebhookUrl(request.getWebhookUrl());
        }
        return MerchantMapper.mapToDto(merchantRepository.save(oldMerchant));
    }

    /*     * Delete Merchant
     *
     * @param merchantId Long
     * @throws NotFoundException if merchant not found
     */
    @Override
    @Transactional
    public void deleteMerchant(Long merchantId) {
        log.info(WriteLog.logInfo("--> Delete Merchant Service"));
        var merchant = merchantRepository.findById(merchantId).orElseThrow(
                () -> {
                    log.warn(WriteLog.logWarning("--> Merchant not found"));
                    return new NotFoundException("Merchant with id " + merchantId + " not found");
                }
        );

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
        } else if (merchantRepository.existsByTaxId(request.getTaxId())) {
            errors.add("Merchant Tax ID already exists.");
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
