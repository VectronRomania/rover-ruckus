package org.firstinspires.ftc.teamcode.systems.util.checkables;

import android.util.Pair;

import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;

public class DrivetrainCheckableGroup extends CheckableGroup {

    public DrivetrainCheckableGroup(Checkable a, Checkable b, Checkable c, Checkable d) {
        items.add(new Pair<>(a, Operation.AND));
        items.add(new Pair<>(b, Operation.AND));
        items.add(new Pair<>(c, Operation.AND));
        items.add(new Pair<>(d, Operation.AND));
    }
}
