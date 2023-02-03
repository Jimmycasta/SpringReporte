package com.example.springreporte.service;

import com.example.springreporte.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IEmpleadoService {

    public List<Empleado> findAll();

    public Page<Empleado> findAll(Pageable pageable);

    public void save(Empleado empleado);

    public Empleado findOne(Long id);

    public void delete(Long id);

}
