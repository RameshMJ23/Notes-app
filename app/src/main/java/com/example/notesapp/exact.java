package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class exact extends AppCompatActivity {

    private static final String TAG = "exact";

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exact);
    }

    public void create(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("text","i want to watch ravanan");
        map.put("isCompleted", false);
        map.put("created", new Timestamp(new Date()));
        product p = new product("samsung", 123,true);


        firestore.collection("users")
                .add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: created successfully " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: creation failed");
                    }
                });
    }

    public void readDoc(View view) {
        FirebaseFirestore.getInstance().collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: data got"  );
                        List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snap: snapshotsList){
                            Log.d(TAG, "onSuccess: " + snap.getData());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: data not retreved");
                    }
                });
    }

    public void updateDoc(View view) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("products")
                .document("EHbmil22BIBF1UKZl9G3");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name","iphone");
        map2.put("isAvailable", FieldValue.delete());
        map2.put("price", 199);

        docRef.update(map2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: value updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: update failed");
                    }
                });

        /*docRef.set(map2, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: value updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: update failed");
                    }
                });*/
    }

    public void deleteDoc(View view) {
        FirebaseFirestore.getInstance().collection("products")
                .document("EHbmil22BIBF1UKZl9G3")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: not deleted");
                    }
                });

        /*FirebaseFirestore.getInstance().collection("products")
                .whereEqualTo("isAvailable",true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        
                        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snaps : snapshots ){
                            batch.delete(snaps.getReference());
                        }
                        
                        batch.commit()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: all documents deleted");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: deletion failed");
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        
                    }
                });*/
    }

    public void getDoc(View view) {
        FirebaseFirestore.getInstance().collection("products")
                .orderBy("isAvailable", Query.Direction.DESCENDING)
                .limit(2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: data got"  );
                        List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snap: snapshotsList){
                            Log.d(TAG, "onSuccess: " + snap.getData());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: data not retreved");
                    }
                });
    }

    public void realTimeGetDoc(View view) {
        FirebaseFirestore.getInstance().collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e(TAG, "onEvent: "+ error );
                            return;
                        }
                        if(value != null){
                            Log.d(TAG, "onEvent: -------------------------");
                            List<DocumentSnapshot> snapshotList = value.getDocuments();

                           /* for(DocumentSnapshot snap : snapshotList){
                                Log.d(TAG, "onEvent: " + snap.getData());
                            }*/
                            List<DocumentChange> documentChangeList = value.getDocumentChanges();

                            for (DocumentChange documentChange: documentChangeList){
                                Log.d(TAG, "onEvent: " + documentChange.getDocument().getData());
                            }

                        }else{
                            Log.e(TAG, "onEvent:" + error );
                        }
                    }
                });
    }
}
