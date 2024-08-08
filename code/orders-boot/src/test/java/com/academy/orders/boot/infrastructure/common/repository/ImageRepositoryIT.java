package com.academy.orders.boot.infrastructure.common.repository;

import com.academy.orders.boot.Application;
import com.academy.orders.domain.common.respository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("it")
class ImageRepositoryIT {
	@Autowired
	private ImageRepository imageRepository;

	@Test
	void getImageLinkByNameTest() {
		var imageName = "image.jpg";
		var expected = "https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/" + imageName;
		var actual = imageRepository.getImageLinkByName(imageName);

		assertEquals(expected, actual);
	}
}
