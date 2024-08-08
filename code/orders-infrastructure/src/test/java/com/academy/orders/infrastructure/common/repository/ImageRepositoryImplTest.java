package com.academy.orders.infrastructure.common.repository;

import com.academy.images_api.generated.api.ImagesApi;
import com.academy.images_api.generated.model.ImageUrlDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageRepositoryImplTest {
	@Mock
	private ImagesApi imagesApi;

	@InjectMocks
	private ImageRepositoryImpl imageRepository;

	@Test
	void getImageLinkByNameTest() {
		String imageName = "example";
		String imageUrl = "https://example.com/image.png";

		when(imagesApi.getImageByName(imageName)).thenReturn(new ImageUrlDTO().imageUrl(imageUrl));

		String result = imageRepository.getImageLinkByName(imageName);

		assertEquals(imageUrl, result);
		verify(imagesApi, times(1)).getImageByName(imageName);
	}
}
