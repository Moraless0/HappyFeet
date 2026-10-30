-- =========================================================
-- DATOS DE PRUEBA PARA HAPPY FEET VETERINARIA
-- =========================================================
USE happy_feet_veterinaria;

-- =========== DUEÑOS ===========
INSERT INTO duenos (nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia, fecha_registro, activo) VALUES
('María García López', '1010234567', 'Calle 10 #20-30, Bogotá', '3101234567', 'mariagl.89@gmail.com', 'Carlos García - 3209876543', '2026-10-25 10:00:00', TRUE),
('Juan Rodríguez Pérez', '1020345678', 'Avenida 15 #30-40, Medellín', '3112345678', 'juanrodriguez_p@hotmail.com', 'Ana Pérez - 3158765432', '2026-10-26 11:30:00', TRUE),
('Carlos Martínez Torres', '1030456789', 'Carrera 5 #40-50, Cali', '3123456789', 'cmartinez.torres@gmail.com', 'Laura Torres - 3176543210', '2026-10-27 09:15:00', TRUE),
('Ana María Hernández', '1040567890', 'Calle 20 #50-60, Barranquilla', '3134567890', 'anamaria_hernandez@yahoo.com', 'Pedro Hernández - 3187654321', '2026-10-28 14:20:00', TRUE),
('Pedro Sánchez Gómez', '1050678901', 'Avenida 25 #60-70, Cartagena', '3145678901', 'psanchez.gomez@gmail.com', 'María Gómez - 3198765432', '2026-10-29 16:45:00', TRUE),
('Laura Díaz Rivera', '1060789012', 'Calle 30 #70-80, Bucaramanga', '3156789012', 'lauradiazr@hotmail.com', 'Jorge Rivera - 3209876543', '2026-10-30 08:30:00', TRUE),
('Jorge Ramírez Castro', '1070890123', 'Carrera 10 #80-90, Pereira', '3167890123', 'jramirez.castro@gmail.com', 'Carmen Castro - 3218765432', '2026-10-31 10:00:00', TRUE),
('Carmen Flores Muñoz', '1080901234', 'Avenida 35 #90-100, Santa Marta', '3178901234', 'carmen.floresm@yahoo.com', 'Roberto Muñoz - 3229876543', '2026-11-01 12:15:00', TRUE),
('Roberto Ortiz Vega', '1090012345', 'Calle 40 #100-110, Ibagué', '3189012345', 'roberto_ortizv@gmail.com', 'Diana Vega - 3238765432', '2026-11-02 09:00:00', TRUE),
('Diana Gutiérrez Ríos', '1100123456', 'Carrera 15 #110-120, Villavicencio', '3190123456', 'dianagutierrez.rios@hotmail.com', 'Miguel Ríos - 3249876543', '2026-11-03 11:30:00', TRUE),
('Miguel Álvarez Morales', '1110234567', 'Calle 50 #120-130, Neiva', '3201234567', 'malvarez.morales@gmail.com', 'Sandra Morales - 3258765432', '2026-11-03 13:00:00', TRUE),
('Sandra Torres Jiménez', '1120345678', 'Avenida 45 #130-140, Armenia', '3212345678', 'sandratorres_j@yahoo.com', 'Felipe Jiménez - 3269876543', '2026-11-03 14:30:00', TRUE);

-- =========== MASCOTAS ===========
INSERT INTO mascotas (dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, alergias, condiciones_preexistentes, fecha_registro, activo) VALUES
(1, 'Firulais', 1, '2020-05-15', 'Macho', 25.5, 'MC001', 'Polen', 'Ninguna', '2026-10-25 10:30:00', TRUE),
(1, 'Luna', 9, '2021-03-20', 'Hembra', 4.2, 'MC002', 'Pescado', 'Asma leve', '2026-10-25 10:35:00', TRUE),
(2, 'Max', 2, '2019-08-10', 'Macho', 30.0, 'MC003', NULL, 'Displasia de cadera', '2026-10-26 12:00:00', TRUE),
(2, 'Bella', 12, '2022-01-25', 'Hembra', 3.8, 'MC004', NULL, 'Ninguna', '2026-10-26 12:05:00', TRUE),
(3, 'Rocky', 3, '2018-11-30', 'Macho', 35.0, 'MC005', 'Ácaros', 'Alergia a antibióticos', '2026-10-27 09:45:00', TRUE),
(3, 'Coco', 13, '2023-02-14', 'Hembra', 5.5, 'MC006', NULL, 'Ninguna', '2026-10-27 09:50:00', TRUE),
(4, 'Simba', 1, '2021-07-20', 'Macho', 28.0, 'MC007', NULL, 'Ninguna', '2026-10-28 14:50:00', TRUE),
(4, 'Nala', 10, '2020-12-05', 'Hembra', 4.0, 'MC008', 'Ácaros', 'Dermatitis', '2026-10-28 14:55:00', TRUE),
(5, 'Thor', 4, '2019-04-10', 'Macho', 12.0, 'MC009', NULL, 'Problemas respiratorios', '2026-10-29 17:00:00', TRUE),
(5, 'Daisy', 14, '2022-06-18', 'Hembra', 3.5, 'MC010', NULL, 'Ninguna', '2026-10-29 17:05:00', TRUE),
(6, 'Charlie', 2, '2020-09-25', 'Macho', 27.5, 'MC011', 'Polvo', 'Ninguna', '2026-10-30 09:00:00', TRUE),
(6, 'Lucy', 11, '2021-11-08', 'Hembra', 4.8, 'MC012', NULL, 'Ninguna', '2026-10-30 09:05:00', TRUE),
(7, 'Buddy', 1, '2018-03-15', 'Macho', 32.0, 'MC013', NULL, 'Artritis', '2026-10-31 10:30:00', TRUE),
(7, 'Molly', 9, '2022-05-30', 'Hembra', 3.9, 'MC014', 'Polen', 'Ninguna', '2026-10-31 10:35:00', TRUE),
(8, 'Jack', 3, '2019-10-20', 'Macho', 33.5, 'MC015', NULL, 'Ninguna', '2026-11-01 12:45:00', TRUE),
(8, 'Lola', 12, '2023-01-10', 'Hembra', 4.1, 'MC016', NULL, 'Ninguna', '2026-11-01 12:50:00', TRUE),
(9, 'Oscar', 5, '2020-06-05', 'Macho', 13.5, 'MC017', NULL, 'Problemas cardiacos', '2026-11-02 09:30:00', TRUE),
(9, 'Sophie', 13, '2022-08-22', 'Hembra', 5.2, 'MC018', NULL, 'Ninguna', '2026-11-02 09:35:00', TRUE),
(10, 'Duke', 2, '2019-12-12', 'Macho', 29.0, 'MC019', 'Ácaros', 'Ninguna', '2026-11-03 12:00:00', TRUE),
(10, 'Zoe', 10, '2021-04-18', 'Hembra', 4.3, 'MC020', NULL, 'Ninguna', '2026-11-03 12:05:00', TRUE),
(11, 'Bear', 1, '2018-07-08', 'Macho', 34.0, 'MC021', NULL, 'Displasia', '2026-11-03 13:30:00', TRUE),
(11, 'Mia', 11, '2022-09-15', 'Hembra', 4.6, 'MC022', NULL, 'Ninguna', '2026-11-03 13:35:00', TRUE),
(12, 'Teddy', 4, '2020-02-28', 'Macho', 11.5, 'MC023', NULL, 'Ninguna', '2026-11-03 15:00:00', TRUE),
(12, 'Chloe', 14, '2023-03-25', 'Hembra', 3.7, 'MC024', NULL, 'Ninguna', '2026-11-03 15:05:00', TRUE),
(1, 'Rex', 3, '2017-06-10', 'Macho', 36.0, 'MC025', NULL, 'Cataratas', '2026-10-25 11:00:00', TRUE),
(2, 'Whiskers', 16, '2021-07-05', 'Macho', 0.4, 'MC026', NULL, 'Ninguna', '2026-10-26 12:30:00', TRUE);

-- =========== VETERINARIOS ===========
INSERT INTO veterinarios (nombre_completo, documento_identidad, licencia_profesional, especialidad, telefono, email, fecha_contratacion, activo) VALUES
('Dr. Carlos Andrés Mendoza', 'VET001', 'LIC-2020-001', 'Medicina General', '3001111111', 'cmendoza@happyfeet.com', '2020-01-15', TRUE),
('Dra. María Elena Rodríguez', 'VET002', 'LIC-2019-002', 'Cirugía', '3002222222', 'mrodriguez@happyfeet.com', '2019-03-20', TRUE),
('Dr. Javier Fernando Torres', 'VET003', 'LIC-2021-003', 'Dermatología', '3003333333', 'jtorres@happyfeet.com', '2021-06-10', TRUE),
('Dra. Laura Isabel García', 'VET004', 'LIC-2020-004', 'Cardiología', '3004444444', 'lgarcia@happyfeet.com', '2020-09-01', TRUE),
('Dr. Ricardo Alejandro Pérez', 'VET005', 'LIC-2022-005', 'Oftalmología', '3005555555', 'rperez@happyfeet.com', '2022-02-15', TRUE),
('Dra. Patricia María López', 'VET006', 'LIC-2018-006', 'Medicina Interna', '3006666666', 'plopez@happyfeet.com', '2018-11-20', TRUE),
('Dr. Andrés Felipe Martínez', 'VET007', 'LIC-2023-007', 'Odontología', '3007777777', 'amartinez@happyfeet.com', '2023-05-01', TRUE),
('Dra. Carolina Andrea Sánchez', 'VET008', 'LIC-2021-008', 'Neurología', '3008888888', 'csanchez@happyfeet.com', '2021-08-15', TRUE);

-- =========== PROVEEDORES ===========
INSERT INTO proveedores (nombre_empresa, contacto, telefono, email, direccion, sitio_web, activo, fecha_registro) VALUES
('FarmaVet S.A.', 'Ing. Roberto Gómez', '3011234567', 'contacto@farmavet.com', 'Calle 50 #10-20, Bogotá', 'www.farmavet.com', TRUE, '2026-10-20 10:00:00'),
('MediAnimal Ltda.', 'Dra. Claudia Ramírez', '3022345678', 'ventas@medianimal.com', 'Avenida 30 #20-30, Medellín', 'www.medianimal.com', TRUE, '2026-10-21 11:00:00'),
('VetSupply Corp.', 'Sr. Carlos Méndez', '3033456789', 'info@vetsupply.com', 'Carrera 15 #30-40, Cali', 'www.vetsupply.com', TRUE, '2026-10-22 09:00:00'),
('BioLab Colombia', 'Dra. Ana María Vega', '3044567890', 'laboratorio@biolab.com', 'Calle 80 #40-50, Barranquilla', 'www.biolab.com.co', TRUE, '2026-10-23 14:00:00'),
('PetFood Express', 'Sr. Luis Fernando Torres', '3055678901', 'pedidos@petfood.com', 'Avenida 100 #50-60, Cartagena', 'www.petfoodexpress.com', TRUE, '2026-10-24 16:00:00');

-- =========== INVENTARIO ===========
INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, precio_compra, precio_venta, requiere_receta, activo, fecha_registro) VALUES
('Amoxicilina 500mg', 1, 'Antibiótico de amplio espectro', 'FarmaVet', 1, 'LOT-2026-001', 50, 10, 'tableta', '2028-12-31', 5000.00, 8500.00, TRUE, TRUE, '2026-10-25 10:00:00'),
('Ibuprofeno 200mg', 1, 'Antiinflamatorio no esteroideo', 'MediAnimal', 2, 'LOT-2026-002', 45, 10, 'tableta', '2029-06-30', 3500.00, 6000.00, TRUE, TRUE, '2026-10-25 10:05:00'),
('Vacuna Rabia', 2, 'Vacuna antirrábica anual', 'BioLab', 4, 'LOT-2026-003', 30, 5, 'frasco', '2027-12-01', 15000.00, 25000.00, FALSE, TRUE, '2026-10-25 10:10:00'),
('Vacuna Sextuple', 2, 'Vacuna múltiple canina', 'BioLab', 4, 'LOT-2026-004', 25, 5, 'frasco', '2027-11-15', 18000.00, 30000.00, FALSE, TRUE, '2026-10-25 10:15:00'),
('Antiparasitario Oral', 1, 'Desparasitante interno', 'FarmaVet', 1, 'LOT-2026-005', 40, 8, 'tableta', '2029-03-31', 8000.00, 12000.00, TRUE, TRUE, '2026-10-25 10:20:00'),
('Vitamina A + D', 1, 'Suplemento vitamínico', 'MediAnimal', 2, 'LOT-2026-006', 60, 15, 'frasco', '2028-09-30', 12000.00, 18000.00, FALSE, TRUE, '2026-10-25 10:25:00'),
('Jeringas 5ml', 3, 'Material desechable', 'VetSupply', 3, 'LOT-2026-007', 100, 20, 'unidad', '2030-12-31', 500.00, 800.00, FALSE, TRUE, '2026-10-25 10:30:00'),
('Gasas Esterilizadas', 3, 'Material de curación', 'VetSupply', 3, 'LOT-2026-008', 80, 15, 'paquete', '2030-06-30', 2000.00, 3500.00, FALSE, TRUE, '2026-10-25 10:35:00'),
('Guantes Látex', 3, 'Material de protección', 'VetSupply', 3, 'LOT-2026-009', 150, 30, 'par', '2030-12-31', 1500.00, 2500.00, FALSE, TRUE, '2026-10-25 10:40:00'),
('Seda Hemostática', 3, 'Material de sutura', 'VetSupply', 3, 'LOT-2026-010', 20, 5, 'rollo', '2029-12-31', 25000.00, 40000.00, FALSE, TRUE, '2026-10-25 10:45:00'),
('Anestésico Local', 1, 'Lidocaína 2%', 'FarmaVet', 1, 'LOT-2026-011', 15, 3, 'frasco', '2028-08-15', 20000.00, 35000.00, TRUE, TRUE, '2026-10-25 10:50:00'),
('Antibiótico Tópico', 1, 'Pomada antibiótica', 'MediAnimal', 2, 'LOT-2026-012', 35, 7, 'tubo', '2029-01-31', 7000.00, 11000.00, FALSE, TRUE, '2026-10-25 10:55:00'),
('Suero Fisiológico', 3, 'Solución salina', 'VetSupply', 3, 'LOT-2026-013', 50, 10, 'frasco', '2028-05-30', 3000.00, 5000.00, FALSE, TRUE, '2026-10-25 11:00:00'),
('Antihistamínico', 1, 'Para alergias', 'FarmaVet', 1, 'LOT-2026-014', 3, 10, 'tableta', '2025-12-01', 4500.00, 7500.00, TRUE, TRUE, '2026-10-25 11:05:00'),
('Alimento Premium Perro', 4, 'Croquetas premium', 'PetFood', 5, 'LOT-2026-015', 25, 5, 'saco', '2027-06-30', 45000.00, 65000.00, FALSE, TRUE, '2026-10-25 11:10:00'),
('Alimento Premium Gato', 4, 'Croquetas premium felino', 'PetFood', 5, 'LOT-2026-016', 20, 5, 'saco', '2027-05-15', 40000.00, 58000.00, FALSE, TRUE, '2026-10-25 11:15:00'),
('Collar Antipulgas', 5, 'Accesorio antiparasitario', 'VetSupply', 3, 'LOT-2026-017', 40, 8, 'unidad', '2028-10-31', 25000.00, 35000.00, FALSE, TRUE, '2026-10-25 11:20:00'),
('Shampoo Antipulgas', 5, 'Shampoo medicinal', 'MediAnimal', 2, 'LOT-2026-018', 30, 6, 'frasco', '2029-02-28', 18000.00, 28000.00, FALSE, TRUE, '2026-10-25 11:25:00'),
('Vitamina C Complex', 1, 'Suplemento inmunológico', 'FarmaVet', 1, 'LOT-2026-019', 55, 12, 'frasco', '2028-11-30', 10000.00, 16000.00, FALSE, TRUE, '2026-10-25 11:30:00'),
('Analgesico Potente', 1, 'Para dolor intenso', 'MediAnimal', 2, 'LOT-2026-020', 2, 5, 'ampolla', '2025-11-15', 25000.00, 40000.00, TRUE, TRUE, '2026-10-25 11:35:00');

-- =========== SERVICIOS ===========
INSERT INTO servicios (nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo) VALUES
('Consulta General', 'Consulta veterinaria de rutina', 'Consulta', 25000.00, 30, TRUE),
('Consulta Especializada', 'Consulta con especialista', 'Consulta', 45000.00, 45, TRUE),
('Vacunación', 'Aplicación de vacuna', 'Procedimiento', 15000.00, 15, TRUE),
('Desparasitación', 'Tratamiento antiparasitario', 'Procedimiento', 12000.00, 20, TRUE),
('Limpieza Dental', 'Profilaxis dental completa', 'Procedimiento', 35000.00, 40, TRUE),
('Cirugía Menor', 'Procedimientos quirúrgicos simples', 'Cirugía', 80000.00, 60, TRUE),
('Cirugía Mayor', 'Procedimientos quirúrgicos complejos', 'Cirugía', 200000.00, 180, TRUE),
('Radiografía', 'Estudio de imagen', 'Diagnóstico', 30000.00, 30, TRUE),
('Laboratorio General', 'Análisis de sangre y orina', 'Diagnóstico', 40000.00, 60, TRUE),
('Ecografía', 'Estudio por ultrasonido', 'Diagnóstico', 50000.00, 45, TRUE),
('Hospitalización', 'Internamiento diario', 'Hospitalización', 45000.00, 1440, TRUE),
('Corte de Uñas', 'Manicura canina/felina', 'Estética', 8000.00, 15, TRUE),
('Baño y Corte', 'Servicio completo de estética', 'Estética', 25000.00, 60, TRUE),
('Castración', 'Procedimiento de esterilización', 'Cirugía', 120000.00, 90, TRUE),
('Emergencia', 'Atención de urgencia', 'Emergencia', 60000.00, 60, TRUE);

-- =========== CITAS ===========
INSERT INTO citas (mascota_id, veterinario_id, fecha_hora, motivo, estado_id, observaciones, fecha_creacion) VALUES
(1, 1, '2026-11-03 09:00:00', 'Revisión general', 1, 'Mascota activa y saludable', '2026-10-30 14:00:00'),
(2, 2, '2026-11-03 10:30:00', 'Chequeo post-cirugía', 1, 'Seguimiento de cirugía', '2026-10-30 15:00:00'),
(3, 1, '2026-11-03 14:00:00', 'Vacunación anual', 1, 'Aplicar vacuna sextuple', '2026-10-31 10:00:00'),
(4, 3, '2026-11-04 09:00:00', 'Problema dermatológico', 1, 'Picazón persistente', '2026-11-01 11:00:00'),
(5, 2, '2026-11-04 11:00:00', 'Dificultad respiratoria', 1, 'Tos frecuente', '2026-11-01 12:00:00'),
(6, 1, '2026-11-05 10:00:00', 'Control de peso', 1, 'Mascota con sobrepeso', '2026-11-02 09:00:00'),
(7, 4, '2026-11-05 14:00:00', 'Chequeo cardíaco', 1, 'Historial de problemas cardiacos', '2026-11-02 10:00:00'),
(8, 3, '2026-11-06 09:00:00', 'Revisión general', 1, 'Chequeo rutinario', '2026-11-02 11:00:00'),
(9, 5, '2026-11-06 11:00:00', 'Examen ocular', 1, 'Cataratas', '2026-11-02 12:00:00'),
(10, 2, '2026-11-07 10:00:00', 'Consulta general', 1, 'Primera consulta', '2026-11-03 09:00:00'),
(11, 1, '2026-11-07 14:00:00', 'Desparasitación', 1, 'Tratamiento preventivo', '2026-11-03 10:00:00'),
(12, 6, '2026-11-08 09:00:00', 'Vacunación', 1, 'Vacuna antirrábica', '2026-11-03 11:00:00'),
(13, 3, '2026-11-08 11:00:00', 'Problema de piel', 1, 'Dermatitis', '2026-11-03 12:00:00'),
(14, 4, '2026-11-09 10:00:00', 'Control cardíaco', 1, 'Seguimiento', '2026-11-03 13:00:00'),
(15, 1, '2026-11-09 14:00:00', 'Limpieza dental', 1, 'Tartar acumulado', '2026-11-03 14:00:00');

-- =========== CONSULTAS MÉDICAS ===========
INSERT INTO consultas_medicas (mascota_id, veterinario_id, cita_id, fecha_hora, motivo, sintomas, diagnostico, recomendaciones, peso_registrado, temperatura) VALUES
(1, 1, 1, '2026-11-03 09:30:00', 'Revisión general', 'Ninguno', 'Mascota saludable', 'Continuar con alimentación actual', 25.5, 38.5),
(2, 2, 2, '2026-11-03 11:00:00', 'Chequeo post-cirugía', 'Herida en proceso de cicatrización', 'Cicatrización normal', 'Continuar antibióticos', 30.0, 38.3),
(3, 1, 3, '2026-11-03 14:30:00', 'Vacunación anual', 'Ninguno', 'Sin patologías', 'Aplicar vacuna sextuple', 35.0, 38.4),
(4, 3, NULL, '2026-11-02 15:00:00', 'Problema dermatológico', 'Picazón intensa en piel', 'Dermatitis alérgica', 'Cambiar dieta, aplicar tratamiento tópico', 28.0, 38.6),
(5, 2, NULL, '2026-11-02 16:30:00', 'Dificultad respiratoria', 'Tos y jadeo', 'Bronquitis leve', 'Antibiótico y reposo', 12.0, 39.2);

-- =========== CLUB DE MASCOTAS ===========
INSERT INTO club_mascotas (dueno_id, puntos_acumulados, puntos_canjeados, puntos_disponibles, nivel, fecha_inscripcion, activo) VALUES
(1, 500, 100, 400, 'Plata', '2026-10-25', TRUE),
(2, 1200, 300, 900, 'Oro', '2026-10-26', TRUE),
(3, 800, 150, 650, 'Plata', '2026-10-27', TRUE),
(4, 300, 50, 250, 'Bronce', '2026-10-28', TRUE),
(5, 1500, 400, 1100, 'Platino', '2026-10-29', TRUE),
(6, 600, 100, 500, 'Plata', '2026-10-30', TRUE),
(7, 2000, 500, 1500, 'Platino', '2026-10-31', TRUE),
(8, 100, 0, 100, 'Bronce', '2026-11-01', TRUE),
(9, 900, 200, 700, 'Oro', '2026-11-02', TRUE),
(10, 400, 80, 320, 'Bronce', '2026-11-03', TRUE);

-- =========== BENEFICIOS CLUB ===========
INSERT INTO beneficios_club (nombre, descripcion, nivel_requerido, puntos_necesarios, tipo_beneficio, valor_beneficio, activo) VALUES
('Descuento 5%', 'Descuento en servicios básicos', 'Bronce', 100, 'Descuento', 5.00, TRUE),
('Descuento 10%', 'Descuento en servicios médicos', 'Plata', 300, 'Descuento', 10.00, TRUE),
('Descuento 15%', 'Descuento en cirugías', 'Oro', 500, 'Descuento', 15.00, TRUE),
('Descuento 20%', 'Descuento en todos los servicios', 'Platino', 1000, 'Descuento', 20.00, TRUE),
('Consulta Gratis', 'Consulta general gratuita', 'Oro', 600, 'Servicio Gratis', 25000.00, TRUE),
('Baño Gratis', 'Servicio de baño gratuito', 'Plata', 400, 'Servicio Gratis', 25000.00, TRUE),
('Vitamina Gratis', 'Suplemento vitamínico gratis', 'Bronce', 150, 'Producto Gratis', 16000.00, TRUE),
('Puntos Extra', 'Doble de puntos en compras', 'Platino', 50, 'Puntos Extra', 0.00, TRUE);
