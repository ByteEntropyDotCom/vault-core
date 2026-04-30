package com.byteentropy.vault_core.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    private EncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        // Pass a valid 32-character dummy key for testing
        String testKey = "12345678901234567890123456789012"; 
        encryptionService = new EncryptionService(testKey);
    }

    @Test
    void testEncryptionIsConsistentAndUnique() throws Exception {
        String secret = "1234-5678-9012-3456";
        
        String cipher1 = encryptionService.encrypt(secret);
        String cipher2 = encryptionService.encrypt(secret);

        assertNotNull(cipher1);
        assertNotNull(cipher2);
        
        // Verifies GCM uniqueness (Random IV working)
        assertNotEquals(cipher1, cipher2, "Ciphertexts should be unique for each call");
    }

    @Test
    void testEncryptionOfEmptyString() throws Exception {
        String cipher = encryptionService.encrypt("");
        assertNotNull(cipher);
    }

    @Test
    void testInvalidKeyLength() {
        // Test that our security guardrail actually works
        assertThrows(IllegalArgumentException.class, () -> {
            new EncryptionService("short-key");
        });
    }
}