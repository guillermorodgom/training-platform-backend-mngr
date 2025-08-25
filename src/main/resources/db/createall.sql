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

DROP TABLE IF EXISTS Curso CASCADE;
CREATE TABLE Curso (
    id_curso        SERIAL PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    descripcion     TEXT,
    categoria       VARCHAR(100), -- Fullstack, Cloud, APIs, Data Engineer, etc.
    nivel           VARCHAR(50),  -- basico, intermedio, avanzado
    estado          VARCHAR(20) DEFAULT 'activo',
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS Capitulo CASCADE;
CREATE TABLE Capitulo (
    id_capitulo     SERIAL PRIMARY KEY,
    id_curso        INT NOT NULL REFERENCES Curso(id_curso) ON DELETE CASCADE,
    titulo          VARCHAR(200) NOT NULL,
    contenido_url   TEXT, -- pdf, video, guia
    orden           INT NOT NULL,
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- PROGRESO
-- =========================

DROP TABLE IF EXISTS ProgresoUsuarioCurso CASCADE;
CREATE TABLE ProgresoUsuarioCurso (
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    id_curso        INT NOT NULL REFERENCES Curso(id_curso) ON DELETE CASCADE,
    estado          VARCHAR(20) DEFAULT 'pendiente', -- pendiente, en_curso, completado
    porcentaje      DECIMAL(5,2) DEFAULT 0, -- avance en porcentaje
    fecha_inicio    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin       TIMESTAMP,
    PRIMARY KEY (id_usuario, id_curso)
);

DROP TABLE IF EXISTS ProgresoUsuarioCapitulo CASCADE;
CREATE TABLE ProgresoUsuarioCapitulo (
    id_usuario      INT NOT NULL REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    id_capitulo     INT NOT NULL REFERENCES Capitulo(id_capitulo) ON DELETE CASCADE,
    estado          VARCHAR(20) DEFAULT 'pendiente', -- pendiente, en_curso, completado
    fecha_inicio    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin       TIMESTAMP,
    PRIMARY KEY (id_usuario, id_capitulo)
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
    criterio        TEXT -- regla para otorgamiento (ej: completar curso X)
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
    tipo            VARCHAR(50), -- nuevo curso, curso completado, insignia
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
    accion          VARCHAR(200) NOT NULL, -- curso inscrito, curso completado, etc.
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
