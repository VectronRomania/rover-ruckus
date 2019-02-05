package org.firstinspires.ftc.teamcode.systems.util;

import android.util.Pair;

import java.util.ArrayList;

public class CheckableGroup implements Checkable {

    protected ArrayList<Pair<Checkable, CheckableLogicalOperation>> items;

    private Boolean result;

    public CheckableGroup() {
        this.items = new ArrayList<>();
    }

    @Override
    public synchronized Boolean check() {
        for (Pair<Checkable, CheckableLogicalOperation> checkable : items) {
            if (result == null) {
                result = checkable.first.check();
                continue;
            }
            result = checkable.second == CheckableLogicalOperation.AND ?
                    result && checkable.first.check() :
                    result || checkable.first.check();
        }
        return result;
    }

    public synchronized CheckableGroup add(Checkable checkable, CheckableLogicalOperation operation) {
        items.add(new Pair<>(checkable, operation));
        return this;
    }
}
