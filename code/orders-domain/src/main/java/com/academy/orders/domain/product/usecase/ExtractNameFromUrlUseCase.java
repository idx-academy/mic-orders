package com.academy.orders.domain.product.usecase;

/**
 * Use case interface for extracting image name from url.
 */
public interface ExtractNameFromUrlUseCase {
	/**
	 * Extracts the name or identifier from the given URL.
	 *
	 * @param url
	 *            the URL from which to extract the name.
	 * @return the extracted name or identifier from the URL.
	 *
	 * @author Denys Ryhal
	 */
	String extractNameFromUrl(String url);
}
