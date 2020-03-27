package LabAssignAssessMV;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import LabAssignAssessMV.domain.Student;
import LabAssignAssessMV.repository.NotaXMLRepo;
import LabAssignAssessMV.repository.StudentXMLRepo;
import LabAssignAssessMV.repository.TemaXMLRepo;
import LabAssignAssessMV.service.Service;
import LabAssignAssessMV.validation.NotaValidator;
import LabAssignAssessMV.validation.StudentValidator;
import LabAssignAssessMV.validation.TemaValidator;
import LabAssignAssessMV.validation.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    String filenameStudent = "fisiere/Studenti.xml";
    String filenameTema = "fisiere/Teme.xml";
    String filenameNota = "fisiere/Note.xml";
    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void tc_1_NewStudent() {

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        int newID = new Random().nextInt(100000) + 30;
        Student student1 = new Student(newID + "","Deaconu Andrei", new Random().nextInt(6) + 931, "daie2301@scs.ubbcluj.ro");
        Student result1 = service.addStudent(student1);

        assertEquals(result1, student1);
    }

    @Test
    public void tc_2_NewStudent_ExistentID() {

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Student student2 = new Student("7","Deaconu Andrei", 932, "daie2301@scs.ubbcluj.ro");

        Student result2 = service.addStudent(student2);

        assertEquals(result2, null);
    }

    @Test(expected = ValidationException.class)
    public void tc_5_NewStudent_NegativeGroup() {

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        int newID = new Random().nextInt(100000) + 30;
        Student student5 = new Student(newID + "", "Deaconu Andrei", -1, "daie2301@scs.ubbcluj.ro");

        Student result = service.addStudent(student5);



        assertEquals(result, null);
    }

    @Test(expected = ValidationException.class)
    public void tc_6_NewStudent_EmptyName() {

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        int newID = new Random().nextInt(100000) + 30;
        Student student6 = new Student(newID + "", "", 932, "daie2301@scs.ubbcluj.ro");


        Student result = service.addStudent(student6);



        assertEquals(result, null);
    }

    @Test
    public void tc_3_NewStudent_NullEmail() {

        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        int newID = new Random().nextInt(100000) + 30;
        Student student3 = new Student(newID + "","Deaconu Andrei", 932, null);

        Iterable<Student> students = service.getAllStudenti();
        try {
            service.addStudent(student3);
        }
        catch (ValidationException e) {
            final String msg = "Email incorect!";
            assertEquals(msg, e.getMessage());
        }
    }
}
