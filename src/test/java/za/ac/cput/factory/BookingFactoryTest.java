package za.ac.cput.factory;
/*
BookingFactoryTest.java
Booking Factory Test
Author: Charmaine Dlamini-222056401
Date: 23/03/2026
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Booking;
import za.ac.cput.domain.Payment;

import static org.junit.jupiter.api.Assertions.*;

class BookingFactoryTest {
    private static Payment payment;
    private static Booking booking;

    @BeforeEach
    void setUp() {
        payment = PaymentFactory.createPayment("reference123", 1000.00,
                "23/03/2026", "Card", "pending");

        booking = BookingFactory.createBooking("12345", "Session1",
                "12:00", "Online", "2 hours", "222056401",
                "ADP362S", "T987654", payment);
    }

    @Test
    void createBooking() {
        assertNotNull(booking);
        System.out.println(booking);

    }
}