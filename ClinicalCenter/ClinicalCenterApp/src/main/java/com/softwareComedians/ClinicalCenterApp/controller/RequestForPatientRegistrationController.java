package com.softwareComedians.ClinicalCenterApp.controller;

import com.softwareComedians.ClinicalCenterApp.dto.RequestForPatientRegistrationDTO;
import com.softwareComedians.ClinicalCenterApp.model.ConfirmationToken;
import com.softwareComedians.ClinicalCenterApp.model.RequestForPatientRegistration;
import com.softwareComedians.ClinicalCenterApp.repository.ConfirmationTokenRepository;
import com.softwareComedians.ClinicalCenterApp.service.RequestForPatientRegistrationService;
import com.softwareComedians.ClinicalCenterApp.service.email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/RqForPatientReg")
public class RequestForPatientRegistrationController {

    private RequestForPatientRegistrationService requestForPatientRegistrationService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public RequestForPatientRegistrationController(RequestForPatientRegistrationService requestForPatientRegistrationService) {
        this.requestForPatientRegistrationService = requestForPatientRegistrationService;
    }

    @PostMapping()
    public ResponseEntity<RequestForPatientRegistrationDTO> createRqForPatientReg(@RequestBody RequestForPatientRegistrationDTO rqDTO) {

        RequestForPatientRegistration rq = requestForPatientRegistrationService.createRqForPatientReg(rqDTO);
        return new ResponseEntity<>(new RequestForPatientRegistrationDTO(rq), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<RequestForPatientRegistrationDTO>> getAll() {
        List<RequestForPatientRegistrationDTO> rqsDTO = requestForPatientRegistrationService.getAll();
        return new ResponseEntity<>(rqsDTO, HttpStatus.OK);
    }

    private ConfirmationToken createConfirmationToken(RequestForPatientRegistration user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        return confirmationTokenRepository.save(confirmationToken);
    }


}
