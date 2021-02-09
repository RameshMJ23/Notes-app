package com.example.notesapp;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;

public class Adapter extends FirestoreRecyclerAdapter<notes, Adapter.NotesViewHolder> {

    NoteListerner noteListerner;

    public Adapter(@NonNull FirestoreRecyclerOptions<notes> options, NoteListerner noteListerner) {
        super(options);
        this.noteListerner = noteListerner;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotesViewHolder holder, int position, @NonNull notes note) {
        holder.checker.setChecked(note.getisCOmpleted());
        holder.titleText.setText(note.getText());
        CharSequence dateChar = DateFormat.format("EEEE, MMM d,yyyy h:mm:ss a",note.getCreated().toDate());
        holder.creationText.setText(dateChar);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notes_row, parent,false);
        return new NotesViewHolder(view);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView titleText, creationText;
        CheckBox checker;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.note_title);
            creationText = itemView.findViewById(R.id.date_time);
            checker = itemView.findViewById(R.id.checkBox);
            checker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());
                    notes Notes = getItem(getAdapterPosition());
                    if(Notes.getisCOmpleted() != isChecked){
                        noteListerner.handleCheckChanged(isChecked, snapshot);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());
                    noteListerner.handleEditNote(snapshot);
                }

            });
        }
        public void deleteItem() {
            noteListerner.handleDeleteItem(getSnapshots().getSnapshot(getAdapterPosition()));
        }
    }

    interface NoteListerner{
        public void handleCheckChanged(boolean isChecked, DocumentSnapshot snapshot);
        public void handleEditNote(final DocumentSnapshot snapshot);
        public void handleDeleteItem(DocumentSnapshot snapshot);
    }
}
