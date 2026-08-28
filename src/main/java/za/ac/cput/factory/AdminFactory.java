package za.ac.cput.factory;
import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;
/*
AdminFactory.java
Admin with factory class
Author: Thimna Booi - 230232108
Date: 16/03/2026
 */

import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;
import java.time.LocalDateTime;

public class AdminFactory {

    public static Admin createAdmin(String adminId, String firstName, String lastName,
                                    String email, String password) {

        if (Helper.isNullOrEmpty(adminId) ||
                Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Admin.Builder()
                .setAdminId(adminId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setCreatedAt(LocalDateTime.now())
                .setLastLogin(LocalDateTime.now())
                .build();
    }

    public static Admin createAdmin(String adminId, String firstName, String lastName,
                                    String email, String password,
                                    LocalDateTime createdAt) {
        if (Helper.isNullOrEmpty(adminId) ||
                Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Admin.Builder()
                .setAdminId(adminId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setCreatedAt(createdAt != null ? createdAt : LocalDateTime.now())
                .setLastLogin(LocalDateTime.now())
                .build();
    }


    public static Admin createAdminWithLogin(String adminId, String firstName, String lastName,
                                             String email, String password,
                                             LocalDateTime lastLogin) {
        if (Helper.isNullOrEmpty(adminId) ||
                Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Admin.Builder()
                .setAdminId(adminId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setCreatedAt(LocalDateTime.now())
                .setLastLogin(lastLogin)
                .build();
    }


    public static Admin createAdminFull(String adminId, String firstName, String lastName,
                                        String email, String password,
                                        LocalDateTime createdAt, LocalDateTime lastLogin) {
        if (Helper.isNullOrEmpty(adminId) ||
                Helper.isNullOrEmpty(firstName) ||
                Helper.isNullOrEmpty(lastName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Admin.Builder()
                .setAdminId(adminId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)

                .setCreatedAt(createdAt != null ? createdAt : LocalDateTime.now())
                .setLastLogin(lastLogin != null ? lastLogin : LocalDateTime.now())
                .build();

    }
}