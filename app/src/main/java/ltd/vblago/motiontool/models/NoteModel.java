package ltd.vblago.motiontool.models;

import java.util.List;

public class NoteModel {
    public List<Position> positions;
    public int duration;

    public NoteModel(List<Position> positions, int duration) {
        this.positions = positions;
        this.duration = duration;
    }
}
