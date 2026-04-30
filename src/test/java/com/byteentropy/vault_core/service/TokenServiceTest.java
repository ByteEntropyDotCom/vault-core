package com.byteentropy.vault_core.service;

import com.byteentropy.vault_core.crypto.EncryptionService;
import com.byteentropy.vault_core.crypto.model.VaultRequest;
import com.byteentropy.vault_core.crypto.model.VaultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    private EncryptionService encryptionService;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        // Manually create the service with a valid key and spy on it
        EncryptionService realService = new EncryptionService("12345678901234567890123456789012");
        encryptionService = Mockito.spy(realService);
        tokenService = new TokenService(encryptionService);
    }

    @Test
    void testTokenizationAndMasking() throws Exception {
        VaultRequest request = new VaultRequest("4111222233334444", "card");
        VaultResponse response = tokenService.tokenize(request);

        assertTrue(response.token().startsWith("tkn-"));
        assertEquals("****4444", response.maskedData());
    }

    @Test
    void testShortDataMasking() throws Exception {
        VaultRequest request = new VaultRequest("123", "pin");
        VaultResponse response = tokenService.tokenize(request);

        assertEquals("****", response.maskedData());
    }
}