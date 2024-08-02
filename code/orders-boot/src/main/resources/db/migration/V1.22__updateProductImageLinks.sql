UPDATE products
SET image_link = substring(image_link from '([^/]+)$');