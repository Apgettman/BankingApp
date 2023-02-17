package com.skypro.bankingApp;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    void simpleUUIDTest(){
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid);
    }
}
