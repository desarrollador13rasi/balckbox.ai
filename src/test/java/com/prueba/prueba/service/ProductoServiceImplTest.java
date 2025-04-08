package com.prueba.prueba.service;

import com.prueba.prueba.model.Producto;
import com.prueba.prueba.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void getAllProductos() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Producto producto2 = new Producto("Producto 2", "Desc 2", 20.0, 10);
        Page<Producto> expected = new PageImpl<>(Arrays.asList(producto1, producto2));
        
        when(productoRepository.findAll(any(Pageable.class))).thenReturn(expected);

        // Act
        Page<Producto> result = productoService.getAllProductos(0, 10);

        // Assert
        assertEquals(2, result.getContent().size());
        verify(productoRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void searchByNombre() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Page<Producto> expected = new PageImpl<>(List.of(producto1));
        
        when(productoRepository.findByNombreContainingIgnoreCase(anyString(), any(Pageable.class)))
            .thenReturn(expected);

        // Act
        Page<Producto> result = productoService.searchByNombre("Producto", 0, 10);

        // Assert
        assertEquals(1, result.getContent().size());
        verify(productoRepository, times(1))
            .findByNombreContainingIgnoreCase(anyString(), any(Pageable.class));
    }

    @Test
    void filterByPriceRange() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Page<Producto> expected = new PageImpl<>(List.of(producto1));
        
        when(productoRepository.findByPrecioBetween(anyDouble(), anyDouble(), any(Pageable.class)))
            .thenReturn(expected);

        // Act
        Page<Producto> result = productoService.filterByPriceRange(5.0, 15.0, 0, 10);

        // Assert
        assertEquals(1, result.getContent().size());
        verify(productoRepository, times(1))
            .findByPrecioBetween(anyDouble(), anyDouble(), any(Pageable.class));
    }

    @Test
    void getProductoById() {
        // Arrange
        Long id = 1L;
        Producto expected = new Producto("Producto 1", "Desc 1", 10.0, 5);
        expected.setId(id);
        
        when(productoRepository.findById(id)).thenReturn(Optional.of(expected));

        // Act
        Producto result = productoService.getProductoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    void createProducto() {
        // Arrange
        Producto producto = new Producto("Nuevo", "Desc", 15.0, 3);
        Producto saved = new Producto("Nuevo", "Desc", 15.0, 3);
        saved.setId(1L);
        
        when(productoRepository.save(producto)).thenReturn(saved);

        // Act
        Producto result = productoService.createProducto(producto);

        // Assert
        assertNotNull(result.getId());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void updateProducto() {
        // Arrange
        Long id = 1L;
        Producto producto = new Producto("Actualizado", "Desc", 20.0, 5);
        Producto existing = new Producto("Original", "Desc", 10.0, 3);
        existing.setId(id);
        
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto result = productoService.updateProducto(id, producto);

        // Assert
        assertEquals("Actualizado", result.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void deleteProducto() {
        // Arrange
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        // Act
        productoService.deleteProducto(id);

        // Assert
        verify(productoRepository, times(1)).deleteById(id);
    }
}
