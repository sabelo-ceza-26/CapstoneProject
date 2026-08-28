package za.ac.cput.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.factory.SubjectFactory;
import za.ac.cput.factory.TutorFactory;
import za.ac.cput.factory.TutorSubjectFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
StudentServiceTest.java
Student Service Test
Author: Sabelo Ceza - 220094489
Date: 25/06/2026
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TutorSubjectServiceTest {

    @Autowired
    private TutorSubjectService service;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TutorService tutorService;
    private static final List<Booking> bookings = new ArrayList<>();

    private static final Tutor tutor = TutorFactory.createTutor(
            "T001",
            "Imaan",
            "Achmat",
            "imaan@gmail.com",
            "0211377053",
            "password",
            150.0,
            bookings
    );
    private static final Subject subject = SubjectFactory.createSubject(
            "ADP362S",
            "Application Development Practice",
            "Programming Module",
            "3rd Year"
    );

    private static final TutorSubject tutorSubject;

    static {
        tutorSubject = TutorSubjectFactory.createTutorSubject(
                "ADP362S",
                "T001",
                5,
                subject,
                tutor
        );
    }

    @Test
    @Transactional
    void a_create() {
        Subject savedSubject = subjectService.create(subject);
        Tutor savedTutor = tutorService.create(tutor);
        
        TutorSubject testTutorSubject = new TutorSubject.Builder()
                .setSubjectCode(savedSubject.getSubjectCode())
                .setTutorId(savedTutor.getTutorId())
                .setYearsTaught(5)
                .setSubject(savedSubject)
                .setTutor(savedTutor)
                .build();
        
        TutorSubject created = service.create(testTutorSubject);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    @Transactional
    void b_read() {
        TutorSubjectId id = new TutorSubjectId(
                "ADP362S",
                "T001"
        );

        TutorSubject found = service.read(id);

        assertNotNull(found);
        assertEquals("ADP362S", found.getSubjectCode());
        assertEquals("T001", found.getTutorId());
        System.out.println(found);
    }

    @Test
    @Transactional
    void c_update() {
        TutorSubject newTutorSubject = new TutorSubject.Builder()
                .setSubjectCode("ADP362S")
                .setTutorId("T001")
                .setYearsTaught(10)
                .setSubject(subject)
                .setTutor(tutor)
                .build();

        TutorSubject updated = service.update(newTutorSubject);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Disabled
    void e_delete() {
        TutorSubjectId id = new TutorSubjectId(
                tutorSubject.getSubjectCode(),
                tutorSubject.getTutorId()
        );
        boolean deleted = service.delete(id);
        assertTrue(deleted);
    }


    @Test
    @Transactional
    void d_getAll() {
        System.out.println(service.getAll());
    }
}