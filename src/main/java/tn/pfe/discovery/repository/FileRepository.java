package tn.pfe.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.discovery.entity.Pdf;

@Repository
public interface FileRepository extends JpaRepository<Pdf,String> {

}
