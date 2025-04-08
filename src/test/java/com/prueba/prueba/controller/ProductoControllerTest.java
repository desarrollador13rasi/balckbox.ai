package com.prueba.prueba.controller;

import com.prueba.prueba.model.Producto;
import com.prueba.prueba.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @Test
    void getAllProductos() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Producto producto2 = new Producto("Producto 2", "Desc 2", 20.0, 10);
        Page<Producto> expected = new PageImpl<>(Arrays.asList(producto1, producto2));
        
        when(productoService.getAllProductos(anyInt(), anyInt())).thenReturn(expected);

        // Act
        ResponseEntity<Page<Producto>> response = productoController.getAllProductos(0, 10);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getContent().size());
        verify(productoService, times(1)).getAllProductos(anyInt(), anyInt());
    }

    @Test
    void searchProductos() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Page<Producto> expected = new PageImpl<>(List.of(producto1));
        
        when(productoService.searchByNombre(anyString(), anyInt(), anyInt()))
            .thenReturn(expected);

        // Act
        ResponseEntity<Page<Producto>> response = productoController.searchProductos("Producto", 0, 10);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(productoService, times(1))
            .searchByNombre(anyString(), anyInt(), anyInt());
    }

    @Test
    void filterByPriceRange() {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Desc 1", 10.0, 5);
        Page<Producto> expected = new PageImpl<>(List.of(producto1));
        
        when(productoService.filterByPriceRange(anyDouble(), anyDouble(), anyInt(), anyInt()))
            .thenReturn(expected);

        // Act
        ResponseEntity<Page<Producto>> response = productoController.filterByPriceRange(5.0, 15.0, 0, 10);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        verify(productoService, times(1))
            .filterByPriceRange(anyDouble(), anyDouble(), anyInt(), anyInt());
    }

    @Test
    void getProductoById() {
        // Arrange
        Long id = 1L;
        Producto expected = new Producto("Producto 1", "Desc 1", 10.0, 5);
        expected.setId(id);
        
        when(productoService.getProductoById(id)).thenReturn(expected);

        // Act
        ResponseEntity<Producto> response = productoController.getProductoById(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(id, response.getBody().getId());
        verify(productoService, times(1)).getProductoById(id);
    }

    @Test
    void createProducto() {
        // Arrange
        Producto producto = new Producto("Nuevo", "Desc", 15.0, 3);
        Producto saved = new Producto("Nuevo", "Desc", 15.0, 3);
        saved.setId(1L);
        
        when(productoService.createProducto(producto)).thenReturn(saved);

        // Act
        ResponseEntity<Producto> response = productoController.createProducto(producto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getId());
        verify(productoService, times(1)).createProducto(producto);
    }

    @Test
    void updateProducto() {
        // Arrange
        Long id = 1L;
        Producto producto = new Producto("Actualizado", "Desc", 20.0, 5);
        
        when(productoService.updateProducto(id, producto)).thenReturn(producto);

        // Act
        ResponseEntity<Producto> response = productoController.updateProducto(id, producto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Actualizado", response.getBody().getNombre());
        verify(productoService, times(1)).updateProducto(id, producto);
    }

    @Test
    void deleteProducto() {
        // Arrange
        Long id = 1L;
        doNothing().when(productoService).deleteProducto(id);

        // Act
        ResponseEntity<Void> response = productoController.deleteProducto(id);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(productoService, times(1)).deleteProducto(id);
    }
}
