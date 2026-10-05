Write-Host "Obteniendo token OAuth2..."
$tokenResponse = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/realms/banco/protocol/openid-connect/token" -ContentType "application/x-www-form-urlencoded" -Body "client_id=bank-client&grant_type=password&username=seba&password=seba123"
$token = $tokenResponse.access_token
Write-Host "Token obtenido correctamente."

Write-Host "`nCuenta 101 antes del retiro:"
Invoke-RestMethod -Method Get -Uri "http://localhost:8081/api/cuentas/101" -Headers @{ Authorization = "Bearer $token" } | ConvertTo-Json

Write-Host "`nRealizando retiro de 5000..."
Invoke-RestMethod -Method Post -Uri "http://localhost:8082/api/operaciones/retiro" -Headers @{ Authorization = "Bearer $token" } -ContentType "application/json" -Body '{"cuentaId":101,"monto":5000}' | ConvertTo-Json

Start-Sleep -Seconds 2
Write-Host "`nTransacciones de la cuenta 101:"
Invoke-RestMethod -Method Get -Uri "http://localhost:8083/api/transacciones/cuenta/101" -Headers @{ Authorization = "Bearer $token" } | ConvertTo-Json
