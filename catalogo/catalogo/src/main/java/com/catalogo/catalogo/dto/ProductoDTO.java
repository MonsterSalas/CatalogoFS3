package com.catalogo.catalogo.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String categoria;
}
