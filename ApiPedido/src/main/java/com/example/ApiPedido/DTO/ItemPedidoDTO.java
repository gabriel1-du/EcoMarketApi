package com.example.ApiPedido.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

    private Integer productoId;
    private Integer cantidad;
    private Integer precioUnitario;
}
