CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(20),
                       city VARCHAR(100),
                       postal_code VARCHAR(20),
                       role VARCHAR(20) NOT NULL,
                       is_blocked BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gardens (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         owner_id BIGINT NOT NULL,
                         title VARCHAR(100) NOT NULL,
                         description TEXT,
                         area_size DOUBLE,
                         latitude DOUBLE,
                         longitude DOUBLE,
                         address VARCHAR(255),
                         city VARCHAR(100),
                         postal_code VARCHAR(20),
                         rules TEXT,
                         has_tools BOOLEAN DEFAULT FALSE,
                         status VARCHAR(20) DEFAULT 'AVAILABLE',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_garden_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE garden_photos (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               garden_id BIGINT NOT NULL,
                               photo_url VARCHAR(255) NOT NULL,
                               CONSTRAINT fk_photo_garden FOREIGN KEY (garden_id) REFERENCES gardens(id) ON DELETE CASCADE
);

CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              garden_id BIGINT NOT NULL,
                              gardener_id BIGINT NOT NULL,
                              start_date DATE NOT NULL,
                              end_date DATE NOT NULL,
                              request_message TEXT,
                              status VARCHAR(20) DEFAULT 'PENDING',
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_reservation_garden FOREIGN KEY (garden_id) REFERENCES gardens(id) ON DELETE CASCADE,
                              CONSTRAINT fk_reservation_gardener FOREIGN KEY (gardener_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          publisher_id BIGINT NOT NULL,
                          title VARCHAR(100) NOT NULL,
                          description TEXT,
                          quantity_kg_or_units DOUBLE NOT NULL,
                          price DOUBLE DEFAULT 0.0,
                          product_type VARCHAR(20) NOT NULL,
                          exchange_type VARCHAR(20) NOT NULL,
                          status VARCHAR(20) DEFAULT 'AVAILABLE',
                          image_url VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_product_publisher FOREIGN KEY (publisher_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE chat_messages (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               sender_id BIGINT NOT NULL,
                               recipient_id BIGINT NOT NULL,
                               product_id BIGINT,
                               content TEXT NOT NULL,
                               is_read BOOLEAN DEFAULT FALSE,
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_chat_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_chat_recipient FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_chat_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
);