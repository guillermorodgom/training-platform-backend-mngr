# 📚 Training Platform - Scripts de Base de Datos

Este directorio contiene scripts para poblar y gestionar la base de datos del Training Platform.

## 🚀 Script de Población de Datos

### Descripción

El script `populate-database.sh` inserta datos de ejemplo en la base de datos, incluyendo:

- **🖥️ Cursos Fullstack**: Frontend, Backend, y Fullstack Development
- **🔗 APIs e Integraciones**: DataPower, IBM Bus, Microservicios, APIs RESTful
- **☁️ Cloud Computing**: AWS, Azure, GCP, Docker, Kubernetes  
- **📊 Data Engineering**: Spark, ETL, Streaming, Data Warehousing
- **🏆 Sistema de Badges**: Insignias y logros
- **👥 Usuarios de Prueba**: Admin, Student, Instructor

### 📋 Prerrequisitos

1. **PostgreSQL** instalado y ejecutándose
2. **Base de datos** `postgres` con schema `training` 
3. **Cliente psql** disponible en el PATH
4. **Aplicación Spring Boot** ejecutada al menos una vez (para crear tablas)

### 🔧 Uso Básico

```bash
# Navegar al directorio del proyecto
cd /path/to/training-platform-backend-mngr

# Ejecutar el script
./scripts/populate-database.sh
```

### ⚙️ Configuración Personalizada

Puedes personalizar la conexión usando variables de entorno:

```bash
# Configuración personalizada
DB_HOST=mi-servidor.com \
DB_PORT=5432 \
DB_NAME=mi_bd \
DB_SCHEMA=training \
DB_USER=mi_usuario \
DB_PASSWORD=mi_password \
./scripts/populate-database.sh
```

### 📊 Datos Insertados

#### Cursos por Categoría:

**🖥️ Fullstack (3 cursos)**
- Desarrollo Fullstack con Spring Boot y Angular
- Arquitectura Fullstack Moderna  
- Fullstack con React y Node.js

**🔧 Backend (3 cursos)**
- Spring Boot Avanzado
- Node.js y Express Profesional
- Microservicios con Java

**🎨 Frontend (3 cursos)**
- Angular Profesional
- React Avanzado
- Vue.js Completo

**🔗 APIs e Integraciones (4 cursos)**
- IBM DataPower Gateway
- IBM Integration Bus (IIB)
- Diseño de APIs RESTful
- Microservicios y Patrones de Integración

**☁️ Cloud (4 cursos)**
- AWS para Desarrolladores
- Azure DevOps y CI/CD
- Google Cloud Platform
- Docker y Kubernetes

**📊 Data Engineering (4 cursos)**
- Apache Spark y Big Data
- ETL con Python y Airflow
- Data Warehousing Moderno
- Streaming de Datos en Tiempo Real

#### Capítulos de Ejemplo:

Cada curso incluye 8 capítulos detallados. Ejemplos:

**IBM DataPower Gateway:**
1. Introducción a IBM DataPower
2. Instalación y Configuración Inicial
3. Web Service Proxy
4. Multi-Protocol Gateway
5. Políticas de Seguridad
6. Transformaciones XSLT
7. Monitoreo y Troubleshooting
8. Best Practices y Optimización

**AWS para Desarrolladores:**
1. Fundamentos de AWS
2. EC2: Instancias y Configuración
3. S3: Almacenamiento en la Nube
4. RDS: Bases de Datos Relacionales
5. Lambda: Funciones Serverless
6. API Gateway
7. CloudFormation: Infraestructura como Código
8. Monitoreo con CloudWatch

#### Sistema de Badges:

**🏆 Insignias de Finalización:**
- Fullstack Master (EPIC) - 500 puntos
- API Integration Expert (RARE) - 300 puntos
- Cloud Architect (EPIC) - 500 puntos
- Data Engineering Pro (LEGENDARY) - 1000 puntos

**⭐ Insignias de Excelencia:**
- Perfect Score (RARE) - 200 puntos
- Quick Learner (COMMON) - 100 puntos
- Consistent Learner (COMMON) - 150 puntos
- Milestone Achiever (RARE) - 250 puntos

#### Usuarios de Prueba:

| Email | Password | Rol | Departamento |
|-------|----------|-----|--------------|
| admin@training-platform.dev | password123 | ADMIN | IT |
| student@training-platform.dev | password123 | STUDENT | Development |
| instructor@training-platform.dev | password123 | INSTRUCTOR | Education |

### 🔍 Verificación

El script automáticamente verifica que los datos se insertaron correctamente y muestra:

- Conteo total por tabla (cursos, capítulos, badges, usuarios)
- Distribución de cursos por categoría
- Confirmación de éxito

### 🛠️ Solución de Problemas

#### Error de Conexión:
```bash
❌ No se puede conectar a PostgreSQL
```
**Solución**: Verificar que PostgreSQL esté ejecutándose y las credenciales sean correctas.

#### Tablas No Existen:
```bash
❌ relation "curso" does not exist
```
**Solución**: Ejecutar primero la aplicación Spring Boot para crear las tablas automáticamente.

#### Schema No Encontrado:
```bash
❌ schema "training" does not exist
```
**Solución**: Crear el schema manualmente:
```sql
CREATE SCHEMA IF NOT EXISTS training;
```

### 📝 Archivos Relacionados

- `sample-data.sql` - Script SQL con todos los datos de ejemplo
- `populate-database.sh` - Script de shell para ejecutar la población
- `README.md` - Esta documentación

### 🔄 Regenerar Datos

Para limpiar y regenerar los datos:

```bash
# 1. Reiniciar la aplicación Spring Boot (con ddl-auto=create-drop)
# 2. Ejecutar nuevamente el script
./scripts/populate-database.sh
```

### 📚 Uso en Desarrollo

Este script es ideal para:

- ✅ Setup inicial de desarrollo
- ✅ Testing de la aplicación
- ✅ Demos y presentaciones  
- ✅ Desarrollo de nuevas funcionalidades
- ✅ Pruebas de carga con datos realistas

### 🎯 Próximos Pasos

Después de ejecutar el script, puedes:

1. **Iniciar el backend** (`mvn spring-boot:run`)
2. **Iniciar el frontend** (`ng serve`)
3. **Acceder a la aplicación** (`http://localhost:4200`)
4. **Login con usuarios de prueba**
5. **Explorar cursos y funcionalidades**

¡Disfruta desarrollando con el Training Platform! 🚀