package com.example.facturationsystem.repositories;

import java.util.List;

import com.example.facturationsystem.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca y devuelve una lista de productos cuyos nombres contienen la cadena
     * especificada.
     *
     * @param term El término de búsqueda para encontrar productos por nombre.
     * @return Una lista de productos cuyos nombres coinciden parcialmente con el
     *         término especificado.
     */
    @Query("select p from Product p where p.name like %?1%")
    List<Product> findByName(String term);

    /**
     * Busca y devuelve una lista de productos cuyos nombres coinciden parcialmente,
     * sin distinguir mayúsculas y minúsculas, con la cadena especificada.
     * <p>
     * Nota: Esta es otra forma de implementar el método
     * {@code findByName(String term)}
     * pero sin la anotación {@code @Query} y utilizando la creación de consultas a
     * partir de nombres de métodos (JPA Query Methods).
     * </p>
     *
     * @param term El término de búsqueda para encontrar productos por nombre.
     * @return Una lista de productos cuyos nombres coinciden parcialmente, sin
     *         distinguir mayúsculas y minúsculas, con el término especificado.
     */
    List<Product> findByNameLikeIgnoreCase(String term);
}
