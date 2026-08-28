package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*
AdminControllerTest.java
AdminControllerTest
Author: Thimna Barbara Booi - 230232108
Date: 28/06/2026
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminControllerTest {

    private static Admin admin;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/admin";

    @BeforeAll
    public static void setUp() {
        admin = AdminFactory.createAdminFull(
                "AD001",
                "Lebohang",
                "Booi",
                "adminLB@cput.ac.za",
                "SecurePass123",
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        Admin createdAdmin = this.restTemplate.postForObject(url, admin, Admin.class);
        assertNotNull(createdAdmin);
        assertEquals(admin.getAdminId(), createdAdmin.getAdminId());
        admin = createdAdmin;
        System.out.println("Created: " + createdAdmin);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + admin.getAdminId();
        ResponseEntity<Admin> createdAdmin =
                restTemplate.getForEntity(url, Admin.class);
        assertNotNull(createdAdmin);
        System.out.println(createdAdmin.getStatusCode());
        System.out.println(createdAdmin.getBody());
    }

    @Test
    void c_update() {
        Admin updatedAdmin = new Admin.Builder().copy(admin)
                .setFirstName("Jonathan")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedAdmin);
        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedAdmin.getAdminId(), Admin.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + admin.getAdminId();
        this.restTemplate.delete(url);

        ResponseEntity<Admin> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + admin.getAdminId(), Admin.class);
        assertNull(response.getBody());
        System.out.println("Admin deleted:" + "true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Admin[]> response = this.restTemplate.getForEntity(url, Admin[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Admin admin : response.getBody()) {
            System.out.println(admin);
        }
    }

    @Test
    void f_findByEmail() {
        String url = BASE_URL + "/findByEmail/" + admin.getEmail();
        ResponseEntity<Admin> response = this.restTemplate.getForEntity(url, Admin.class);
        assertNotNull(response.getBody());
        assertEquals(admin.getEmail(), response.getBody().getEmail());
        System.out.println("Found by email: " + response.getBody());
    }
}