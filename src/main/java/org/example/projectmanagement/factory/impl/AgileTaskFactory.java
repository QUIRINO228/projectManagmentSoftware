package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.models.enums.AgileStatus;

public class AgileTaskFactory extends AbstractTaskFactory {

    public AgileTaskFactory() {
        this.methodology = "Agile";
    }

    @Override
    protected String getInitialStatus() {
        return AgileStatus.NEW.name();
    }
}