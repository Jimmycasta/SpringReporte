package com.example.springreporte.repository;

import com.example.springreporte.entities.Empleado;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepositoryPaging extends PagingAndSortingRepository<Empleado, Long>{
}
