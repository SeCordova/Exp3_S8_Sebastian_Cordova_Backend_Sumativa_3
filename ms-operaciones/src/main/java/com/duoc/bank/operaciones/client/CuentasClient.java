package com.duoc.bank.operaciones.client;
import com.duoc.bank.operaciones.dto.*; import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker; import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.*; import org.springframework.stereotype.Component; import org.springframework.web.client.RestTemplate;
@Component
public class CuentasClient {
 private final RestTemplate rest; public CuentasClient(RestTemplate rest){this.rest=rest;}
 @CircuitBreaker(name="cuentas",fallbackMethod="fallbackDebito") @Retry(name="cuentas")
 public CuentaResponse debitar(Long cuentaId,Double monto,String token){
  HttpHeaders h=new HttpHeaders(); h.setBearerAuth(token); h.setContentType(MediaType.APPLICATION_JSON);
  HttpEntity<DebitoRequest> entity=new HttpEntity<>(new DebitoRequest(monto),h);
  return rest.exchange("http://ms-cuentas/api/cuentas/{id}/debitar",HttpMethod.POST,entity,CuentaResponse.class,cuentaId).getBody();
 }
 public CuentaResponse fallbackDebito(Long cuentaId,Double monto,String token,Throwable ex){ throw new IllegalStateException("Servicio de cuentas no disponible. Circuit Breaker activo.",ex); }
}
