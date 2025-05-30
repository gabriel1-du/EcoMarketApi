package com.example.ApiPedido.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ApiPedido.Model.Carrito;
import com.example.ApiPedido.Model.ItemCarrito;
import com.example.ApiPedido.Model.ItemPedido;
import com.example.ApiPedido.Model.Pedido;
import com.example.ApiPedido.Model.Producto;
import com.example.ApiPedido.Repository.CarritoRepository;
import com.example.ApiPedido.Repository.PedidoRepository;
import com.example.ApiPedido.Repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

public class PedidoService2 {

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
     public Pedido crearPedidoDesdeCarrito(Integer id) {
        // 1. Obtener carrito existente
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        // 2. Preparar Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(carrito.getUsuario());
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE"); // o lo que corresponda

        List<ItemPedido> itemsPedido = new ArrayList<>();
        int total = 0;

        // 3. Convertir cada ItemCarrito en ItemPedido
        //Esto para no estropear el sistema de moodelo
        for (ItemCarrito itemCarrito : carrito.getItems()) {
            Integer productoId = itemCarrito.getProductoId();

            // Obtener precio del producto desde el productoId
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: ID " + id));

            Integer precioUnitario = producto.getPrecio(); // suponiendo que es Integer
            Integer cantidad = itemCarrito.getCantidad();

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido); // relación bidireccional
            itemPedido.setProductoId(productoId);
            itemPedido.setCantidad(cantidad);
            itemPedido.setPrecioUnitario(precioUnitario);

            total += cantidad * precioUnitario;
            itemsPedido.add(itemPedido);
        }

        // 4. Asignar ítems y total
        pedido.setItems(itemsPedido);
        pedido.setTotal(total);

        // 5. Guardar pedido (con items en cascada)
        return pedidoRepository.save(pedido);
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