package org.firstinspires.ftc.teamcode.systems.util;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.ArrayList;

/**
 * CheckableGroup is a group of Checkables, evaluated as a group.
 */
public class CheckableGroup implements Checkable {

    /**
     * Logical operation for evaluating Checkables.
     */
    public enum Operation {
        AND,
        OR
    }

    /**
     * The items of this group.
     */
    protected ArrayList<Pair<Checkable, Operation>> items;

    /**
     * The result of the last evaluation.
     */
    private Boolean result;

    public CheckableGroup() {
        this.items = new ArrayList<>();
    }

    /**
     * Evaluate all Checkables and return the result.
     * @return
     */
    @NonNull
    @Override
    public synchronized Boolean check() {
        for (Pair<Checkable, Operation> checkable : items) {
            if (result == null) {
                result = checkable.first.check();
                continue;
            }
            result = checkable.second == Operation.AND ?
                    result && checkable.first.check() :
                    result || checkable.first.check();
        }
        return result;
    }

    /**
     * Add a new Checkable to the group.
     * @param checkable
     * @param operation
     * @return
     */
    @NonNull
    public synchronized CheckableGroup add(@NonNull Checkable checkable, @NonNull Operation operation) {
        items.add(new Pair<>(checkable, operation));
        return this;
    }
}
