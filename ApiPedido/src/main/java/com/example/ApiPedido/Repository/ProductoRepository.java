package com.example.ApiPedido.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ApiPedido.Model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Integer> {

}
