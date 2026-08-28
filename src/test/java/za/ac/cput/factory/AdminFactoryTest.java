package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Admin;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/*
AdminFactoryTest.java
Admin factory testing
Author: Thimna Booi - 230232108
Date: 25/03/2026
 */

class AdminFactoryTest {

    @Test
    void createAdmin() {
        Admin admin = AdminFactory.createAdminFull(
                "AD001",
                "Lebohang",
                "Booi",
                "adminLB@cput.ac.za",
                "SecurePass123",
                LocalDateTime.now(),
                null
        );
        assertNotNull(admin);
        System.out.println(admin);
    }

}