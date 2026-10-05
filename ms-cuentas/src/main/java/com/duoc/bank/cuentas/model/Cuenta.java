package com.duoc.bank.cuentas.model;
import jakarta.persistence.*;
@Entity @Table(name="cuentas")
public class Cuenta {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="cuenta_id", unique=true, nullable=false) private Long cuentaId;
 private String nombre;
 private Double saldo;
 private String tipo;
 public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public Long getCuentaId(){return cuentaId;} public void setCuentaId(Long cuentaId){this.cuentaId=cuentaId;}
 public String getNombre(){return nombre;} public void setNombre(String nombre){this.nombre=nombre;}
 public Double getSaldo(){return saldo;} public void setSaldo(Double saldo){this.saldo=saldo;}
 public String getTipo(){return tipo;} public void setTipo(String tipo){this.tipo=tipo;}
}
