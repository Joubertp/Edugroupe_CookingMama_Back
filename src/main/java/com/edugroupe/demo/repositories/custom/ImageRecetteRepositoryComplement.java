package com.edugroupe.demo.repositories.custom;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import com.edugroupe.demo.metiers.ImageRecette;

public interface ImageRecetteRepositoryComplement {

	boolean saveImageFile(ImageRecette image, InputStream file);
	Optional<File> getImageFile(String storageId);
	boolean deleteImageFile(ImageRecette image);
	
}
