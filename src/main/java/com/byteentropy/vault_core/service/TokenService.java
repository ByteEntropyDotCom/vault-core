package com.byteentropy.vault_core.service;

import com.byteentropy.vault_core.crypto.model.VaultRequest;
import com.byteentropy.vault_core.crypto.model.VaultResponse;
import com.byteentropy.vault_core.crypto.EncryptionService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final EncryptionService encryptionService;
    // Mock database: In production, use Redis or Postgres
    private final Map<String, String> vaultStorage = new ConcurrentHashMap<>();

    public TokenService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public VaultResponse tokenize(VaultRequest request) throws Exception {
        String token = "tkn-" + UUID.randomUUID();
        String encryptedData = encryptionService.encrypt(request.data());
        
        vaultStorage.put(token, encryptedData);
        
        String masked = request.data().length() > 4 
            ? "****" + request.data().substring(request.data().length() - 4)
            : "****";
            
        return new VaultResponse(token, masked);
    }
}
