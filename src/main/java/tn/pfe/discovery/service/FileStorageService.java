package tn.pfe.discovery.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import tn.pfe.discovery.entity.Pdf;

public interface FileStorageService {
	public Pdf store(MultipartFile file) throws IOException;
	public Pdf getFile(String id);
	public Stream<Pdf> getAllFiles();
	
}
