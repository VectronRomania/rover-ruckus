package org.firstinspires.ftc.teamcode.systems.util.checkables;

import android.util.Pair;

import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.CheckableLogicalOperation;

public class DrivetrainEncoderCheckableGroup extends CheckableGroup {

    public DrivetrainEncoderCheckableGroup(Checkable a, Checkable b, Checkable c, Checkable d) {
        items.add(new Pair<>(a, CheckableLogicalOperation.AND));
        items.add(new Pair<>(b, CheckableLogicalOperation.AND));
        items.add(new Pair<>(c, CheckableLogicalOperation.AND));
        items.add(new Pair<>(d, CheckableLogicalOperation.AND));
    }
}
