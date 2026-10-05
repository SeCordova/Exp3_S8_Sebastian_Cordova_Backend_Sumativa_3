package com.duoc.bank.transacciones.repository;
import com.duoc.bank.transacciones.model.Transaccion; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface TransaccionRepository extends JpaRepository<Transaccion,Long>{ List<Transaccion> findByCuentaIdOrderByFechaDesc(Long cuentaId); }
