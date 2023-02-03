package com.example.springreporte.controller;

import com.example.springreporte.entities.Empleado;
import com.example.springreporte.paging.PageRender;
import com.example.springreporte.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadoController {

    @Autowired
    private IEmpleadoService service;

    @GetMapping({"/","/listar",""})
    public String listarEmpleados(@RequestParam(name = "page",defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Empleado> empleados = service.findAll(pageRequest);
        PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);
        model.addAttribute("titulo", "Listado de empleados");
        model.addAttribute("empleados", empleados);
        model.addAttribute("page", pageRender);
        return "listar";

    }

}