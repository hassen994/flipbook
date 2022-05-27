package tn.pfe.discovery.service;

import java.util.stream.Stream;

import org.springframework.data.repository.query.Param;

import tn.pfe.discovery.entity.Images;

public interface ImageService {
	
	public Images getFile(String id);
	public Stream<Images> getAllFiles();
	Stream<Images> getAllImagesByForeignkey(@Param("pdfId") String id);

}
