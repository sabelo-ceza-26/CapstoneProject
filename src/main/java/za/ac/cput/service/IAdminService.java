package za.ac.cput.service;

import za.ac.cput.domain.Admin;

import java.util.List;

/*
IAdminService.java
IAdminService
Author: Thimna Booi - 230232108
Date: 17/06/2026
 */

public interface IAdminService extends IService<Admin, String> {
    List<Admin> getAll();
    Admin findByEmail(String email);
}