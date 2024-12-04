package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.bridge.AbstractTaskBridge;
import org.example.projectmanagement.models.enums.RUPPhase;

public class RupTaskBridge extends AbstractTaskBridge {

    public RupTaskBridge() {
        this.methodology = "RUP";
    }

    @Override
    protected String getInitialStatus() {
        return RUPPhase.INCEPTION.name();
    }
}