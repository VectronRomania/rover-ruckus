package org.firstinspires.ftc.teamcode.systems.autonomous;

import java.util.ArrayList;

public class CheckableGroup implements Checkable {

    private ArrayList<Checkable> checkables;

    private Boolean result;

    private CheckableLogicalOperation operation;

    public CheckableGroup(CheckableLogicalOperation operation) {
        this.operation = operation;
    }

    @Override
    public Boolean check() {
        for (Checkable checkable : checkables) {
            result = BackgroundChecker.evaluateCheckable(checkable, result);
        }
        return result;
    }

    @Override
    public CheckableLogicalOperation getOperation() {
        return operation;
    }

    public CheckableGroup add(Checkable checkable) {
        checkables.add(checkable);
        return this;
    }
}
