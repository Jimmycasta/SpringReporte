package com.example.springreporte.reports;

import com.example.springreporte.entities.Empleado;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class EmpleadoExporterPDF {

    private List<Empleado> listaEmpleados;

    public EmpleadoExporterPDF(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    private void cabeceraTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.gray);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Apellido", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Email", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Teléfono", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Sexo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Salario", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);

    }

    private void datosTabla(PdfPTable tabla) {
        for (Empleado empleado : listaEmpleados) {

            tabla.addCell(String.valueOf(empleado.getId()));
            tabla.addCell(empleado.getNombre());
            tabla.addCell(empleado.getApellido());
            tabla.addCell(empleado.getEmail());
            tabla.addCell(empleado.getTelefono());
            tabla.addCell(empleado.getSexo());
            tabla.addCell(String.valueOf(empleado.getSalario()));
            tabla.addCell(String.valueOf(empleado.getFecha()));
        }

    }

    public void exportarPdf(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.LETTER);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.black);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de empleados", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(8);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f, 2.2f});
        tabla.setWidthPercentage(110);

        cabeceraTabla(tabla);
        datosTabla(tabla);

        documento.add(tabla);
        documento.close();
    }


}
