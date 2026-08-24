package za.ac.cput.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
AdminControllerTest.java
AdminControllerTest
Author: Thimna Barbara Booi - 230232108
Date: 28/06/2026
 */


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

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
    void a_registerAdmin() {

        ResponseEntity<Admin> response =
                adminController.registerAdmin(admin);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(adminId, response.getBody().getAdminId());
        assertEquals(email, response.getBody().getEmail());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("ADMIN", response.getBody().getRole());
        assertNotNull(response.getBody().getCreatedAt());
    }

    @Test
    void b_loginAdmin() {

        adminService.create(admin);

        ResponseEntity<Admin> response =
                adminController.loginAdmin(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(adminId, response.getBody().getAdminId());
        assertEquals(email, response.getBody().getEmail());
        assertNotNull(response.getBody().getLastLogin());
    }


    @Test
    void d_getAdminById() {

        adminService.create(admin);

        ResponseEntity<Admin> response =
                adminController.getAdminById(adminId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(adminId, response.getBody().getAdminId());
        assertEquals(email, response.getBody().getEmail());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void e_getAdminByIdNotFound() {

        ResponseEntity<Admin> response =
                adminController.getAdminById("NONEXISTENT");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void f_updateAdmin() {

        adminService.create(admin);

        Admin updatedAdmin = AdminFactory.createAdmin(
                adminId,
                "Jonathan",
                "Smith",
                "jonathan.smith@example.com",
                "NewPassword123",
                "SUPER_ADMIN"
        );

        ResponseEntity<Admin> response =
                adminController.updateAdmin(updatedAdmin);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(adminId, response.getBody().getAdminId());
        assertEquals("Jonathan", response.getBody().getFirstName());
        assertEquals("Smith", response.getBody().getLastName());
        assertEquals(
                "jonathan.smith@example.com",
                response.getBody().getEmail()
        );
        assertEquals(
                "SUPER_ADMIN",
                response.getBody().getRole()
        );
    }

    @Test
    void g_deleteAdmin() {

        adminService.create(admin);

        ResponseEntity<Void> response =
                adminController.deleteAdmin(adminId);

        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        assertNull(response.getBody());

        Admin deletedAdmin =
                adminRepository.findById(adminId).orElse(null);

        assertNull(deletedAdmin);
    }

    @Test
    void h_deleteAdminNotFound() {

        ResponseEntity<Void> response =
                adminController.deleteAdmin("NONEXISTENT");

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertNull(response.getBody());
    }
}