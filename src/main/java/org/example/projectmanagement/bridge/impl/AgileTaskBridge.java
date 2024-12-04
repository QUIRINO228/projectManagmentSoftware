package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.bridge.AbstractTaskBridge;
import org.example.projectmanagement.models.enums.AgileStatus;

public class AgileTaskBridge extends AbstractTaskBridge {

    public AgileTaskBridge() {
        this.methodology = "Agile";
    }

    @Override
    protected String getInitialStatus() {
        return AgileStatus.NEW.name();
    }
}