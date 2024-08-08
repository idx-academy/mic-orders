package com.academy.orders.domain.common.respository;

/**
 * Repository interface for using third party api and load image links.
 */
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
