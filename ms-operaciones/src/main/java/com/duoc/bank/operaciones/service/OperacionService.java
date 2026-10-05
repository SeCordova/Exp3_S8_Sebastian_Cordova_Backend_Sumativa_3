package com.duoc.bank.operaciones.service;
import com.duoc.bank.operaciones.client.CuentasClient; import com.duoc.bank.operaciones.dto.*; import com.duoc.bank.operaciones.event.RetiroEvent;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Service;
@Service
public class OperacionService {
 private final CuentasClient cuentas; private final KafkaTemplate<String,RetiroEvent> kafka;
 public OperacionService(CuentasClient cuentas,KafkaTemplate<String,RetiroEvent> kafka){this.cuentas=cuentas;this.kafka=kafka;}
 public RetiroResponse retirar(RetiroRequest req,String token){
  if(req.cuentaId()==null || req.monto()==null || req.monto()<=0) throw new IllegalArgumentException("Datos de retiro invalidos");
  CuentaResponse c=cuentas.debitar(req.cuentaId(),req.monto(),token);
  kafka.send("retiros",String.valueOf(req.cuentaId()),new RetiroEvent(req.cuentaId(),req.monto(),"RETIRO"));
  return new RetiroResponse("Retiro realizado correctamente",req.cuentaId(),req.monto(),c.saldo());
 }
}
