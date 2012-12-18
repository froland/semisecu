package com.hermes.owasphotel.web.mvc;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadedImage {
	private CommonsMultipartFile file;

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
