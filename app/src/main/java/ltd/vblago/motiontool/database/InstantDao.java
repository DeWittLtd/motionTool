package ltd.vblago.motiontool.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ltd.vblago.motiontool.database.entity.Instant;

@Dao
public interface InstantDao {
    @Query("SELECT * FROM instant")
    List<Instant> getAllInstants();

    @Query("SELECT * FROM instant WHERE uid LIKE :uid LIMIT 1")
    Instant getInstantById(int uid);

    @Insert
    long insert(Instant instant);

    @Insert
    void insert(List<Instant> instants);

    @Update
    void update(Instant instant);

    @Delete
    void delete(Instant instant);
}
