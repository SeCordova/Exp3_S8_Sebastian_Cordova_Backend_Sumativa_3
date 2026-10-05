package com.duoc.bank.cuentas.service;
import com.duoc.bank.cuentas.dto.CuentaResponse;
import com.duoc.bank.cuentas.model.Cuenta;
import com.duoc.bank.cuentas.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CuentaService {
 private final CuentaRepository repo;
 public CuentaService(CuentaRepository repo){this.repo=repo;}
 public CuentaResponse obtener(Long cuentaId){
  Cuenta c=repo.findByCuentaId(cuentaId).orElseThrow(()->new IllegalArgumentException("Cuenta no encontrada"));
  return new CuentaResponse(c.getCuentaId(),c.getNombre(),c.getSaldo(),c.getTipo());
 }
 @Transactional public CuentaResponse debitar(Long cuentaId,Double monto){
  if(monto==null || monto<=0) throw new IllegalArgumentException("El monto debe ser mayor a 0");
  Cuenta c=repo.findByCuentaId(cuentaId).orElseThrow(()->new IllegalArgumentException("Cuenta no encontrada"));
  if(c.getSaldo()<monto) throw new IllegalStateException("Saldo insuficiente");
  c.setSaldo(c.getSaldo()-monto); repo.save(c);
  return new CuentaResponse(c.getCuentaId(),c.getNombre(),c.getSaldo(),c.getTipo());
 }
}
