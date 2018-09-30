package ltd.vblago.motiontool.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ltd.vblago.motiontool.database.entity.Instant;
import ltd.vblago.motiontool.database.entity.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE uid LIKE :uid LIMIT 1")
    Note getNoteById(int uid);

    @Insert
    long insert(Note note);

    @Insert
    void insert(List<Instant> instants);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
