package com.duoc.bank.transacciones.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="transacciones")
public class Transaccion {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long cuentaId; private Double monto; private String tipo; private LocalDateTime fecha;
 public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public Long getCuentaId(){return cuentaId;} public void setCuentaId(Long v){cuentaId=v;}
 public Double getMonto(){return monto;} public void setMonto(Double v){monto=v;}
 public String getTipo(){return tipo;} public void setTipo(String v){tipo=v;}
 public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime v){fecha=v;}
}
