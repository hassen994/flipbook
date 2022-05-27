package tn.pfe.discovery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.pfe.discovery.entity.Pdf;
import tn.pfe.discovery.message.ResponseFile;
import tn.pfe.discovery.message.ResponseMessage;
import tn.pfe.discovery.service.FileStorageService;

@RestController
@CrossOrigin("http://localhost:8083")
public class FilePdfController {
	
	private FileStorageService fileStorageService;

	public FilePdfController(FileStorageService fileStorageService) {
		
		this.fileStorageService = fileStorageService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> fileUpload(@RequestParam("file") MultipartFile file){
		String message="";
		try {
			fileStorageService.store(file);
			message = "upload file successfully: "+file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			 message = "Could not upload the file: " + file.getOriginalFilename() + "!";
		      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
		    		  .body(new ResponseMessage(message));
		    }
		}
	@GetMapping("/files/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
	    Pdf pdf = fileStorageService.getFile(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION,
	        		"attachment; filename=\"" + pdf.getFileName() + "\"")
	        .body(pdf.getData());
	  }
	  
	  @GetMapping("/files")
	  public ResponseEntity<List<ResponseFile>> getListFiles() {
	    List<ResponseFile> files = fileStorageService.getAllFiles().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/files/")
	          .path(dbFile.getId())
	          .toUriString();
	      return new ResponseFile(
	    	  dbFile.getId(),
	          dbFile.getFileName(),
	          fileDownloadUri,
	          dbFile.getFileType(),
	          dbFile.getData().length);
	    }).collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(files);
	
	}
}


