package ltd.vblago.motiontool.models;

import java.util.List;

public class NoteModel {
    public List<Position> positions;
    public int duration;

    public NoteModel(List<Position> positions, int duration, int downtime) {
        this.positions = positions;
        this.duration = duration;
        for (int i = 0; i < downtime; i++) {
            positions.remove(positions.size()-1);
        }
    }
}
