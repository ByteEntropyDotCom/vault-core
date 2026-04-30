package com.byteentropy.vault_core.api;

import com.byteentropy.vault_core.crypto.model.VaultRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

// Add the property here so the WebContext can start
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "VAULT_MASTER_KEY=12345678901234567890123456789012"
)
class VaultControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTokenizeEndpointSuccess() {
        VaultRequest request = new VaultRequest("sensitive-pii-data", "pii");
        
        ResponseEntity<Object> response = restTemplate.postForEntity(
            "/api/v1/vault/tokenize", 
            request, 
            Object.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}