package com.caito.merchantservice.configs.security.keys;

import org.springframework.stereotype.Component;

import java.security.*;

/**
 * KeyUtils is a utility class for generating RSA key pairs.
 *
 * @author caito
 *
 */
@Component
public class KeyUtils {

   /**
    * Generates an RSA key pair with a key size of 4096 bits.
    *
    * @return a KeyPair containing the generated public and private keys
    */
   public static KeyPair generateKeyPair() {
       KeyPair keyPair = null;
       try {
          KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            keyPair = keyPairGenerator.generateKeyPair();
       } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
       }
         return keyPair;
   }


}
