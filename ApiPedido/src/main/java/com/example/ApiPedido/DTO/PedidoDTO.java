package com.example.ApiPedido.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer id;
    private LocalDateTime fechaPedido;
    private Integer total;
    private String estado;
    private UsuarioDTO usuario;
    private List<ItemPedidoDTO> items;
}
