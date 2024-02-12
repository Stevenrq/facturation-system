package com.example.facturationsystem.util.paginator;

/**
 * Un registro (record) que representa un elemento de página en una paginación.
 * Cada elemento de página tiene un número y un indicador booleano que indica si
 * es la página actual.
 */
public record PageItem(int number, boolean current) {

    /**
     * Devuelve el número de página.
     *
     * @return el número de página
     */
    @Override
    public int number() {
        return number;
    }

    /**
     * Devuelve un valor booleano que indica si este elemento de página es la página
     * actual.
     *
     * @return true si este elemento de página es la página actual, de lo contrario
     *         false
     */
    @Override
    public boolean current() {
        return current;
    }
}
