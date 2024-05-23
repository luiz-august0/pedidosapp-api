package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.service.AbstractService;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
    public abstract String getPortugueseClassName();

    public abstract Class<? extends AbstractService> getServiceClass();

    public String getObjectName() {
        return "";
    }

}
