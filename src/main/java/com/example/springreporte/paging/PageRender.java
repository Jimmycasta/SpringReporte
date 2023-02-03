package com.example.springreporte.paging;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int elementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        elementosPorPagina = 5;
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;

        int desde;
        int hasta;

        if (totalPaginas <= elementosPorPagina) {
            desde = 1;
            hasta = totalPaginas;
        } else {
            if (paginaActual <= elementosPorPagina) {
                desde = 1;
                hasta = elementosPorPagina;
            } else if (paginaActual >= totalPaginas - elementosPorPagina / 2) {
                desde = totalPaginas - elementosPorPagina + 1;
                hasta = elementosPorPagina;
            } else {
                desde = paginaActual - elementosPorPagina / 2;
                hasta = elementosPorPagina;
            }
        }
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));

        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getElementosPorPagina() {
        return elementosPorPagina;
    }

    public void setElementosPorPagina(int elementosPorPagina) {
        this.elementosPorPagina = elementosPorPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<PageItem> paginas) {
        this.paginas = paginas;
    }
    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevius() {
        return page.hasPrevious();
    }

}
