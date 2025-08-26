#!/bin/bash

# =============================================================================
# Script para poblar la base de datos del Training Platform
# =============================================================================

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuración de base de datos
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-postgres}"
DB_SCHEMA="${DB_SCHEMA:-training}"
DB_USER="${DB_USER:-postgres}"
DB_PASSWORD="${DB_PASSWORD:-postgres}"

# Función para imprimir mensajes con colores
print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_header() {
    echo -e "\n${BLUE}=============================================================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}=============================================================================${NC}\n"
}

# Verificar si PostgreSQL está disponible
check_postgres() {
    print_info "Verificando conexión a PostgreSQL..."
    
    if ! command -v psql &> /dev/null; then
        print_error "psql no está instalado. Por favor instala PostgreSQL client."
        exit 1
    fi
    
    if ! PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "SELECT 1;" &> /dev/null; then
        print_error "No se puede conectar a PostgreSQL. Verifica la configuración."
        echo "Host: $DB_HOST:$DB_PORT"
        echo "Database: $DB_NAME"
        echo "User: $DB_USER"
        exit 1
    fi
    
    print_success "Conexión a PostgreSQL exitosa"
}

# Ejecutar el script SQL
execute_sql() {
    local sql_file="$1"
    
    print_info "Ejecutando script SQL: $sql_file"
    
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "SET search_path TO $DB_SCHEMA;" -f "$sql_file"
    
    if [ $? -eq 0 ]; then
        print_success "Script SQL ejecutado correctamente"
    else
        print_error "Error al ejecutar el script SQL"
        exit 1
    fi
}

# Verificar datos insertados
verify_data() {
    print_info "Verificando datos insertados..."
    
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "
    SET search_path TO $DB_SCHEMA;
    
    SELECT 
        'Cursos' as tabla, 
        COUNT(*) as total 
    FROM curso
    UNION ALL
    SELECT 
        'Capítulos' as tabla, 
        COUNT(*) as total 
    FROM capitulo
    UNION ALL
    SELECT 
        'Badges' as tabla, 
        COUNT(*) as total 
    FROM badge
    UNION ALL
    SELECT 
        'Usuarios' as tabla, 
        COUNT(*) as total 
    FROM usuario;
    
    -- Mostrar cursos por categoría
    SELECT 
        categoria,
        COUNT(*) as num_cursos
    FROM curso 
    GROUP BY categoria 
    ORDER BY categoria;
    "
    
    print_success "Verificación de datos completada"
}

# Función principal
main() {
    print_header "🚀 TRAINING PLATFORM - POBLACIÓN DE BASE DE DATOS"
    
    # Directorio del script
    SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
    PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
    SQL_FILE="$PROJECT_DIR/src/main/resources/db/sample-data.sql"
    
    print_info "Directorio del proyecto: $PROJECT_DIR"
    print_info "Archivo SQL: $SQL_FILE"
    
    # Verificar que el archivo SQL existe
    if [ ! -f "$SQL_FILE" ]; then
        print_error "Archivo SQL no encontrado: $SQL_FILE"
        exit 1
    fi
    
    # Configuración mostrada
    print_info "Configuración de base de datos:"
    echo "  Host: $DB_HOST:$DB_PORT"
    echo "  Database: $DB_NAME"
    echo "  Schema: $DB_SCHEMA"
    echo "  User: $DB_USER"
    
    # Verificar conexión
    check_postgres
    
    # Preguntar confirmación
    echo ""
    read -p "¿Continuar con la población de datos? (y/N): " -n 1 -r
    echo ""
    
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        print_warning "Operación cancelada"
        exit 0
    fi
    
    # Ejecutar script
    execute_sql "$SQL_FILE"
    
    # Verificar resultados
    verify_data
    
    print_header "✨ DATOS INSERTADOS EXITOSAMENTE"
    print_success "La base de datos ha sido poblada con:"
    echo "  🖥️  Cursos Fullstack (Frontend, Backend, Fullstack)"
    echo "  🔗 APIs e Integraciones (DataPower, IBM Bus, Microservicios)"
    echo "  ☁️  Cursos Cloud (AWS, Azure, GCP, Kubernetes)"
    echo "  📊 Data Engineering (Spark, ETL, Streaming)"
    echo "  🏆 Sistema de badges e insignias"
    echo "  👥 Usuarios de prueba (admin, student, instructor)"
    echo ""
    print_info "Usuarios de prueba (password: 'password123'):"
    echo "  📧 admin@training-platform.dev (ADMIN)"
    echo "  📧 student@training-platform.dev (STUDENT)"
    echo "  📧 instructor@training-platform.dev (INSTRUCTOR)"
    echo ""
    print_success "¡Listo para usar! 🎉"
}

# Verificar argumentos
if [ "$1" = "--help" ] || [ "$1" = "-h" ]; then
    echo "Usage: $0 [options]"
    echo ""
    echo "Opciones de environment variables:"
    echo "  DB_HOST     - Host de PostgreSQL (default: localhost)"
    echo "  DB_PORT     - Puerto de PostgreSQL (default: 5432)"
    echo "  DB_NAME     - Nombre de la base de datos (default: postgres)"
    echo "  DB_SCHEMA   - Schema a usar (default: training)"
    echo "  DB_USER     - Usuario de PostgreSQL (default: postgres)"
    echo "  DB_PASSWORD - Password de PostgreSQL (default: postgres)"
    echo ""
    echo "Ejemplo:"
    echo "  DB_PASSWORD=mipassword $0"
    exit 0
fi

# Ejecutar función principal
main "$@"