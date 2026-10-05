package com.duoc.bank.operaciones.controller;
import com.duoc.bank.operaciones.dto.*; import com.duoc.bank.operaciones.service.OperacionService;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/operaciones")
public class OperacionController {
 private final OperacionService service; public OperacionController(OperacionService service){this.service=service;}
 @PostMapping("/retiro") public RetiroResponse retirar(@RequestBody RetiroRequest req,@RequestHeader("Authorization") String auth){ return service.retirar(req,auth.replace("Bearer ","")); }
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<String> bad(IllegalArgumentException ex){return ResponseEntity.badRequest().body(ex.getMessage());}
 @ExceptionHandler(IllegalStateException.class) ResponseEntity<String> unavailable(IllegalStateException ex){return ResponseEntity.status(503).body(ex.getMessage());}
}
