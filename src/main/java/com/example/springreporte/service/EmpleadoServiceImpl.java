package com.example.springreporte.service;

import com.example.springreporte.entities.Empleado;
import com.example.springreporte.repository.IEmpleadoRepositoryJpa;
import com.example.springreporte.repository.IEmpleadoRepositoryPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepositoryPaging repositoryPaging;
    @Autowired
    private IEmpleadoRepositoryJpa repositoryJpa;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return (List<Empleado>) repositoryJpa.findAll();
    }

    @Override
    public Page<Empleado> findAll(Pageable pageable) {
        return repositoryPaging.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Empleado empleado) {
        repositoryJpa.save(empleado);

    }

    @Override
    @Transactional(readOnly = true)
    public Empleado findOne(Long id) {
        return repositoryJpa.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repositoryJpa.deleteById(id);
    }

}
