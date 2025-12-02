package com.caito.merchantservice.persistence.entities;

import com.caito.merchantservice.utils.enums.BusinessYtpe;
import com.caito.merchantservice.utils.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/*
* Merchant Entity representing a merchant in the system.
* Fields include business details, contact information, status, and timestamps.
* Uses Lombok for boilerplate code reduction and Hibernate annotations for automatic timestamping.
* Mapped to the "merchants" table in the database.
+
* @author Caito
*
*/
@Entity
@Table(name = "merchants")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String TaxId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    @Enumerated(EnumType.STRING)
    private MerchantStatus status;
    @Enumerated(EnumType.STRING)
    private BusinessYtpe businessType;
    private String apiKey;
    private String webhookUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MerchantUser> users;
}
