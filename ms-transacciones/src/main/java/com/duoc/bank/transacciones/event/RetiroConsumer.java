package com.duoc.bank.transacciones.event;
import com.duoc.bank.transacciones.model.Transaccion; import com.duoc.bank.transacciones.repository.TransaccionRepository;
import org.springframework.kafka.annotation.KafkaListener; import org.springframework.stereotype.Component; import java.time.LocalDateTime;
@Component
public class RetiroConsumer {
 private final TransaccionRepository repo;
 public RetiroConsumer(TransaccionRepository repo){this.repo=repo;}
 @KafkaListener(topics="retiros",groupId="ms-transacciones")
 public void recibir(RetiroEvent event){
  Transaccion t=new Transaccion(); t.setCuentaId(event.cuentaId()); t.setMonto(event.monto()); t.setTipo(event.tipo()); t.setFecha(LocalDateTime.now()); repo.save(t);
 }
}
