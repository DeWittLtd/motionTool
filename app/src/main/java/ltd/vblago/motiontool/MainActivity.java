package ltd.vblago.motiontool;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ltd.vblago.motiontool.database.entity.Instant;
import ltd.vblago.motiontool.database.entity.Note;
import ltd.vblago.motiontool.models.NoteModel;
import ltd.vblago.motiontool.models.Position;
import ltd.vblago.motiontool.utils.SequenceFormation;
import ltd.vblago.motiontool.utils.WriteNote;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.azimuth) TextView azimuthTv;
    @BindView(R.id.pitch) TextView pitchTv;
    @BindView(R.id.roll) TextView rollTv;
    @BindView(R.id.startStopBtn) Button startStopBtn;

    WriteNote writeNote;
    boolean startEntry, savedNote;
    NoteModel note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        startEntry = false;
        writeNote = new WriteNote(this.getApplicationContext());

        writeNote.setCallback(new WriteNote.MyCallback() {
            @Override
            public void showProgress(NoteModel noteModel) {
                note = noteModel;
                savedNote = true;
            }

            @Override
            public void showStateOfWritingNote(int state) {
                // changing color of background
            }
        });

        String currentDBPath = getDatabasePath("NoteDb").getAbsolutePath();
        Toast.makeText(this, DebugDB.getAddressLog(), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.startStopBtn)
    public void startStopEntry(){
        if (!startEntry){
            writeNote.startStopwatch();
            startStopBtn.setText(R.string.stop_btn);
        }else {
            writeNote.stopStopwatch();
            startStopBtn.setText(R.string.start_stop_btn);
        }
        startEntry = !startEntry;
    }

    @OnClick(R.id.saveBtn)
    public void saveEntry(){
        if (savedNote){
            new InsertAsync().execute(note);
            savedNote = false;
        }
    }

    private class InsertAsync extends AsyncTask<NoteModel, Void, Void> {

        @Override
        protected Void doInBackground(NoteModel... noteModels) {

            long uid = App.get().getNoteDatabase().noteDao().insert(new Note(noteModels[0].duration));
            List<Instant> instants = new ArrayList<>(noteModels[0].positions.size());
            List<Position> formatPositions = SequenceFormation.formation(noteModels[0].positions, noteModels[0].positions.size());
            int i = 1;
            for (Position position : formatPositions) {
                instants.add(new Instant((int) uid, i, position.azimuth, position.pitch, position.roll));
                i++;
            }
            App.get().getNoteDatabase().instantDao().insert(instants);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
