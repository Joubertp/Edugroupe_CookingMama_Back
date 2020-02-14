package com.edugroupe.demo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileStorageManager {

	@Value("${filestorage.directory}")
	private File storageRoot;
	
	public String saveNewFile(String collection, InputStream data) {
		
		if (storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
			throw new RuntimeException("storage root invalid");
		}
		
		String name = collection + "#" + LocalDateTime.now().getNano();
		String sha1Name = DigestUtils.sha1Hex(name);
		String sousRep = sha1Name.substring(0, 2);
	
		File rep = Paths.get(storageRoot.getAbsolutePath(), sousRep).toFile();
		
		if (!rep.exists())
			rep.mkdirs();
		
		if (!rep.isDirectory())
			throw new RuntimeException("unable to create storage directory for " + sha1Name);
		
		System.out.println("sauvegarde du fichier " + sha1Name);
		
		try {
			Files.copy(data, Paths.get(rep.getAbsolutePath(), sha1Name), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("unable to save file", e);
		}
		
		return sha1Name;
	}
	
	public Optional<File> getFile(String storageId) {
		
		if (storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
			throw new RuntimeException("storage root invalid");
		}
		
		String sousRep = storageId.substring(0, 2);
		
		File rep = Paths.get(storageRoot.getAbsolutePath(), sousRep).toFile();
		
		if (!rep.exists() || !rep.isDirectory()) {
			return Optional.empty();
		}
		
		File f = Paths.get(rep.getAbsolutePath(), storageId).toFile();
		
		if (f != null && f.exists() && f.isFile())
			return Optional.of(f);
		else
			return Optional.empty();		
	}
	
	public boolean deleteFile(String storageId) {
		
		if (storageRoot == null || !storageRoot.exists() || !storageRoot.isDirectory()) {
			return false;
		}
		
		String sousRep = storageId.substring(0, 2);
		
		File rep = Paths.get(storageRoot.getAbsolutePath(), sousRep).toFile();
		
		if (!rep.exists() || !rep.isDirectory()) {
			return false;
		}
		
		File f = Paths.get(rep.getAbsolutePath(), storageId).toFile();
		
		if (f != null && f.exists() && f.isFile()) {
			System.out.println("suppression du fichier " + storageId);
			return f.delete();
		}
		else
			return false;
	}
	
}
