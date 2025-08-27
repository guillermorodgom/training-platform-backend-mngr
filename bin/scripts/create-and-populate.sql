-- =============================================================================
-- Training Platform - Crear tablas y poblar con datos
-- =============================================================================

SET client_min_messages = WARNING;

-- Crear schema si no existe
CREATE SCHEMA IF NOT EXISTS training;
SET search_path = training;

-- =============================================================================
-- 1. CREAR TABLAS
-- =============================================================================

-- Tabla de cursos
CREATE TABLE IF NOT EXISTS curso (
    id_curso SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(100),
    nivel VARCHAR(50),
    estado VARCHAR(20),
    fecha_creacion TIMESTAMP DEFAULT NOW()
);

-- Tabla de capítulos
CREATE TABLE IF NOT EXISTS capitulo (
    id_capitulo SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    contenido_url TEXT,
    orden INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT NOW()
);

-- Tabla de relación curso-capítulo
CREATE TABLE IF NOT EXISTS curso_capitulo (
    id_curso INTEGER NOT NULL,
    id_capitulo INTEGER NOT NULL,
    PRIMARY KEY (id_curso, id_capitulo),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso),
    FOREIGN KEY (id_capitulo) REFERENCES capitulo(id_capitulo)
);

-- Tabla de badges
CREATE TABLE IF NOT EXISTS badge (
    id_badge BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(50),
    color VARCHAR(20),
    category VARCHAR(50) NOT NULL CHECK (category IN ('COMPLETION','EXCELLENCE','SPEED','CONSISTENCY','MILESTONE')),
    rarity VARCHAR(50) NOT NULL CHECK (rarity IN ('COMMON','RARE','EPIC','LEGENDARY')),
    requirements TEXT,
    points INTEGER NOT NULL DEFAULT 0,
    course_id INTEGER,
    fecha_creacion TIMESTAMP DEFAULT NOW()
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN','INSTRUCTOR','STUDENT')),
    department VARCHAR(100),
    profile_picture VARCHAR(255),
    join_date TIMESTAMP DEFAULT NOW(),
    last_active TIMESTAMP DEFAULT NOW(),
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE
);

-- =============================================================================
-- 2. INSERTAR CURSOS
-- =============================================================================

INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
-- 🖥 Fullstack Development
('Desarrollo Fullstack con Spring Boot y Angular', 'Curso completo para desarrollar aplicaciones web modernas usando Spring Boot como backend y Angular como frontend', 'Fullstack', 'Intermedio', 'activo', NOW()),
('Arquitectura Fullstack Moderna', 'Aprende a diseñar y desarrollar aplicaciones fullstack escalables con mejores prácticas', 'Fullstack', 'Avanzado', 'activo', NOW()),
('Fullstack con React y Node.js', 'Desarrollo completo de aplicaciones web usando el stack MERN (MongoDB, Express, React, Node.js)', 'Fullstack', 'Intermedio', 'activo', NOW()),

-- 🔧 Backend Development  
('Spring Boot Avanzado', 'Domina Spring Boot con microservicios, seguridad JWT, testing y deployment', 'Backend', 'Avanzado', 'activo', NOW()),
('Node.js y Express Profesional', 'Desarrollo backend robusto con Node.js, Express, bases de datos y APIs RESTful', 'Backend', 'Intermedio', 'activo', NOW()),
('Microservicios con Java', 'Arquitectura de microservicios usando Spring Cloud, Docker y Kubernetes', 'Backend', 'Avanzado', 'activo', NOW()),

-- 🎨 Frontend Development
('Angular Profesional', 'Desarrollo de aplicaciones Angular modernas con TypeScript, RxJS y mejores prácticas', 'Frontend', 'Intermedio', 'activo', NOW()),
('React Avanzado', 'Hooks, Context, Redux, Next.js y patrones avanzados de React para aplicaciones escalables', 'Frontend', 'Avanzado', 'activo', NOW()),
('Vue.js Completo', 'Desde conceptos básicos hasta aplicaciones complejas con Vue 3, Composition API y Nuxt', 'Frontend', 'Intermedio', 'activo', NOW()),

-- 🔗 APIs e Integraciones
('IBM DataPower Gateway', 'Configuración y administración de IBM DataPower para servicios web empresariales', 'APIs e Integraciones', 'Avanzado', 'activo', NOW()),
('IBM Integration Bus (IIB)', 'Desarrollo de flujos de integración con IBM Integration Bus y WebSphere Message Broker', 'APIs e Integraciones', 'Avanzado', 'activo', NOW()),
('Diseño de APIs RESTful', 'Mejores prácticas para diseñar, documentar y versionar APIs REST profesionales', 'APIs e Integraciones', 'Intermedio', 'activo', NOW()),
('Microservicios y Patrones de Integración', 'Patrones de comunicación, circuit breakers, y estrategias de integración entre servicios', 'APIs e Integraciones', 'Avanzado', 'activo', NOW()),

-- ☁ Cloud Computing
('AWS para Desarrolladores', 'Servicios esenciales de AWS: EC2, S3, Lambda, RDS, y deployment de aplicaciones', 'Cloud', 'Intermedio', 'activo', NOW()),
('Azure DevOps y CI/CD', 'Implementación de pipelines de integración y deployment continuo en Microsoft Azure', 'Cloud', 'Avanzado', 'activo', NOW()),
('Google Cloud Platform', 'Desarrollo y deployment de aplicaciones en GCP con Kubernetes, Cloud Functions y BigQuery', 'Cloud', 'Intermedio', 'activo', NOW()),
('Docker y Kubernetes', 'Containerización y orquestación de aplicaciones con Docker y Kubernetes', 'Cloud', 'Avanzado', 'activo', NOW()),

-- 📊 Data Engineering
('Apache Spark y Big Data', 'Procesamiento de grandes volúmenes de datos con Apache Spark, Scala y Python', 'Data Engineer', 'Avanzado', 'activo', NOW()),
('ETL con Python y Airflow', 'Pipelines de datos robustos usando Python, Pandas, Apache Airflow y bases de datos', 'Data Engineer', 'Intermedio', 'activo', NOW()),
('Data Warehousing Moderno', 'Diseño e implementación de data warehouses con Snowflake, dbt y herramientas modernas', 'Data Engineer', 'Avanzado', 'activo', NOW()),
('Streaming de Datos en Tiempo Real', 'Apache Kafka, Stream processing y arquitecturas de datos en tiempo real', 'Data Engineer', 'Avanzado', 'activo', NOW());

-- =============================================================================
-- 3. INSERTAR CAPÍTULOS
-- =============================================================================

-- Fullstack con Spring Boot y Angular (ID: 1)
INSERT INTO capitulo (titulo, contenido_url, orden, fecha_creacion) VALUES
('Introducción al Desarrollo Fullstack', '/content/fullstack/intro', 1, NOW()),
('Configuración del Entorno de Desarrollo', '/content/fullstack/setup', 2, NOW()),
('Spring Boot: APIs REST', '/content/fullstack/spring-apis', 3, NOW()),
('Angular: Componentes y Servicios', '/content/fullstack/angular-basics', 4, NOW()),
('Integración Frontend-Backend', '/content/fullstack/integration', 5, NOW()),
('Autenticación JWT', '/content/fullstack/jwt-auth', 6, NOW()),
('Testing End-to-End', '/content/fullstack/e2e-testing', 7, NOW()),
('Deployment y Producción', '/content/fullstack/deployment', 8, NOW()),

-- IBM DataPower Gateway (ID: 10)
('Introducción a IBM DataPower', '/content/datapower/intro', 1, NOW()),
('Instalación y Configuración Inicial', '/content/datapower/setup', 2, NOW()),
('Web Service Proxy', '/content/datapower/ws-proxy', 3, NOW()),
('Multi-Protocol Gateway', '/content/datapower/mpg', 4, NOW()),
('Políticas de Seguridad', '/content/datapower/security', 5, NOW()),
('Transformaciones XSLT', '/content/datapower/xslt', 6, NOW()),
('Monitoreo y Troubleshooting', '/content/datapower/monitoring', 7, NOW()),
('Best Practices y Optimización', '/content/datapower/best-practices', 8, NOW()),

-- AWS para Desarrolladores (ID: 14)
('Fundamentos de AWS', '/content/aws/fundamentals', 1, NOW()),
('EC2: Instancias y Configuración', '/content/aws/ec2', 2, NOW()),
('S3: Almacenamiento en la Nube', '/content/aws/s3', 3, NOW()),
('RDS: Bases de Datos Relacionales', '/content/aws/rds', 4, NOW()),
('Lambda: Funciones Serverless', '/content/aws/lambda', 5, NOW()),
('API Gateway', '/content/aws/api-gateway', 6, NOW()),
('CloudFormation: Infraestructura como Código', '/content/aws/cloudformation', 7, NOW()),
('Monitoreo con CloudWatch', '/content/aws/cloudwatch', 8, NOW()),

-- Apache Spark y Big Data (ID: 18)
('Introducción a Big Data y Spark', '/content/spark/intro', 1, NOW()),
('Instalación y Configuración de Spark', '/content/spark/setup', 2, NOW()),
('RDDs: Conceptos Fundamentales', '/content/spark/rdds', 3, NOW()),
('DataFrames y Datasets', '/content/spark/dataframes', 4, NOW()),
('Spark SQL', '/content/spark/sql', 5, NOW()),
('Machine Learning con MLlib', '/content/spark/mllib', 6, NOW()),
('Streaming con Spark', '/content/spark/streaming', 7, NOW()),
('Optimización y Performance Tuning', '/content/spark/optimization', 8, NOW());

-- =============================================================================
-- 4. RELACIONES CURSO-CAPITULO
-- =============================================================================

-- Fullstack con Spring Boot y Angular
INSERT INTO curso_capitulo (id_curso, id_capitulo) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8);

-- IBM DataPower Gateway  
INSERT INTO curso_capitulo (id_curso, id_capitulo) VALUES
(10, 9), (10, 10), (10, 11), (10, 12), (10, 13), (10, 14), (10, 15), (10, 16);

-- AWS para Desarrolladores
INSERT INTO curso_capitulo (id_curso, id_capitulo) VALUES
(14, 17), (14, 18), (14, 19), (14, 20), (14, 21), (14, 22), (14, 23), (14, 24);

-- Apache Spark y Big Data
INSERT INTO curso_capitulo (id_curso, id_capitulo) VALUES
(18, 25), (18, 26), (18, 27), (18, 28), (18, 29), (18, 30), (18, 31), (18, 32);

-- =============================================================================
-- 5. INSERTAR BADGES
-- =============================================================================

INSERT INTO badge (name, description, icon, color, category, rarity, requirements, points, fecha_creacion) VALUES
-- Insignias de Finalización
('Fullstack Master', 'Completa todos los cursos de Fullstack Development', '🖥️', '#4F46E5', 'COMPLETION', 'EPIC', 'Completar 3 cursos de Fullstack', 500, NOW()),
('API Integration Expert', 'Domina las integraciones y APIs empresariales', '🔗', '#059669', 'COMPLETION', 'RARE', 'Completar 2 cursos de APIs e Integraciones', 300, NOW()),
('Cloud Architect', 'Experto en tecnologías de nube', '☁️', '#0EA5E9', 'COMPLETION', 'EPIC', 'Completar 3 cursos de Cloud', 500, NOW()),
('Data Engineering Pro', 'Especialista en ingeniería de datos', '📊', '#DC2626', 'COMPLETION', 'LEGENDARY', 'Completar todos los cursos de Data Engineering', 1000, NOW()),

-- Insignias de Excelencia
('Perfect Score', 'Obtén 100% en cualquier curso', '⭐', '#F59E0B', 'EXCELLENCE', 'RARE', 'Puntuación perfecta en un curso', 200, NOW()),
('Quick Learner', 'Completa un curso en menos de una semana', '⚡', '#8B5CF6', 'SPEED', 'COMMON', 'Finalizar curso en 7 días o menos', 100, NOW()),
('Consistent Learner', 'Estudia 7 días consecutivos', '📚', '#10B981', 'CONSISTENCY', 'COMMON', 'Actividad diaria por 7 días', 150, NOW()),
('Milestone Achiever', 'Alcanza 50% de progreso en 5 cursos', '🎯', '#F97316', 'MILESTONE', 'RARE', 'Progreso del 50% en 5 cursos diferentes', 250, NOW());

-- =============================================================================
-- 6. INSERTAR USUARIOS DE PRUEBA
-- =============================================================================

INSERT INTO usuario (email, password, first_name, last_name, role, department, join_date, last_active, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES
('admin@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Admin', 'User', 'ADMIN', 'IT', NOW(), NOW(), true, true, true, true),
('student@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Student', 'Demo', 'STUDENT', 'Development', NOW(), NOW(), true, true, true, true),
('instructor@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Instructor', 'Demo', 'INSTRUCTOR', 'Education', NOW(), NOW(), true, true, true, true);

-- Password para todos los usuarios: "password123"

-- =============================================================================
-- 7. VERIFICACIÓN
-- =============================================================================

-- Mostrar resumen de datos insertados
DO $$
BEGIN
    RAISE NOTICE '';
    RAISE NOTICE '=== RESUMEN DE DATOS INSERTADOS ===';
    RAISE NOTICE 'Cursos: % registros', (SELECT COUNT(*) FROM curso);
    RAISE NOTICE 'Capítulos: % registros', (SELECT COUNT(*) FROM capitulo);
    RAISE NOTICE 'Relaciones Curso-Capítulo: % registros', (SELECT COUNT(*) FROM curso_capitulo);
    RAISE NOTICE 'Badges: % registros', (SELECT COUNT(*) FROM badge);
    RAISE NOTICE 'Usuarios: % registros', (SELECT COUNT(*) FROM usuario);
    RAISE NOTICE '';
    RAISE NOTICE '✅ DATOS INSERTADOS EXITOSAMENTE';
    RAISE NOTICE '';
END $$;

-- Consultas de verificación (comentadas para no mostrar en output normal)
/*
SELECT 'CURSOS POR CATEGORÍA' as info;
SELECT categoria, COUNT(*) as num_cursos FROM curso GROUP BY categoria ORDER BY categoria;

SELECT 'PRIMEROS 5 CURSOS' as info;
SELECT id_curso, titulo, categoria, nivel FROM curso ORDER BY id_curso LIMIT 5;

SELECT 'CAPÍTULOS POR CURSO (muestra)' as info;
SELECT c.titulo as curso, COUNT(cc.id_capitulo) as num_capitulos 
FROM curso c 
LEFT JOIN curso_capitulo cc ON c.id_curso = cc.id_curso 
GROUP BY c.titulo 
ORDER BY c.titulo 
LIMIT 5;
*/

COMMIT;