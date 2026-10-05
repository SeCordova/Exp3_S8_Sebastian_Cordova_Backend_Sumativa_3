package com.duoc.bank.transacciones.event;
public record RetiroEvent(Long cuentaId, Double monto, String tipo) {}
