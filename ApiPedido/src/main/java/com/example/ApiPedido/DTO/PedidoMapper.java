package com.example.ApiPedido.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ApiPedido.Model.ItemCarrito;
import com.example.ApiPedido.Model.ItemPedido;
import com.example.ApiPedido.Model.Pedido;
import com.example.ApiPedido.Model.Usuario;

public class PedidoMapper {

     public static UsuarioDTO toDto(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        return dto;
    }

    public static ItemPedidoDTO toDto(ItemPedido ip) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setProductoId(ip.getProductoId());
        dto.setCantidad(ip.getCantidad());
        dto.setPrecioUnitario(ip.getPrecioUnitario());
        return dto;
    }

    public static PedidoDTO toDto(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());
        dto.setFechaPedido(p.getFechaPedido());
        dto.setTotal(p.getTotal());
        dto.setEstado(p.getEstado());
        dto.setUsuario(toDto(p.getUsuario()));

        List<ItemPedidoDTO> items = p.getItems()
            .stream()
            .map(PedidoMapper::toDto)
            .collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }

     /**
     * Convierte un ItemCarrito (entidad) en ItemPedido (entidad).
     */
    public static ItemPedido fromCarrito(ItemCarrito ic) {
        ItemPedido ip = new ItemPedido();
        ip.setProductoId(ic.getProductoId());
        ip.setCantidad(ic.getCantidad());
        // El precio lo obtienes vía productoRepository en el service, o si ic trae precio:
        ip.setPrecioUnitario(ic.getPrecioUnitario());
        return ip;
    }
}
