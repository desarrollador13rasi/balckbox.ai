package com.prueba.prueba.service;

import com.prueba.prueba.model.Producto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductoService {
    Page<Producto> getAllProductos(int page, int size);
    Producto getProductoById(Long id);
    Producto createProducto(Producto producto);
    Producto updateProducto(Long id, Producto producto);
    void deleteProducto(Long id);
    Page<Producto> searchByNombre(String nombre, int page, int size);
    Page<Producto> filterByPriceRange(double minPrice, double maxPrice, int page, int size);
}
