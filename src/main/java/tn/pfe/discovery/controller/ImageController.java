package tn.pfe.discovery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.pfe.discovery.entity.Images;
import tn.pfe.discovery.message.ResponseFile;
import tn.pfe.discovery.service.ImageService;

@RestController
@CrossOrigin("http://localhost:8083")
public class ImageController {
	
	ImageService imageService;

	public ImageController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}
	
	
	@GetMapping("/images")
	public ResponseEntity<List<ResponseFile>> getListImages() {
		List<ResponseFile> images = imageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder
			          .fromCurrentContextPath()
			          .path("/images/")
			          .path(dbFile.getId())
			          .toUriString();
			      return new ResponseFile(
			    	  dbFile.getId(),
			          dbFile.getFileName(),
			          fileDownloadUri,
			          dbFile.getFileType(),
			          dbFile.getData().length);
			    }).collect(Collectors.toList());
			    return ResponseEntity.status(HttpStatus.OK).body(images);
		}
	
	@GetMapping("/pages/{id}")
	@Transactional
	public ResponseEntity<List<ResponseFile>> getAllImagesByForeignkeys(@PathVariable String id) {
		
		List<ResponseFile> pages = imageService.getAllImagesByForeignkey(id).map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder
			          .fromCurrentContextPath()
			          .path("/images/")
			          .path(dbFile.getId())
			          .toUriString();
			      return new ResponseFile(
			    	  dbFile.getId(),
			          dbFile.getFileName(),
			          fileDownloadUri,
			          dbFile.getFileType(),
			          dbFile.getData().length);
			    }).collect(Collectors.toList());
			    return ResponseEntity.status(HttpStatus.OK).body(pages);
		
		
	}
	
	@GetMapping("/images/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		Images images = imageService.getFile(id);
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION,
		        		"attachment; filename=\"" + images.getFileName() + "\"")
		        .body(images.getData());
	}

}
