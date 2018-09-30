package ltd.vblago.motiontool.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ltd.vblago.motiontool.database.entity.Instant;
import ltd.vblago.motiontool.database.entity.Note;

@Database(entities = {Note.class, Instant.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract InstantDao instantDao();
}
