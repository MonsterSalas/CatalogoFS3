package com.catalogo.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalogo.catalogo.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
