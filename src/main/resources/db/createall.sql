-- =========================
-- TABLAS BASE
-- =========================

DROP TABLE IF EXISTS Usuario CASCADE;
CREATE TABLE Usuario (
    id_usuario      SERIAL PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    correo          VARCHAR(150) UNIQUE NOT NULL,
    rol             VARCHAR(50) CHECK (rol IN ('admin','colaborador','instructor')),
    fecha_registro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS Capitulo CASCADE;
CREATE TABLE Capitulo (
    id_capitulo     SERIAL PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    contenido_url   TEXT,
    orden           INT NOT NULL CHECK (orden > 0),
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS Curso CASCADE;
CREATE TABLE Curso (
    id_curso        SERIAL PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    descripcion     TEXT,
    categoria       VARCHAR(100),
    nivel           VARCHAR(50),
    estado          VARCHAR(20) DEFAULT 'activo',
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS Contenido CASCADE;
CREATE TABLE Contenido (
    id_contenido    SERIAL PRIMARY KEY,
    id_curso        INT NOT NULL REFERENCES Curso(id_curso) ON DELETE CASCADE,
    nombre_archivo  VARCHAR(255) NOT NULL,
    tipo_extension  VARCHAR(10) NOT NULL,
    tamaño          BIGINT NOT NULL CHECK (tamaño > 0),
    datos           BYTEA NOT NULL,
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla intermedia para relación Curso ↔ Capítulo
DROP TABLE IF EXISTS CursoCapitulo CASCADE;
CREATE TABLE CursoCapitulo (
    id_curso        INT NOT NULL REFERENCES Curso(id_curso) ON DELETE CASCADE,
    id_capitulo     INT NOT NULL REFERENCES Capitulo(id_capitulo) ON DELETE CASCADE,
    PRIMARY KEY (id_curso, id_capitulo)
);


-- =========================
-- PROGRESO
-- =========================

DROP TABLE IF EXISTS ProgresoUsuarioCurso CASCADE;
CREATE TABLE ProgresoUsuarioCurso (
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    id_curso        INT NOT NULL REFERENCES Curso(id_curso) ON DELETE CASCADE,
    estado          VARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'en_curso', 'completado')),
    porcentaje      DECIMAL(5,2) DEFAULT 0 CHECK (porcentaje >= 0 AND porcentaje <= 100),
    fecha_inicio    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin       TIMESTAMP,
    PRIMARY KEY (id_usuario, id_curso),
    CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
);

DROP TABLE IF EXISTS ProgresoUsuarioCapitulo CASCADE;
CREATE TABLE ProgresoUsuarioCapitulo (
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    id_capitulo     INT NOT NULL REFERENCES Capitulo(id_capitulo) ON DELETE CASCADE,
    estado          VARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'en_curso', 'completado')),
    fecha_inicio    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin       TIMESTAMP,
    PRIMARY KEY (id_usuario, id_capitulo),
    CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
);


-- =========================
-- INSIGNIAS
-- =========================

DROP TABLE IF EXISTS Insignia CASCADE;
CREATE TABLE Insignia (
    id_insignia     SERIAL PRIMARY KEY,
    nombre          VARCHAR(150) NOT NULL,
    descripcion     TEXT,
    imagen_url      TEXT,
    criterio        TEXT
);

DROP TABLE IF EXISTS UsuarioInsignia CASCADE;
CREATE TABLE UsuarioInsignia (
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    id_insignia     INT NOT NULL REFERENCES Insignia(id_insignia) ON DELETE CASCADE,
    fecha_otorgada  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_insignia)
);


-- =========================
-- NOTIFICACIONES
-- =========================

DROP TABLE IF EXISTS Notificacion CASCADE;
CREATE TABLE Notificacion (
    id_notificacion SERIAL PRIMARY KEY,
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    tipo            VARCHAR(50),
    mensaje         TEXT NOT NULL,
    leida           BOOLEAN DEFAULT FALSE,
    fecha_envio     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- =========================
-- HISTORICO
-- =========================

DROP TABLE IF EXISTS HistoricoActividad CASCADE;
CREATE TABLE HistoricoActividad (
    id_historial    SERIAL PRIMARY KEY,
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    accion          VARCHAR(200) NOT NULL,
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- CONSTRAINTS ADICIONALES
-- =========================

-- Validar que el porcentaje esté entre 0 y 100
ALTER TABLE ProgresoUsuarioCurso 
ADD CONSTRAINT chk_porcentaje_valido 
CHECK (porcentaje >= 0 AND porcentaje <= 100);

-- Validar que la fecha_fin sea posterior a fecha_inicio
ALTER TABLE ProgresoUsuarioCurso 
ADD CONSTRAINT chk_fechas_validas_curso 
CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio);

ALTER TABLE ProgresoUsuarioCapitulo 
ADD CONSTRAINT chk_fechas_validas_capitulo 
CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio);

-- Validar estados válidos
ALTER TABLE ProgresoUsuarioCurso 
ADD CONSTRAINT chk_estado_valido_curso 
CHECK (estado IN ('pendiente', 'en_curso', 'completado'));

ALTER TABLE ProgresoUsuarioCapitulo 
ADD CONSTRAINT chk_estado_valido_capitulo 
CHECK (estado IN ('pendiente', 'en_curso', 'completado'));

-- Validar que el orden de capítulo sea positivo
ALTER TABLE Capitulo 
ADD CONSTRAINT chk_orden_positivo 
CHECK (orden > 0);
