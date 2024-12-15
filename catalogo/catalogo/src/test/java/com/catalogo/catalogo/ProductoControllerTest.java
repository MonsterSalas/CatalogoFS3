package com.catalogo.catalogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.catalogo.catalogo.controllers.ProductoController;
import com.catalogo.catalogo.dto.ProductoDTO;
import com.catalogo.catalogo.models.Producto;
import com.catalogo.catalogo.services.ProductoService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private ProductoDTO productoDTO;
    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Inicializar datos de prueba
        productoDTO = new ProductoDTO();
        // Aquí debes establecer los campos necesarios del DTO
        
        producto = new Producto();
        // Aquí debes establecer los campos necesarios del Producto
    }

    @Test
    void crearProducto_DeberiaRetornarProductoCreado() {
        // Arrange
        when(productoService.crearProducto(any(ProductoDTO.class))).thenReturn(producto);

        // Act
        ResponseEntity<Producto> response = productoController.crearProducto(productoDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(productoService).crearProducto(any(ProductoDTO.class));
    }

    @Test
    void getAllProductos_DeberiaRetornarListaDeProductos() {
        // Arrange
        List<Producto> productos = Arrays.asList(producto);
        when(productoService.getAllProductos()).thenReturn(productos);

        // Act
        ResponseEntity<List<Producto>> response = productoController.getAllProductos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productoService).getAllProductos();
    }

    @Test
    void getProductoById_DeberiaRetornarProducto() {
        // Arrange
        when(productoService.getProductoById(anyLong())).thenReturn(producto);

        // Act
        ResponseEntity<Producto> response = productoController.getProductoById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(productoService).getProductoById(1L);
    }

    @Test
    void updateProducto_DeberiaRetornarProductoActualizado() {
        // Arrange
        when(productoService.updateProducto(anyLong(), any(ProductoDTO.class))).thenReturn(producto);

        // Act
        ResponseEntity<Producto> response = productoController.updateProducto(1L, productoDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(productoService).updateProducto(1L, productoDTO);
    }

    @Test
    void deleteProducto_DeberiaRetornarNoContent() {
        // Arrange
        doNothing().when(productoService).deleteProducto(anyLong());

        // Act
        ResponseEntity<Void> response = productoController.deleteProducto(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productoService).deleteProducto(1L);
    }
}