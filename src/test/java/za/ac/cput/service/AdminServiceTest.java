package za.ac.cput.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/*
AdminServiceTest.java
AdminServiceTest
Author: Thimna Booi - 230232108
Date: 25/06/2026
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    private Admin admin = AdminFactory.createAdminFull(
            "ADM002026",
            "Lebohang",
            "Booi",
            "adminLB@cput.ac.za",
            "SecurePass123",
            LocalDateTime.now(),
            null
    );

    @Test
    void a_create() {
        Admin created = adminService.create(admin);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Admin read = adminService.read(admin.getAdminId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Admin newAdmin = new Admin.Builder().copy(admin)
                .setFirstName("Lerato")
                .setPassword("NewPassword123")
                .build();
        Admin updated = adminService.update(newAdmin);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Disabled
    void e_delete() {
        adminService.delete(admin.getAdminId());
        Admin deleted = adminService.read(admin.getAdminId());
        assertNotNull(deleted);
        System.out.println("Admin Deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(adminService.getAll());
    }

    @Test
    void f_findByEmail() {
        Admin found = adminService.findByEmail(admin.getEmail());
        assertNotNull(found);
        System.out.println(found);
    }
}