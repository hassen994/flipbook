package tn.pfe.discovery.service;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import tn.pfe.discovery.entity.Images;
import tn.pfe.discovery.repository.ImagesRepository;

@Service
public class ImageServiceImpl implements ImageService {
	
	ImagesRepository imagesRepository;
	
	

	public ImageServiceImpl(ImagesRepository imagesRepository) {
		super();
		this.imagesRepository = imagesRepository;
	}

	@Override
	public Images getFile(String id) {
		// TODO Auto-generated method stub
		return imagesRepository.findById(id).get();
	}

	@Override
	public Stream<Images> getAllFiles() {
		
		return imagesRepository.findAll().stream();
	}

	@Override
	public Stream<Images> getAllImagesByForeignkey(String id) {
		// TODO Auto-generated method stub
		return imagesRepository.getAllImagesByForeignkey(id);
	}

}
