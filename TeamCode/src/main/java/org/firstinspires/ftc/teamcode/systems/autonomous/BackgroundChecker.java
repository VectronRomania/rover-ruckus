package org.firstinspires.ftc.teamcode.systems.autonomous;

import java.util.ArrayList;

public class BackgroundChecker {

    private ArrayList<Checkable> checkables;

    private Integer timeout;

    public BackgroundChecker() {

    }

    public static Boolean evaluateCheckable(Checkable checkable, Boolean previous) {
        if (previous == null) {
            return checkable.check();
        }
        switch (checkable.getOperation()) {
            case AND:
                return previous && checkable.check();
            case OR:
                return previous && checkable.check();
            default:
                return null;
        }
    }

    public void add(Checkable checkable) {
        checkable.check();
    }
}
