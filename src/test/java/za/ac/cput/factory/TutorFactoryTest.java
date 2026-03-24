package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Tutor;
import static org.junit.jupiter.api.Assertions.*;

/*
TutorRepositoryTest.java
Tutor repository testing
Author: Imaan Achmat
230458971
Date: 25 March 2026
 */

public class TutorFactoryTest {
@Test
    void createTutor() {
    Tutor tutor = TutorFactory.createTutor("T001", "Imaan", "Achmat",
            "imaan@gmail.com", "0211377053",
            "password", 150.0);

    assertNotNull(tutor);
   System.out.println(tutor);
    }
}
