package com.softwareComedians.ClinicalCenterApp.repository;
import com.softwareComedians.ClinicalCenterApp.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament,Long>{

    void deleteById(Long id);
}