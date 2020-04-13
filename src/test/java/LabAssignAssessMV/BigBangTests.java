package LabAssignAssessMV;

import LabAssignAssessMV.domain.Nota;
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
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class BigBangTests {
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
    public void tc_1_AddStudent() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Student student = new Student("1","Deaconu Andrei", 932, "daie2301@scs.ubbcluj.ro");

        service.deleteStudent("1");
        assertNull(studentXMLRepository.findOne("1"));
        service.addStudent(student);
        assertNotNull(studentXMLRepository.findOne("1"));
        assertEquals(studentXMLRepository.findOne("1").getNume(), "Deaconu Andrei");
        assertEquals(studentXMLRepository.findOne("1").getGrupa(), 932);
        assertEquals(studentXMLRepository.findOne("1").getEmail(), "daie2301@scs.ubbcluj.ro");
    }

    @Test
    public void tc_2_AddAssignment() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Tema assignment = new Tema("1", "testAssignment", 13, 7);

        service.deleteTema("1");
        assertNull(temaXMLRepository.findOne("1"));
        service.addTema(assignment);
        assertNotNull(temaXMLRepository.findOne("1"));
        assertEquals(temaXMLRepository.findOne("1").getDescriere(), "testAssignment");
        assertEquals(temaXMLRepository.findOne("1").getDeadline(), 13);
        assertEquals(temaXMLRepository.findOne("1").getPrimire(), 7);
    }

    @Test
    public void tc_3_AddGrade() {
        LocalDate date = LocalDate.now();
        Nota grade = new Nota("1","1","1",9.75, date);

        notaXMLRepository.delete("1");
        assertNull(notaXMLRepository.findOne("1"));
        notaXMLRepository.save(grade);
        assertNotNull(notaXMLRepository.findOne("1"));
        assertEquals(notaXMLRepository.findOne("1").getIdStudent(), "1");
        assertEquals(notaXMLRepository.findOne("1").getIdTema(), "1");
        assertEquals(notaXMLRepository.findOne("1").getNota(), 9.75, 0);
        assertEquals(notaXMLRepository.findOne("1").getData(), date);
    }

    @Test
    public void tc_4_BigBang() {
        tc_1_AddStudent();
        tc_2_AddAssignment();
        tc_3_AddGrade();
    }
}