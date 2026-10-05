package com.duoc.bank.cuentas.controller;
import com.duoc.bank.cuentas.dto.*;
import com.duoc.bank.cuentas.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/cuentas")
public class CuentaController {
 private final CuentaService service;
 public CuentaController(CuentaService service){this.service=service;}
 @GetMapping("/{id}") public CuentaResponse obtener(@PathVariable Long id){return service.obtener(id);}
 @PostMapping("/{id}/debitar") public CuentaResponse debitar(@PathVariable Long id,@RequestBody DebitoRequest req){return service.debitar(id,req.monto());}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<String> bad(IllegalArgumentException ex){return ResponseEntity.badRequest().body(ex.getMessage());}
 @ExceptionHandler(IllegalStateException.class) ResponseEntity<String> conflict(IllegalStateException ex){return ResponseEntity.status(409).body(ex.getMessage());}
}
