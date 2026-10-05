package com.duoc.bank.cuentas.repository;
import com.duoc.bank.cuentas.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CuentaRepository extends JpaRepository<Cuenta,Long>{ Optional<Cuenta> findByCuentaId(Long cuentaId); }
