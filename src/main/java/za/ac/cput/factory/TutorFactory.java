package za.ac.cput.factory;
/*
tutorfactory.java
Tutor Factory Class
Author: Imaan Achmat
(230458971)
Date: 15 March 2026
 */

import za.ac.cput.domain.Tutor;
import za.ac.cput.util.Helper;

public class TutorFactory {
    public static Tutor createTutor(String tutorId, String firstName, String lastName, String email,
                                    String phoneNumber, String password,
                                    double hourlyRate) {

        if (Helper.isNullOrEmpty(tutorId)
                || Helper.isNullOrEmpty(firstName)
                || Helper.isNullOrEmpty(lastName)
                || Helper.isNullOrEmpty(phoneNumber)
                || Helper.isNullOrEmpty(password)
                || Helper.isNullOrEmpty(email)
                || hourlyRate <= 0) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Tutor.Builder()
                .setTutorId(tutorId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setPassword(password)
                .setHourlyRate(hourlyRate)
                .build();
    }
}
