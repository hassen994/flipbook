package tn.pfe.discovery.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tn.pfe.discovery.entity.Images;
import tn.pfe.discovery.entity.Pdf;
import tn.pfe.discovery.repository.FileRepository;
import tn.pfe.discovery.repository.ImagesRepository;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	private FileRepository fileRepository;
	private ImagesRepository imagesRepository;
	
	public  FileStorageServiceImpl (FileRepository fileRepository, ImagesRepository imagesRepository) {
		this.fileRepository = fileRepository;
		this.imagesRepository = imagesRepository;
		
	}
	
	@Override
	public Pdf store(MultipartFile file) throws IOException {
		String name = StringUtils.cleanPath(file.getOriginalFilename());
		Pdf pdfFile = new Pdf(name, file.getContentType(), file.getBytes());
		Pdf stored =  fileRepository.save(pdfFile);
		PDDocument doc = PDDocument.load(file.getBytes());
		PDFRenderer pdfRenderer = new PDFRenderer(doc);
		for (int page = 0 ; page < doc.getNumberOfPages();++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bim, "jpg", baos);
			baos.flush();
			
			Images img = new Images("image_"+page,"jpeg", baos.toByteArray(),pdfFile);
			imagesRepository.save(img);
			
		}
		doc.close();

		return stored;
	}

	@Override
	public Pdf getFile(String id) {
		Pdf pdf =  fileRepository.findById(id).get();
		return pdf;
	}

	@Override
	public Stream<Pdf> getAllFiles() {
		return fileRepository.findAll().stream();
	}

}
