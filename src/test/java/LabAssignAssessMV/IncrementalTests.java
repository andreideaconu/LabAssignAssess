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
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class IncrementalTests {
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
    public void tc_1_AddStudent_Incremental() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Student student = new Student("3","Radu Preda", 932, "rpie2703@scs.ubbcluj.ro");

        service.deleteStudent("3");
        assertNull(studentXMLRepository.findOne("3"));
        service.addStudent(student);
        assertNotNull(studentXMLRepository.findOne("3"));
        assertEquals(studentXMLRepository.findOne("3").getNume(), "Radu Preda");
        assertEquals(studentXMLRepository.findOne("3").getGrupa(), 932);
        assertEquals(studentXMLRepository.findOne("3").getEmail(), "rpie2703@scs.ubbcluj.ro");
    }

    @Test
    public void tc_2_AddAssignment_Incremental() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

        Tema assignment = new Tema("3", "Assignment3_inLab4_and_inLab5_TakeHome_inLab6", 10, 8);

        service.deleteTema("3");
        assertNull(temaXMLRepository.findOne("3"));
        service.addTema(assignment);
        assertNotNull(temaXMLRepository.findOne("3"));
        assertEquals(temaXMLRepository.findOne("3").getDescriere(), "Assignment3_inLab4_and_inLab5_TakeHome_inLab6");
        assertEquals(temaXMLRepository.findOne("3").getDeadline(), 10);
        assertEquals(temaXMLRepository.findOne("3").getPrimire(), 8);
    }

    @Test
    public void tc_3_AddGrade_Incremental() {
        LocalDate date = LocalDate.now();
        Nota grade = new Nota("3","3","3",10, date);

        notaXMLRepository.delete("3");
        assertNull(notaXMLRepository.findOne("3"));
        notaXMLRepository.save(grade);
        assertNotNull(notaXMLRepository.findOne("3"));
        assertEquals(notaXMLRepository.findOne("3").getIdStudent(), "3");
        assertEquals(notaXMLRepository.findOne("3").getIdTema(), "3");
        assertEquals(notaXMLRepository.findOne("3").getNota(), 10, 0);
        assertEquals(notaXMLRepository.findOne("3").getData(), date);
    }

    @Test
    public void tc_4_AddStudent_AddAssignment() {
        tc_1_AddStudent_Incremental();
        tc_2_AddAssignment_Incremental();
    }

    @Test
    public void tc_5_AddStudent_AddAssignment_AddGrade() {
        tc_1_AddStudent_Incremental();
        tc_2_AddAssignment_Incremental();
        tc_3_AddGrade_Incremental();
    }
}