package com.example.springreporte.repository;

import com.example.springreporte.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepositoryJpa extends JpaRepository<Empleado, Long> {
}
