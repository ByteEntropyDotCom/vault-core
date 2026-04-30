package com.byteentropy.vault_core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = "VAULT_MASTER_KEY=12345678901234567890123456789012")
class VaultCoreApplicationTests {
    @Test
    void contextLoads() {
        // Verifies the application context starts without errors
    }
}