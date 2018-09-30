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
    private static final int DOWNTIME = 10; // downtime in 0.1 second
    private static final int INITIAL_CAPACITY = 20;
    private boolean isStarted;
    private Position currentPosition;
    private int primordialAzimuth;
    private int stopCounter;
    public ArrayList<Position> positions;
    private Context context;

    public WriteNote(Context context) {
        time = 0;
        stopCounter = 0;
        this.context = context;
        positions = new ArrayList<>(INITIAL_CAPACITY);
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
                if (currentPosition.pitch>-16 && currentPosition.pitch<16 && currentPosition.roll>-21 && currentPosition.roll<16){
                    if (isStarted){
                        if (stopCounter > DOWNTIME){
                            stopStopwatch();
                        }
                        if (stopCounter == 0){
                            callback.showStateOfWritingNote(0);
                        }
                            stopCounter++;
                    }else {
                        h.sendEmptyMessageDelayed(START, 100);
                    }
                }
                time += 1;
                stopCounter = 0;
                positions.add(currentPosition.getCopyForDb(primordialAzimuth));
                if (!isStarted){
                    isStarted = true;
                    primordialAzimuth = currentPosition.azimuth;
                    callback.showStateOfWritingNote(1);
                }
                h.sendEmptyMessageDelayed(START, 100);
            }
        }
    };

    public void setCallback(MyCallback callback) {
        this.callback = callback;
    }

    public void startStopwatch() {
        primordialAzimuth = -1;
        stopCounter = 0;
        h.sendEmptyMessage(START);
    }

    public void stopStopwatch() {
//        if (isStarted) {
//            stopStopwatch();
//        }
        isStarted = false;
        h.removeMessages(START);
        callback.showProgress(new NoteModel(positions, time, DOWNTIME));
        time = 0;
        positions = new ArrayList<>(INITIAL_CAPACITY);
    }

    @Override
    public void orientation(Double azimuth, Double pitch, Double roll) {
        currentPosition.setAzimuth(azimuth);
        currentPosition.setPitch(pitch);
        currentPosition.setRoll(roll);
    }

    public interface MyCallback {
        void showProgress(NoteModel noteModel);
        void showStateOfWritingNote(int state);
    }
}