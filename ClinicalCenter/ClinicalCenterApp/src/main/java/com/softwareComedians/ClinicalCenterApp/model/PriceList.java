package com.softwareComedians.ClinicalCenterApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "priceList")
@Getter
@Setter
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "priceList")
    private Set<PriceListItem>  priceList;

    @OneToOne(mappedBy = "priceList")
    private Clinic clinic;


    public PriceList() {

    }


}
