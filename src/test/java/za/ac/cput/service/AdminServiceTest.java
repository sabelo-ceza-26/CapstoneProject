package za.ac.cput.service;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;
import za.ac.cput.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
AdminServiceTest.java
AdminServiceTest
Author: Thimna Booi - 230232108
Date: 25/06/2026
 */

import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;
import za.ac.cput.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;
    private String adminId;
    private String email;
    private String password;

    @BeforeEach
    void setUp() {

        adminRepository.deleteAll();

        adminId = "ADM001";
        email = "john.doe@example.com";
        password = "SecurePass123";

        admin = AdminFactory.createAdmin(
                adminId,
                "John",
                "Doe",
                email,
                password,
                "ADMIN"
        );
    }

    @Test
    @DisplayName("create admin successfully")
    void a_create() {
        Admin createdAdmin = adminService.create(admin);

        assertNotNull(createdAdmin);
        assertEquals(adminId, createdAdmin.getAdminId());
        assertEquals(email, createdAdmin.getEmail());
        assertEquals("ADMIN", createdAdmin.getRole());
        assertNotNull(createdAdmin.getCreatedAt());

        Admin savedAdmin = adminRepository.findById(adminId).orElse(null);
        assertNotNull(savedAdmin);
        assertEquals(adminId, savedAdmin.getAdminId());
    }

    @Test
    @DisplayName("throw exception when creating admin with existing email")
    void b_createEmailExists() {
        adminService.create(admin);

        Admin duplicateAdmin = AdminFactory.createAdmin(
                "ADM002",
                "Jane",
                "Doe",
                email,
                "DifferentPass123",
                "SUPER_ADMIN"
        );

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.create(duplicateAdmin);
        });

        assertEquals(
                "Admin with email " + email + " already exists",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("read admin by id successfully")
    void c_read() {
        adminService.create(admin);

        Admin foundAdmin = adminService.read(adminId);

        assertNotNull(foundAdmin);
        assertEquals(adminId, foundAdmin.getAdminId());
        assertEquals(email, foundAdmin.getEmail());
    }

    @Test
    @DisplayName("return null when admin not found")
    void d_readNotFound() {
        String nonExistentId = "NONEXISTENT";

        Admin foundAdmin = adminService.read(nonExistentId);

        assertNull(foundAdmin);
    }

    @Test
    @DisplayName("update admin successfully")
    void e_update() {
        adminService.create(admin);

        Admin updatedAdmin = AdminFactory.createAdmin(
                adminId,
                "Jonathan",
                "Smith",
                "jonathan.smith@example.com",
                "NewPassword123",
                "SUPER_ADMIN"
        );

        Admin result = adminService.update(updatedAdmin);

        assertNotNull(result);
        assertEquals("Jonathan", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jonathan.smith@example.com", result.getEmail());
        assertEquals("SUPER_ADMIN", result.getRole());

        Admin savedAdmin = adminRepository.findById(adminId).orElse(null);

        assertNotNull(savedAdmin);
        assertEquals("Jonathan", savedAdmin.getFirstName());
        assertEquals("SUPER_ADMIN", savedAdmin.getRole());
    }

    @Test
    @DisplayName("throw exception when updating non-existent admin")
    void f_updateNotFound() {
        String nonExistentId = "NONEXISTENT";

        Admin nonExistentAdmin = AdminFactory.createAdmin(
                nonExistentId,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "Password123",
                "ADMIN"
        );

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.update(nonExistentAdmin);
        });

        assertEquals(
                "Admin not found with ID: " + nonExistentId,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("delete admin successfully")
    void g_delete() {
        adminService.create(admin);

        boolean deleted = adminService.delete(adminId);

        assertTrue(deleted);

        Admin deletedAdmin = adminRepository.findById(adminId).orElse(null);

        assertNull(deletedAdmin);
    }

    @Test
    @DisplayName("return false when deleting non-existent admin")
    void h_deleteNotFound() {
        String nonExistentId = "NONEXISTENT";

        boolean deleted = adminService.delete(nonExistentId);

        assertFalse(deleted);
    }


    @Test
    @DisplayName("get empty list when no admins exist")
    void j_getAllEmpty() {
        List<Admin> result = adminService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("login successfully with correct credentials")
    void k_login() {
        adminService.create(admin);

        Admin loggedInAdmin = adminService.login(email, password);

        assertNotNull(loggedInAdmin);
        assertEquals(adminId, loggedInAdmin.getAdminId());
        assertEquals(email, loggedInAdmin.getEmail());
        assertNotNull(loggedInAdmin.getLastLogin());

        Admin savedAdmin = adminRepository.findById(adminId).orElse(null);

        assertNotNull(savedAdmin);
        assertNotNull(savedAdmin.getLastLogin());
    }

    @Test
    @DisplayName("throw exception when login with incorrect password")
    void l_loginIncorrectPassword() {
        adminService.create(admin);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.login(email, "WrongPassword");
        });

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    @DisplayName("throw exception when login with non-existent email")
    void m_loginEmailNotFound() {
        String nonExistentEmail = "nonexistent@example.com";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.login(nonExistentEmail, password);
        });

        assertEquals(
                "Admin not found with email: " + nonExistentEmail,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("find admin by email successfully")
    void n_findByEmail() {
        adminService.create(admin);

        Admin foundAdmin = adminService.findByEmail(email);

        assertNotNull(foundAdmin);
        assertEquals(adminId, foundAdmin.getAdminId());
        assertEquals(email, foundAdmin.getEmail());
    }

    @Test
    @DisplayName("return null when finding admin by non-existent email")
    void o_findByEmailNotFound() {
        String nonExistentEmail = "nonexistent@example.com";

        Admin foundAdmin = adminService.findByEmail(nonExistentEmail);

        assertNull(foundAdmin);
    }

    @Test
    @DisplayName("check if email exists")
    void p_existsByEmail() {
        adminService.create(admin);

        boolean exists = adminService.existsByEmail(email);

        assertTrue(exists);
    }

    @Test
    @DisplayName("return false when checking non-existent email")
    void q_existsByEmailNotFound() {
        String nonExistentEmail = "nonexistent@example.com";

        boolean exists = adminService.existsByEmail(nonExistentEmail);

        assertFalse(exists);
    }

    @Test
    @DisplayName("get admins by role successfully")
    void r_getByRole() {
        adminService.create(admin);

        Admin admin2 = AdminFactory.createAdmin(
                "ADM002",
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "Password456",
                "SUPER_ADMIN"
        );

        adminService.create(admin2);

        List<Admin> result = adminService.getByRole("ADMIN");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getRole());
    }

    @Test
    @DisplayName("return empty list when no admins with role exist")
    void s_getByRoleNoAdmins() {
        adminService.create(admin);

        List<Admin> result = adminService.getByRole("SUPER_ADMIN");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}