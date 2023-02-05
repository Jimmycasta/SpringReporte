package com.example.springreporte.controller;

import com.example.springreporte.entities.Empleado;
import com.example.springreporte.paging.PageRender;
import com.example.springreporte.reports.EmpleadoExporterExcel;
import com.example.springreporte.reports.EmpleadoExporterPDF;
import com.example.springreporte.service.IEmpleadoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class EmpleadoController {

    @Autowired
    private IEmpleadoService service;

    @GetMapping({"/", "/listar", ""})
    public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Empleado> empleados = service.findAll(pageRequest);
        PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);
        model.addAttribute("titulo", "Listado de empleados");
        model.addAttribute("empleados", empleados);
        model.addAttribute("page", pageRender);
        return "listar";

    }

    @GetMapping("/ver/{id}")
    public String detalleEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Empleado empleado = service.findOne(id);
        if (empleado == null) {
            flash.addFlashAttribute("error", "El empleado no esta en la base de datos.");
            return "redirect:/listar";
        }
        modelo.put("empleado", empleado);
        modelo.put("titulo", "Detalle del empleado " + empleado.getNombre());
        return "ver";
    }

    @GetMapping("/form")
    public String registroEmpleado(Map<String, Object> modelo) {
        Empleado empleado = new Empleado();
        modelo.put("empleado", empleado);
        modelo.put("titulo", "Registro Empleados");
        return "form";

    }
    @PostMapping("/form")
    public String guardaEmpleado(@Valid Empleado empleado, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de Empleado");
            return "form";
        }
        String mensaje = (empleado.getId() != null) ? "El empleado ha sido editado" : "Empleado registrado con exito";
        service.save(empleado);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listar";

    }

    @GetMapping("/form/{id}")
    public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Empleado empleado = null;
        if (id > 0) {
            empleado = service.findOne(id);
            if (empleado == null) {
                flash.addFlashAttribute("error", "El Id de empleado no existe");
                return "redirect:/listar";

            }
        } else {
            flash.addFlashAttribute("error", "El Id de empleado no puede ser =");
            return "redirect:/listar";
        }
        modelo.put("empleado", empleado);
        modelo.put("titulo", "Edición de empleado");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            service.delete(id);
            flash.addAttribute("success", "Cliente eliminado");
        }
        return "redirect:/listar";

    }

    @GetMapping("/exportarPDF")
    public void exportarListaPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Empleado> empleados = service.findAll();

        EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
        exporter.exportarPdf(response);



    }

    @GetMapping("/exportarExcel")
    public void exportarListaExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Empleado> empleados = service.findAll();

        EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
        exporter.exportarExcel(response);



    }


}
