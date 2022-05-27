package tn.pfe.discovery.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.pfe.discovery.entity.Images;

@Repository
public interface ImagesRepository extends JpaRepository<Images, String> {
	
	@Query("SELECT i FROM Images i WHERE i.pdf.id= :pdfId")
	Stream<Images> getAllImagesByForeignkey(@Param("pdfId") String id);

}
