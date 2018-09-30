package ltd.vblago.motiontool.models;

public class Position {
    public int azimuth;
    public int pitch;
    public int roll;

    public Position(int azimuth, int pitch, int roll) {
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.roll = roll;
    }

    public Position() {
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = (int) azimuth;
    }

    public void setPitch(double pitch) {
        this.pitch = (int) pitch;
    }

    public void setRoll(double roll) {
        this.roll = (int) roll;
    }

    public Position getCopy() {
        return new Position(azimuth, pitch, roll);
    }

    public Position getCopyForDb(int primordialAzimuth) {
        primordialAzimuth = azimuth-primordialAzimuth;
        if (primordialAzimuth < 0){
            primordialAzimuth += 360;
        }
        return new Position(primordialAzimuth, pitch, roll);
    }
}
