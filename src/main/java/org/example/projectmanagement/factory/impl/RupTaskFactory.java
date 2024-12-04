package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.models.enums.RUPPhase;

public class RupTaskFactory extends AbstractTaskFactory {

    public RupTaskFactory() {
        this.methodology = "RUP";
    }

    @Override
    protected String getInitialStatus() {
        return RUPPhase.INCEPTION.name();
    }
}