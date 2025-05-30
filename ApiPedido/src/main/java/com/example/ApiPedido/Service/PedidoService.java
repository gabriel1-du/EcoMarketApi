package com.example.ApiPedido.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ApiPedido.Model.Carrito;
import com.example.ApiPedido.Model.ItemCarrito;
import com.example.ApiPedido.Model.ItemPedido;
import com.example.ApiPedido.Model.Pedido;
import com.example.ApiPedido.Model.Producto;
import com.example.ApiPedido.Repository.CarritoRepository;
import com.example.ApiPedido.Repository.PedidoRepository;
import com.example.ApiPedido.Repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

    //Inyecciones Repositorios
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    //-------------
    //Metodos para crear
    //--------------

    public Pedido crearPedidoDesdeCarrito(Integer carritoId) {
        // Buscar el carrito
        Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setUsuario(carrito.getUsuario());
        pedido.setItems(carrito.getItems()); // si los items del carrito se pueden copiar directamente

        // Guardar el pedido
        return pedidoRepository.save(pedido);
    }
}
    //------------
    //Metodos para obtener
    //-----------

    //Obtener todos los pedidos
    public List<Pedido> getAll() {
        return pedidoRepository.findAll(); 
    }

    //Obtener un pedido por Id
    public Pedido getById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    

    //-----------------
    //Metodos para actualizar
    //------------------

    //Actualizar un pedido
    public Pedido update(Integer id, Pedido pedido) {
        Optional<Pedido> existenteOpt = pedidoRepository.findById(id);
            if (existenteOpt.isPresent()) {
            Pedido existente = existenteOpt.get();

            // Solo se permite actualizar estado por ahora
            existente.setEstado(pedido.getEstado());

        return pedidoRepository.save(existente);
        }
        return null;
   }

   //-------------
   //Metodo para eliminar
   //------------
   public Pedido delete(Integer id) {
    Optional<Pedido> pedido = pedidoRepository.findById(id);
    if (pedido.isPresent()) {
        pedidoRepository.deleteById(id);
        return pedido.get();
    }
    return null;
    }
}