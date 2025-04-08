package com.prueba.prueba.service;

import com.prueba.prueba.model.Producto;
import com.prueba.prueba.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Page<Producto> getAllProductos(int page, int size) {
        return productoRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Producto> searchByNombre(String nombre, int page, int size) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre, PageRequest.of(page, size));
    }

    @Override
    public Page<Producto> filterByPriceRange(double minPrice, double maxPrice, int page, int size) {
        return productoRepository.findByPrecioBetween(minPrice, maxPrice, PageRequest.of(page, size));
    }

    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
