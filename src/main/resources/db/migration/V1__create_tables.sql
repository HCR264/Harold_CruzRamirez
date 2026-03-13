CREATE TABLE users (
	id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	email VARCHAR(50) NOT NULL,
	name VARCHAR(200) NOT NULL,
	phone VARCHAR(20) NOT NULL,
	password VARCHAR(255) NOT NULL,
	tax_id VARCHAR(13) UNIQUE NOT NULL,
	created_at VARCHAR(20) NOT NULL
);

CREATE TABLE addresses (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	street VARCHAR(50) NOT NULL,
	country_code VARCHAR(3) NOT NULL,
	user_id UUID NOT NULL,
	constraint fk_address_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);