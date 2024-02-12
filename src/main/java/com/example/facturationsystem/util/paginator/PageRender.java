package com.example.facturationsystem.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se utiliza para renderizar la paginación de resultados de una
 * página.
 * Está parametrizada con un tipo {@code T} que representa el tipo de elementos
 * contenidos en la página.
 *
 * @param <T> el tipo de elementos contenidos en la página
 */
public class PageRender<T> {

    private final String url;
    private final Page<T> page;
    private final int totalPages;
    private final int currentPage;
    private final List<PageItem> pages;

    /**
     * Constructor que inicializa la clase {@code PageRender} con la URL base y la
     * página de resultados.
     *
     * @param url  la URL base para la paginación
     * @param page la página de resultados a renderizar
     */
    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.totalPages = page.getTotalPages();
        int numberOfItemsPerPage = page.getSize();
        this.currentPage = page.getNumber() + 1;
        this.pages = new ArrayList<>();

        int from, to;

        if (totalPages <= numberOfItemsPerPage) {
            from = 1;
            to = totalPages;
        } else {
            if (currentPage <= numberOfItemsPerPage / 2) {
                from = 1;
                to = numberOfItemsPerPage;
            } else if (currentPage >= totalPages - numberOfItemsPerPage / 2) {
                from = totalPages - numberOfItemsPerPage + 1;
                to = numberOfItemsPerPage;
            } else {
                from = currentPage - numberOfItemsPerPage / 2;
                to = numberOfItemsPerPage;
            }
        }

        for (int i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, currentPage == from + i));
        }
    }

    /**
     * Devuelve la URL base para la paginación.
     *
     * @return la URL base
     */
    public String getUrl() {
        return url;
    }

    /**
     * Devuelve el número total de páginas.
     *
     * @return el número total de páginas
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Devuelve el número de la página actual.
     *
     * @return el número de la página actual
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Devuelve una lista de elementos de página.
     *
     * @return una lista de elementos de página
     */
    public List<PageItem> getPages() {
        return pages;
    }

    /**
     * Devuelve true si la página actual es la primera página.
     *
     * @return true si la página actual es la primera página, de lo contrario false
     */
    public boolean isFirst() {
        return page.isFirst();
    }

    /**
     * Devuelve true si la página actual es la última página.
     *
     * @return true si la página actual es la última página, de lo contrario false
     */
    public boolean isLast() {
        return page.isLast();
    }

    /**
     * Devuelve true si hay una página siguiente después de la página actual.
     *
     * @return true si hay una página siguiente, de lo contrario false
     */
    public boolean isHasNext() {
        return page.hasNext();
    }

    /**
     * Devuelve true si hay una página anterior antes de la página actual.
     *
     * @return true si hay una página anterior, de lo contrario false
     */
    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
