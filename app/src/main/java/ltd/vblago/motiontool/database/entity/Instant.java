package ltd.vblago.motiontool.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Instant implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "id_note")
    private int id_note;

    @ColumnInfo(name = "instant_number")
    private int instant_number;

    @ColumnInfo(name = "azimuth")
    private int azimuth;

    @ColumnInfo(name = "pitch")
    private int pitch;

    @ColumnInfo(name = "roll")
    private int roll;

    public int getUid() {
        return uid;
    }

    public int getId_note() {
        return id_note;
    }

    public int getInstant_number() {
        return instant_number;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public int getPitch() {
        return pitch;
    }

    public int getRoll() {
        return roll;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setId_note(int id_note) {
        this.id_note = id_note;
    }

    public void setInstant_number(int instant_number) {
        this.instant_number = instant_number;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeInt(this.id_note);
        dest.writeInt(this.instant_number);
        dest.writeInt(this.azimuth);
        dest.writeInt(this.pitch);
        dest.writeInt(this.roll);
    }

    protected Instant(Parcel in) {
        this.uid = in.readInt();
        this.id_note = in.readInt();
        this.instant_number = in.readInt();
        this.azimuth = in.readInt();
        this.pitch = in.readInt();
        this.roll = in.readInt();
    }

    public Instant(int id_note, int instant_number, int azimuth, int pitch, int roll) {
        this.id_note = id_note;
        this.instant_number = instant_number;
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.roll = roll;
    }

    public static final Creator<Instant> CREATOR = new Creator<Instant>() {
        @Override
        public Instant createFromParcel(Parcel source) {
            return new Instant(source);
        }

        @Override
        public Instant[] newArray(int size) {
            return new Instant[size];
        }
    };
}
