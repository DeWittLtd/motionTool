package ltd.vblago.motiontool.utils;

import java.util.ArrayList;
import java.util.List;

import ltd.vblago.motiontool.models.Position;

public class SequenceFormation {

    public static List<Position> formation(List<Position> positions, int size){
        List<Position> formatPositions = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            formatPositions.add(positions.get((int) (size*0.05*i)));
        }
        return formatPositions;
    }
}
