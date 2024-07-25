package com.academy.orders.domain.common.respository;

public interface ImageRepository {
	/**
	 * Method get image's url by imag`s name using third-party
	 *
	 * @param name
	 *            name of the image
	 *
	 * @return {@link String URL of the image}
	 * @author Denys Ryhal
	 */
	String getImageLinkByName(String name);
}
