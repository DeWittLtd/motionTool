package ltd.vblago.motiontool.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import ltd.vblago.motiontool.models.NoteModel;
import ltd.vblago.motiontool.models.Position;
import ltd.vblago.motiontool.sensors.Orientation;

public class WriteNote implements OrientationSensorInterface {

    private MyCallback callback;
    private int time;
    private static final int START = 1;
    private boolean isStarted;
    private Position currentPosition;
    private int primordialAzimuth;
    public ArrayList<Position> positions;
    private Context context;

    public WriteNote(Context context) {
        time = 0;
        this.context = context;
        positions = new ArrayList<>(20);
        currentPosition = new Position();

        Orientation orientationSensor = new Orientation(this.context, this);
        orientationSensor.init(1.0, 1.0, 1.0);

        // set output speed and turn initialized sensor on
        // 0 Normal
        // 1 UI
        // 2 GAME
        // 3 FASTEST
        orientationSensor.on(3);

        // turn orientation sensor off
        //orientationSensor.off();

        // return true or false
        //Toast.makeText(this, String.valueOf(orientationSensor.isSupport()), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what == START) {
                if (primordialAzimuth == -1){
                    primordialAzimuth = currentPosition.azimuth;
                }
                time += 1;
                positions.add(currentPosition.getCopyForDb(primordialAzimuth));
                h.sendEmptyMessageDelayed(START, 100);
            }
        }
    };

    public void setCallback(MyCallback callback) {
        this.callback = callback;
    }

    public void startStopwatch() {
        primordialAzimuth = -1;
        isStarted = true;
        h.sendEmptyMessage(START);
    }

    public void stopStopwatch() {
//        if (isStarted) {
//            stopStopwatch();
//        }
        isStarted = false;
        h.removeMessages(START);
        callback.showProgress(new NoteModel(positions, time));
        time = 0;
        positions = new ArrayList<>(20);
    }

    @Override
    public void orientation(Double azimuth, Double pitch, Double roll) {
        currentPosition.setAzimuth(azimuth);
        currentPosition.setPitch(pitch);
        currentPosition.setRoll(roll);
    }

    public interface MyCallback {
        void showProgress(NoteModel noteModel);
    }
}