-- Training Platform Sample Data
-- Script para crear cursos y capítulos iniciales

-- ============================================================================
-- 1. CURSOS PRINCIPALES
-- ============================================================================

-- 🖥 Fullstack Development
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('Desarrollo Fullstack con Spring Boot y Angular', 'Curso completo para desarrollar aplicaciones web modernas usando Spring Boot como backend y Angular como frontend', 'Fullstack', 'Intermedio', 'activo', NOW()),
('Arquitectura Fullstack Moderna', 'Aprende a diseñar y desarrollar aplicaciones fullstack escalables con mejores prácticas', 'Fullstack', 'Avanzado', 'activo', NOW()),
('Fullstack con React y Node.js', 'Desarrollo completo de aplicaciones web usando el stack MERN (MongoDB, Express, React, Node.js)', 'Fullstack', 'Intermedio', 'activo', NOW());

-- 🔧 Backend Development  
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('Spring Boot Avanzado', 'Domina Spring Boot con microservicios, seguridad JWT, testing y deployment', 'Backend', 'Avanzado', 'activo', NOW()),
('Node.js y Express Profesional', 'Desarrollo backend robusto con Node.js, Express, bases de datos y APIs RESTful', 'Backend', 'Intermedio', 'activo', NOW()),
('Microservicios con Java', 'Arquitectura de microservicios usando Spring Cloud, Docker y Kubernetes', 'Backend', 'Avanzado', 'activo', NOW());

-- 🎨 Frontend Development
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('Angular Profesional', 'Desarrollo de aplicaciones Angular modernas con TypeScript, RxJS y mejores prácticas', 'Frontend', 'Intermedio', 'activo', NOW()),
('React Avanzado', 'Hooks, Context, Redux, Next.js y patrones avanzados de React para aplicaciones escalables', 'Frontend', 'Avanzado', 'activo', NOW()),
('Vue.js Completo', 'Desde conceptos básicos hasta aplicaciones complejas con Vue 3, Composition API y Nuxt', 'Frontend', 'Intermedio', 'activo', NOW());

-- 🔗 APIs e Integraciones
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('IBM DataPower Gateway', 'Configuración y administración de IBM DataPower para servicios web empresariales', 'APIs e Integraciones', 'Avanzado', 'activo', NOW()),
('IBM Integration Bus (IIB)', 'Desarrollo de flujos de integración con IBM Integration Bus y WebSphere Message Broker', 'APIs e Integraciones', 'Avanzado', 'activo', NOW()),
('Diseño de APIs RESTful', 'Mejores prácticas para diseñar, documentar y versionar APIs REST profesionales', 'APIs e Integraciones', 'Intermedio', 'activo', NOW()),
('Microservicios y Patrones de Integración', 'Patrones de comunicación, circuit breakers, y estrategias de integración entre servicios', 'APIs e Integraciones', 'Avanzado', 'activo', NOW());

-- ☁ Cloud Computing
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('AWS para Desarrolladores', 'Servicios esenciales de AWS: EC2, S3, Lambda, RDS, y deployment de aplicaciones', 'Cloud', 'Intermedio', 'activo', NOW()),
('Azure DevOps y CI/CD', 'Implementación de pipelines de integración y deployment continuo en Microsoft Azure', 'Cloud', 'Avanzado', 'activo', NOW()),
('Google Cloud Platform', 'Desarrollo y deployment de aplicaciones en GCP con Kubernetes, Cloud Functions y BigQuery', 'Cloud', 'Intermedio', 'activo', NOW()),
('Docker y Kubernetes', 'Containerización y orquestación de aplicaciones con Docker y Kubernetes', 'Cloud', 'Avanzado', 'activo', NOW());

-- 📊 Data Engineering
INSERT INTO curso (titulo, descripcion, categoria, nivel, estado, fecha_creacion) VALUES
('Apache Spark y Big Data', 'Procesamiento de grandes volúmenes de datos con Apache Spark, Scala y Python', 'Data Engineer', 'Avanzado', 'activo', NOW()),
('ETL con Python y Airflow', 'Pipelines de datos robustos usando Python, Pandas, Apache Airflow y bases de datos', 'Data Engineer', 'Intermedio', 'activo', NOW()),
('Data Warehousing Moderno', 'Diseño e implementación de data warehouses con Snowflake, dbt y herramientas modernas', 'Data Engineer', 'Avanzado', 'activo', NOW()),
('Streaming de Datos en Tiempo Real', 'Apache Kafka, Stream processing y arquitecturas de datos en tiempo real', 'Data Engineer', 'Avanzado', 'activo', NOW());

-- ============================================================================
-- 2. CAPÍTULOS POR CURSO
-- ============================================================================

-- Fullstack con Spring Boot y Angular (ID: 1)
INSERT INTO capitulo (titulo, contenido_url, orden, fecha_creacion) VALUES
('Introducción al Desarrollo Fullstack', '/content/fullstack/intro', 1, NOW()),
('Configuración del Entorno de Desarrollo', '/content/fullstack/setup', 2, NOW()),
('Spring Boot: APIs REST', '/content/fullstack/spring-apis', 3, NOW()),
('Angular: Componentes y Servicios', '/content/fullstack/angular-basics', 4, NOW()),
('Integración Frontend-Backend', '/content/fullstack/integration', 5, NOW()),
('Autenticación JWT', '/content/fullstack/jwt-auth', 6, NOW()),
('Testing End-to-End', '/content/fullstack/e2e-testing', 7, NOW()),
('Deployment y Producción', '/content/fullstack/deployment', 8, NOW());

-- APIs e Integraciones - IBM DataPower (ID: 10)
INSERT INTO capitulo (titulo, contenido_url, orden, fecha_creacion) VALUES
('Introducción a IBM DataPower', '/content/datapower/intro', 1, NOW()),
('Instalación y Configuración Inicial', '/content/datapower/setup', 2, NOW()),
('Web Service Proxy', '/content/datapower/ws-proxy', 3, NOW()),
('Multi-Protocol Gateway', '/content/datapower/mpg', 4, NOW()),
('Políticas de Seguridad', '/content/datapower/security', 5, NOW()),
('Transformaciones XSLT', '/content/datapower/xslt', 6, NOW()),
('Monitoreo y Troubleshooting', '/content/datapower/monitoring', 7, NOW()),
('Best Practices y Optimización', '/content/datapower/best-practices', 8, NOW());

-- Cloud - AWS para Desarrolladores (ID: 14)
INSERT INTO capitulo (titulo, contenido_url, orden, fecha_creacion) VALUES
('Fundamentos de AWS', '/content/aws/fundamentals', 1, NOW()),
('EC2: Instancias y Configuración', '/content/aws/ec2', 2, NOW()),
('S3: Almacenamiento en la Nube', '/content/aws/s3', 3, NOW()),
('RDS: Bases de Datos Relacionales', '/content/aws/rds', 4, NOW()),
('Lambda: Funciones Serverless', '/content/aws/lambda', 5, NOW()),
('API Gateway', '/content/aws/api-gateway', 6, NOW()),
('CloudFormation: Infraestructura como Código', '/content/aws/cloudformation', 7, NOW()),
('Monitoreo con CloudWatch', '/content/aws/cloudwatch', 8, NOW());

-- Data Engineering - Apache Spark (ID: 18)
INSERT INTO capitulo (titulo, contenido_url, orden, fecha_creacion) VALUES
('Introducción a Big Data y Spark', '/content/spark/intro', 1, NOW()),
('Instalación y Configuración de Spark', '/content/spark/setup', 2, NOW()),
('RDDs: Conceptos Fundamentales', '/content/spark/rdds', 3, NOW()),
('DataFrames y Datasets', '/content/spark/dataframes', 4, NOW()),
('Spark SQL', '/content/spark/sql', 5, NOW()),
('Machine Learning con MLlib', '/content/spark/mllib', 6, NOW()),
('Streaming con Spark', '/content/spark/streaming', 7, NOW()),
('Optimización y Performance Tuning', '/content/spark/optimization', 8, NOW());

-- ============================================================================
-- 3. RELACIONES CURSO-CAPITULO
-- ============================================================================

-- Fullstack con Spring Boot y Angular
INSERT INTO curso_capitulo (id_curso, id_capitulo) SELECT 1, id_capitulo FROM capitulo WHERE contenido_url LIKE '/content/fullstack/%';

-- IBM DataPower Gateway  
INSERT INTO curso_capitulo (id_curso, id_capitulo) SELECT 10, id_capitulo FROM capitulo WHERE contenido_url LIKE '/content/datapower/%';

-- AWS para Desarrolladores
INSERT INTO curso_capitulo (id_curso, id_capitulo) SELECT 14, id_capitulo FROM capitulo WHERE contenido_url LIKE '/content/aws/%';

-- Apache Spark y Big Data
INSERT INTO curso_capitulo (id_curso, id_capitulo) SELECT 18, id_capitulo FROM capitulo WHERE contenido_url LIKE '/content/spark/%';

-- ============================================================================
-- 4. BADGES/INSIGNIAS DEL SISTEMA
-- ============================================================================

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

-- ============================================================================
-- 5. USUARIO DE PRUEBA
-- ============================================================================

INSERT INTO usuario (email, password, first_name, last_name, role, department, join_date, last_active, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES
('admin@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Admin', 'User', 'ADMIN', 'IT', NOW(), NOW(), true, true, true, true),
('student@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Student', 'Demo', 'STUDENT', 'Development', NOW(), NOW(), true, true, true, true),
('instructor@training-platform.dev', '$2a$10$x8Sq5QQjE3H3OhZ6xnJLuukOX6sTpB5i9q5Q8fR5hHRTpOzj5vJ5S', 'Instructor', 'Demo', 'INSTRUCTOR', 'Education', NOW(), NOW(), true, true, true, true);

-- Password para todos los usuarios: "password123"

-- ============================================================================
-- VERIFICACIÓN DE DATOS
-- ============================================================================

-- Consultas para verificar que todo se insertó correctamente:
-- SELECT COUNT(*) as total_cursos FROM curso;
-- SELECT COUNT(*) as total_capitulos FROM capitulo;  
-- SELECT COUNT(*) as total_badges FROM badge;
-- SELECT COUNT(*) as total_usuarios FROM usuario;
-- SELECT c.titulo, COUNT(ch.id_capitulo) as num_capitulos FROM curso c LEFT JOIN curso_capitulo cc ON c.id_curso = cc.id_curso LEFT JOIN capitulo ch ON cc.id_capitulo = ch.id_capitulo GROUP BY c.titulo ORDER BY c.titulo;

COMMIT;