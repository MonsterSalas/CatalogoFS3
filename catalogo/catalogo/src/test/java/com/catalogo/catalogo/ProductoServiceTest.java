package com.catalogo.catalogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.catalogo.catalogo.dto.ProductoDTO;
import com.catalogo.catalogo.models.Producto;
import com.catalogo.catalogo.repository.ProductoRepository;
import com.catalogo.catalogo.services.ProductoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private ProductoDTO productoDTO;
    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Inicializar DTO
        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto Test");
        productoDTO.setDescripcion("Descripción Test");
        productoDTO.setPrecio(1000.0);
        productoDTO.setStock(10);
        productoDTO.setCategoria("Categoría Test");
        
        // Inicializar Producto
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción Test");
        producto.setPrecio(1000.0);
        producto.setStock(10);
        producto.setCategoria("Categoría Test");
    }

    @Test
    void crearProducto_DeberiaCrearYRetornarProducto() {
        // Arrange
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto resultado = productoService.crearProducto(productoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(producto.getNombre(), resultado.getNombre());
        assertEquals(producto.getDescripcion(), resultado.getDescripcion());
        assertEquals(producto.getPrecio(), resultado.getPrecio());
        assertEquals(producto.getStock(), resultado.getStock());
        assertEquals(producto.getCategoria(), resultado.getCategoria());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void getAllProductos_DeberiaRetornarListaDeProductos() {
        // Arrange
        List<Producto> productos = Arrays.asList(producto);
        when(productoRepository.findAll()).thenReturn(productos);

        // Act
        List<Producto> resultado = productoService.getAllProductos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void getProductoById_DeberiaRetornarProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        // Act
        Producto resultado = productoService.getProductoById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(producto.getId(), resultado.getId());
        verify(productoRepository).findById(1L);
    }

    @Test
    void updateProducto_DeberiaActualizarYRetornarProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto resultado = productoService.updateProducto(1L, productoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(productoDTO.getNombre(), resultado.getNombre());
        assertEquals(productoDTO.getDescripcion(), resultado.getDescripcion());
        assertEquals(productoDTO.getPrecio(), resultado.getPrecio());
        assertEquals(productoDTO.getStock(), resultado.getStock());
        assertEquals(productoDTO.getCategoria(), resultado.getCategoria());
        verify(productoRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void deleteProducto_DeberiaEliminarProducto() {
        // Arrange
        doNothing().when(productoRepository).deleteById(anyLong());

        // Act
        productoService.deleteProducto(1L);

        // Assert
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void getProductoById_DeberiaLanzarExcepcionCuandoNoExisteProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productoService.getProductoById(1L);
        });
    }

    @Test
    void updateProducto_DeberiaLanzarExcepcionCuandoNoExisteProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productoService.updateProducto(1L, productoDTO);
        });
    }
}