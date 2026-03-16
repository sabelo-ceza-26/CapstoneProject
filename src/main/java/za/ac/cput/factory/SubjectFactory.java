package za.ac.cput.factory;

import za.ac.cput.domain.Subject;
import za.ac.cput.util.Helper;

public class SubjectFactory {

    public static Subject createSubject(String subjectCode,
                                        String subjectName, String subjectDescription,
                                        String gradeLevel) {

        if (Helper.isNullOrEmpty(subjectCode)
                || Helper.isNullOrEmpty(subjectName)
                || Helper.isNullOrEmpty(subjectDescription)
                || Helper.isNullOrEmpty(gradeLevel))
        {
            return null;
        }
        return new Subject.Builder()
                .setSubjectCode(subjectCode)
                .setSubjectName(subjectName)
                .setSubjectDescription(subjectDescription)
                .setGradeLevel(gradeLevel)
                .build();

    }
}