package com.softwareComedians.ClinicalCenterApp.dto;

import com.softwareComedians.ClinicalCenterApp.model.RequestForPatientRegistration;

public class RequestForPatientRegistrationDTO {
    private UserDTO userData;
    private Long id;
    private boolean isAccepted;
    private String reasonOfRejection;

    public RequestForPatientRegistrationDTO() {
    }

    public RequestForPatientRegistrationDTO(UserDTO userData, Long id, boolean isAccepted, String reasonOfRejection) {
        this.userData = userData;
        this.id = id;
        this.isAccepted = isAccepted;
        this.reasonOfRejection = reasonOfRejection;
    }
    public RequestForPatientRegistrationDTO(RequestForPatientRegistration rq){
        this.id=rq.getId();
        this.userData= new UserDTO(rq.getUserData());
        this.isAccepted = rq.isAccepted();
        this.reasonOfRejection = rq.getReasonOfRejection();
    }

    public UserDTO getUserData() {
        return userData;
    }

    public void setUserData(UserDTO userData) {
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }

    public void setReasonOfRejection(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }
}
