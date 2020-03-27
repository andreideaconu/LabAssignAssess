package LabAssignAssessMV.app;


import LabAssignAssessMV.repository.NotaFileRepository;
import LabAssignAssessMV.repository.StudentFileRepository;
import LabAssignAssessMV.repository.StudentXMLRepo;
import LabAssignAssessMV.repository.TemaXMLRepo;
import LabAssignAssessMV.repository.NotaXMLRepo;
import LabAssignAssessMV.repository.TemaFileRepository;
import LabAssignAssessMV.service.Service;
import LabAssignAssessMV.validation.NotaValidator;
import LabAssignAssessMV.validation.StudentValidator;
import LabAssignAssessMV.validation.TemaValidator;
import LabAssignAssessMV.view.UI;



public class MainApplication {

    public static void main(String[] args) {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

       //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        UI ui = new UI(service);
        ui.run();
    }

}
