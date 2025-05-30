package com.example.ApiPedido.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiPedido.DTO.PedidoDTO;
import com.example.ApiPedido.DTO.PedidoMapper;
import com.example.ApiPedido.Model.Pedido;
import com.example.ApiPedido.Service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    //Inyeccion de los servicios
    @Autowired
    private PedidoService pedidoService;


    //-------------------
    //METODOS GET
    //-----------------


    // Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Pedido pedido = pedidoService.getById(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }
    }

    //Obtener todos 
    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        return ResponseEntity.ok(pedidoService.getAll());
    }

      
    
    //-----------------
    //METODOS POST
    //-----------------

    // Crear pedido A PARTIR DEL CARRITO ruta = (POST /api/pedido/{carritoId})
     @PostMapping
    public ResponseEntity<PedidoDTO> crearPedidoDesdeCarrito(@RequestParam Long carritoId) {
        Pedido pedido = pedidoService.crearPedidoDesdeCarrito(carritoId);
        PedidoDTO dto = PedidoMapper.toDto(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }



    //---------------
    //Metodos PUT
    //---------------

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.update(id, pedido);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado para actualizar");
        }
    }

    //---------------
    //METODOS DELETE
    //-----------------
    // Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Pedido eliminado = pedidoService.delete(id);
        if (eliminado != null) {
            return ResponseEntity.ok(eliminado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado para eliminar");
        }
    }




}
