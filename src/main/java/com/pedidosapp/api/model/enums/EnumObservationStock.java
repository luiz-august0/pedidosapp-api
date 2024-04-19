package com.pedidosapp.api.model.enums;

public enum EnumObservationStock {
    PURCHASE_ORDER("Movimento gerado pelo pedido de compra"),
    ORDER("Movimento gerado pelo pedido de venda");

    private String observation;

    EnumObservationStock(String observation) {
        this.observation = observation;
    }

    public String getObservation() {
        return observation;
    }
}
