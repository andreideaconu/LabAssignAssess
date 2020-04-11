package LabAssignAssessMV;

import LabAssignAssessMV.domain.Student;
import LabAssignAssessMV.domain.Tema;
import LabAssignAssessMV.repository.NotaXMLRepo;
import LabAssignAssessMV.repository.StudentXMLRepo;
import LabAssignAssessMV.repository.TemaXMLRepo;
import LabAssignAssessMV.service.Service;
import LabAssignAssessMV.validation.NotaValidator;
import LabAssignAssessMV.validation.StudentValidator;
import LabAssignAssessMV.validation.TemaValidator;
import LabAssignAssessMV.validation.ValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AssignmentTestsWBT
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
    Service service;
    int newID;

    @Before
    public void init() {
        newID = new Random().nextInt(100000) + 100;
    }

    @Test
    public void tc_1_AddAssignment() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment1 = new Tema(newID + "","testAssignment", 13, 7);

        assertNull(temaXMLRepository.findOne(newID + ""));

        service.addTema(assignment1);

        assertNotNull(temaXMLRepository.findOne(newID + ""));
        assertEquals(temaXMLRepository.findOne(newID + "").getDescriere(), "testAssignment");
    }

    @Test(expected = ValidationException.class)
    public void tc_2_AddAssignment_EmptyNumber() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment2 = new Tema("","testAssignment", 13, 7);

        service.addTema(assignment2);
    }

    @Test(expected = ValidationException.class)
    public void tc_3_AddAssignment_EmptyDescription() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment3 = new Tema(newID + "","", 13, 7);

        service.addTema(assignment3);
    }

    @Test(expected = ValidationException.class)
    public void tc_4_AddAssignment_TooSmallDeadline() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment4 = new Tema(newID + "","testAssignment", 0, 7);

        service.addTema(assignment4);
    }

    @Test(expected = ValidationException.class)
    public void tc_5_AddAssignment_TooBigDeadline() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment5 = new Tema(newID + "","testAssignment", 15, 7);

        service.addTema(assignment5);
    }

    @Test(expected = ValidationException.class)
    public void tc_6_AddAssignment_DeadlineBeforeReceived() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment6 = new Tema(newID + "","testAssignment", 5, 7);

        service.addTema(assignment6);
    }

    @Test(expected = ValidationException.class)
    public void tc_7_AddAssignment_TooSmallReceived() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment7 = new Tema(newID + "","testAssignment", 5, 0);

        service.addTema(assignment7);
    }

    @Test(expected = ValidationException.class)
    public void tc_8_AddAssignment_TooBigReceived() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment8 = new Tema(newID + "","testAssignment", 5, 15);

        service.addTema(assignment8);
    }

    @Test(expected = ValidationException.class)
    public void tc_9_AddAssignment_WrongIDAndReceived() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment9 = new Tema("","testAssignment", 5, 15);

        service.addTema(assignment9);
    }

    @Test(expected = ValidationException.class)
    public void tc_10_AddAssignment_WrongIDAndDeadline() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment10 = new Tema("","testAssignment", 15, 5);

        service.addTema(assignment10);
    }

    @Test(expected = ValidationException.class)
    public void tc_11_AddAssignment_WrongReceivedAndDeadline() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment11 = new Tema(newID + "","testAssignment", 0, 0);

        service.addTema(assignment11);
    }

    @Test(expected = ValidationException.class)
    public void tc_12_AddAssignment_WrongData() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment12 = new Tema("","", 15, 0);

        service.addTema(assignment12);
    }

    @Test
    public void tc_13_AddAssignment_ExistentID() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Tema assignment13 = new Tema(7 + "","test", 10, 6);

        Tema result = service.addTema(assignment13);

        assertNotEquals(result, null);
    }

}
