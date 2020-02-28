package com.edugroupe.demo.repositories.custom;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.edugroupe.demo.metiers.ImageRecette;
import com.edugroupe.demo.util.FileStorageManager;

public class ImageRecetteRepositoryComplementImpl implements ImageRecetteRepositoryComplement {

	@Autowired
	private FileStorageManager fileStorageManager;
	
	@Override
	public boolean saveImageFile(ImageRecette image, InputStream file) {

		String storageId = fileStorageManager.saveNewFile("images_de_recette", file);
		image.setStorageId(storageId);
		
		return true;
		
	}
		
	@Override
	public Optional<File> getImageFile(String storageId) {
		return fileStorageManager.getFile(storageId);
	}

	@Override
	public boolean deleteImageFile(ImageRecette image) {
		return fileStorageManager.deleteFile(image.getStorageId());
	}

}
