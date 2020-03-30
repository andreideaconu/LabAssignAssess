package LabAssignAssessMV;

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

    @Test
    public void tc_1_AddAssignment() {
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        int newID = new Random().nextInt(100000) + 100;

        Tema assignment1 = new Tema(newID + "","testAssignment", 13, 7);

        assertNull(temaXMLRepository.findOne(newID + ""));

        service.addTema(assignment1);

        assertNotNull(temaXMLRepository.findOne(newID + ""));
        assertEquals(temaXMLRepository.findOne(newID + "").getDescriere(), "testAssignment");
    }

    @Test
    public void tc_2_AddAssignment_EmptyNumber() {
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema assignment2 = new Tema("","testAssignment", 13, 7);
        try {
            service.addTema(assignment2);
        }
        catch (ValidationException e) {
            final String msg = "Numar tema invalid!";
            assertEquals(msg, e.getMessage());
        }
    }

}
