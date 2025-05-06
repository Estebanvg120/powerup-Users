package com.pragma.powerup.domain.spi;

public interface ISecurityContextPort {
    String encryptedPassword(String password);
    String getAuthenticatedRole();
}
