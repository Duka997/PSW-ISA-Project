package com.softwareComedians.ClinicalCenterApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patient extends User {
	//preuzmi user data iz requesta
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicalRecord", referencedColumnName = "id")
	private MedicalRecord medicalRecord;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<ConsultTerm> appointedTerms;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;

//	@ManyToMany
//	@JoinTable(name = "PersonnelPatient", joinColumns = @JoinColumn(name = "pat_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "presonnel_id", referencedColumnName = "id"))
	//private Set<Personnel> personnels;
	
	public Patient() {
		super();
	}

	
}
