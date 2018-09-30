package ltd.vblago.motiontool.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "duration")
    private int duration;

    public int getUid() {
        return uid;
    }

    public int getDuration() {
        return duration;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Note() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeInt(this.duration);
    }

    protected Note(Parcel in) {
        this.uid = in.readInt();
        this.duration = in.readInt();
    }

    public Note(int duration) {
        this.duration = duration;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
