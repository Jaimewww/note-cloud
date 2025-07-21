-- Insertar roles por defecto si no existen (MySQL)
INSERT IGNORE INTO roles (ID, NAME) VALUES (1, 'USER');
INSERT IGNORE INTO roles (ID, NAME) VALUES (2, 'ADMIN');