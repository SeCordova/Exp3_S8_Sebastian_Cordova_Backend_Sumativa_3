# Exp3 Semana 8 - Desarrollo Backend III

Proyecto realizado como continuidad de la Semana 6. Para esta entrega se corrigió el enfoque anterior y se trabajó directamente con microservicios independientes, sin usar BFF.

## Objetivo

Preparar la solución bancaria para un entorno Cloud agregando seguridad OAuth 2.0, descubrimiento de servicios, resiliencia, mensajería asíncrona y Docker.

## Microservicios

- **ms-cuentas (8081):** consulta cuentas y realiza el débito del saldo.
- **ms-operaciones (8082):** recibe solicitudes de retiro. Se comunica con `ms-cuentas` usando Eureka y Load Balancer. Tiene Circuit Breaker y Retry con Resilience4j.
- **ms-transacciones (8083):** guarda y consulta las transacciones. Recibe los retiros de forma asíncrona mediante Kafka.
- **eureka-server (8761):** descubrimiento de servicios.
- **config-server (8888):** configuración centralizada.
- **Keycloak (8080):** servidor OAuth 2.0 para obtener tokens JWT.
- **MySQL (3306):** base de datos.
- **Kafka (9092):** mensajería entre microservicios.

## Requisitos

- Docker Desktop instalado y ejecutándose.
- Puertos 8080, 8081, 8082, 8083, 8761, 8888, 9092 y 3306 disponibles.

No es obligatorio tener Maven ni Java instalados para ejecutarlo con Docker, ya que las imágenes se compilan dentro de los Dockerfile.

## Cómo ejecutar

Desde la carpeta raíz del proyecto:

```powershell
docker compose up --build
```

La primera ejecución demora más porque Docker debe descargar las imágenes y compilar los proyectos Maven.

Para detener todo:

```powershell
docker compose down
```

Para borrar también el volumen de MySQL y volver a cargar los datos iniciales:

```powershell
docker compose down -v
```

## OAuth 2.0

Keycloak se inicia con un realm llamado `banco` y un cliente público `bank-client`.

Usuario de prueba:

- usuario: `seba`
- contraseña: `seba123`

Para obtener el token en PowerShell:

```powershell
$tokenResponse = Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8080/realms/banco/protocol/openid-connect/token" `
  -ContentType "application/x-www-form-urlencoded" `
  -Body "client_id=bank-client&grant_type=password&username=seba&password=seba123"

$token = $tokenResponse.access_token
```

## Pruebas

### 1. Consultar una cuenta

```powershell
Invoke-RestMethod -Method Get `
  -Uri "http://localhost:8081/api/cuentas/101" `
  -Headers @{ Authorization = "Bearer $token" }
```

### 2. Realizar un retiro

```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8082/api/operaciones/retiro" `
  -Headers @{ Authorization = "Bearer $token" } `
  -ContentType "application/json" `
  -Body '{"cuentaId":101,"monto":5000}'
```

El retiro descuenta el saldo desde `ms-cuentas`. Luego `ms-operaciones` publica un evento en Kafka y `ms-transacciones` lo consume y guarda en la base de datos.

### 3. Revisar transacciones

```powershell
Invoke-RestMethod -Method Get `
  -Uri "http://localhost:8083/api/transacciones/cuenta/101" `
  -Headers @{ Authorization = "Bearer $token" }
```

### 4. Revisar Eureka

Abrir en el navegador:

`http://localhost:8761`

Deberían aparecer registrados `MS-CUENTAS`, `MS-OPERACIONES` y `MS-TRANSACCIONES`.

### 5. Revisar Circuit Breaker

Estado del microservicio de operaciones:

`http://localhost:8082/actuator/health`

Para probar tolerancia a fallos se puede detener temporalmente cuentas:

```powershell
docker stop ms-cuentas
```

Luego repetir varias veces el retiro. `ms-operaciones` responderá con el fallback indicando que el servicio de cuentas no está disponible. Después se puede levantar nuevamente:

```powershell
docker start ms-cuentas
```


`-- README.md
```


