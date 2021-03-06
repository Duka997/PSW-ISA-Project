package com.softwareComedians.ClinicalCenterApp.controller;

import com.softwareComedians.ClinicalCenterApp.common.consts.UserRoles;
import com.softwareComedians.ClinicalCenterApp.dto.ConsultDTO;
import com.softwareComedians.ClinicalCenterApp.dto.ConsultTermDTO;
import com.softwareComedians.ClinicalCenterApp.dto.PatientDTO;
import com.softwareComedians.ClinicalCenterApp.dto.UserDTO;
import com.softwareComedians.ClinicalCenterApp.mail.SmtpMailSender;
import com.softwareComedians.ClinicalCenterApp.mappers.UserMapper;
import com.softwareComedians.ClinicalCenterApp.model.*;
import com.softwareComedians.ClinicalCenterApp.repository.AuthorityRepository;
import com.softwareComedians.ClinicalCenterApp.service.*;
import com.softwareComedians.ClinicalCenterApp.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/patient")
@CrossOrigin
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RequestForPatientRegistrationService requestForPatientRegistrationService;

    @Autowired
    private ConsultTermService consultTermService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RequestForConsultService requestForConsultService;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @Autowired
    private ClinicsService clinicsService;

    @Autowired
    public PatientController(PatientService patientService, RequestForPatientRegistrationService requestForPatientRegistrationService) {
        this.patientService = patientService;
        this.requestForPatientRegistrationService = requestForPatientRegistrationService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PatientDTO>> getAll() {
        List<PatientDTO> patientsDTO = patientService.getAll();
        return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "add/{id}")
    public ResponseEntity<Void> addPatient(@PathVariable Long id) throws URISyntaxException {

        RequestForPatientRegistration request = requestForPatientRegistrationService.findOne(id);
        requestForPatientRegistrationService.remove(id);

        Patient patient = new Patient(request.getUserData());
        MedicalRecord medicalRecord = MedicalRecord.builder()
                                        .alergies("-")
                                        .bloodType("-")
                                        .weight("-")
                                        .height("-")
                                        .diopter("-")
                                        .build();
        patient.setMedicalRecord(medicalRecord);
        patient.setActivated(true);
        patient.setRole(UserRoles.ROLE_PATIENT);

        patient = patientService.save(patient);

        HttpHeaders headers;
        headers = new HttpHeaders();
        headers.setLocation(new URI("http://localhost:4200/#/login"));

        if(patient!=null)
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAllExaminations")
    //@PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<ConsultTermDTO>> getAllExaminations() {
        List<ConsultTerm> terms = consultTermService.findAll();
        List<ConsultTermDTO> termsDTO = new ArrayList<>();

        for (ConsultTerm t : terms) {
            termsDTO.add(new ConsultTermDTO(t));
        }

        return new ResponseEntity<>(termsDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll/{id}")
    //@PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<ConsultDTO>> getAll(@PathVariable Long id) {
        List<ConsultDTO> consults = patientService.findAllConsult(id);
        if(consults == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return  new ResponseEntity<>(consults, HttpStatus.OK);
        }
    }

    @PreAuthorize("ROLE_PATIENT")
    @PostMapping(value = "/edit")
    public ResponseEntity<UserDTO> editPatient(@RequestBody PatientDTO patientDTO) {
        User userInfo = patientService.editPatient(patientDTO);
        return new ResponseEntity<>(UserMapper.toDto(userInfo), HttpStatus.OK);
    }

    @GetMapping(value = "requestConsultTerm/{id}")
    public ResponseEntity<Void> requestConsultTerm(@PathVariable Long id) throws MessagingException {
        RequestForConsult request = requestForConsultService.findById(id);

        smtpMailSender.send("pswtim2@gmail.com","Consult term accepted",
                " You can confirm or reject your request for consult: "+ "\r\n"+
                        " <a href='http://localhost:8080/api/patient/acceptConsultTerm/"+request.getId()+"'> Confirm </a>"+ "\r\n"+
                        " <a href='http://localhost:8080/api/patient/rejectConsultTerm/"+request.getId()+"'> Reject </a>");

        if(request!=null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "acceptConsultTerm/{id}")
    public ResponseEntity<Void> acceptConsultTerm(@PathVariable Long id) {
        RequestForConsult request = requestForConsultService.findById(id);
        request.setAccepted(true);

        request = requestForConsultService.save(request);

        if(request!=null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "rejectConsultTerm/{id}")
    public ResponseEntity<Void> rejectConsultTerm(@PathVariable Long id) {
        //TODO: ovo obrise nekoliko tabela!? a treba samo consult term sa prosledjenim ID-jem
        requestForConsultService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
