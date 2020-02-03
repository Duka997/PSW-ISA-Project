package com.softwareComedians.ClinicalCenterApp.dto;

import com.softwareComedians.ClinicalCenterApp.model.RequstForOperation;

import java.sql.Date;

public class RequestForOperationDTO {
    private Long id;
    private Date dateAndTime;
    private boolean isAccepted;
    private UserDTO patient;
    private UserDTO applicant;


    public RequestForOperationDTO() {
    }

    public RequestForOperationDTO(Date dateAndTime, boolean isAccepted, Long id, UserDTO p, UserDTO a) {
        this.dateAndTime = dateAndTime;
        this.isAccepted = isAccepted;
        this.id=id;
        this.applicant = a;
        this.patient = p;
    }

    public RequestForOperationDTO(RequstForOperation rfc) {
        dateAndTime = rfc.getDateAndTime();
        isAccepted = rfc.isAccepted();
        id=rfc.getId();
      //  patient = new UserDTO(rfc.getPatient());
       // applicant = new UserDTO(rfc.getApplicant());

    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getPatient() {
        return patient;
    }

    public void setPatient(UserDTO patient) {
        this.patient = patient;
    }

    public UserDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(UserDTO applicant) {
        this.applicant = applicant;
    }
}
