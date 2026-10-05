package com.duoc.bank.transacciones.controller;
import com.duoc.bank.transacciones.model.Transaccion; import com.duoc.bank.transacciones.repository.TransaccionRepository;
import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/transacciones")
public class TransaccionController {
 private final TransaccionRepository repo; public TransaccionController(TransaccionRepository repo){this.repo=repo;}
 @GetMapping public List<Transaccion> listar(){return repo.findAll();}
 @GetMapping("/cuenta/{cuentaId}") public List<Transaccion> porCuenta(@PathVariable Long cuentaId){return repo.findByCuentaIdOrderByFechaDesc(cuentaId);}
}
