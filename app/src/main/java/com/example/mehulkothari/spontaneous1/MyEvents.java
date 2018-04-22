package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import CreateEventPack.CreateEventObject;

/**
 * Created by mehulkothari on 3/15/2017.
 */
public class MyEvents extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mPostReference;
    LinearLayoutManager mLayout;
    int flag;
    public DataSnapshot ds;
    ArrayList<String> user_key;
    ArrayList<String> user_key_1;
    ArrayList<CreateEventObject> createEventObjects;
    Map<String, CreateEventObject> createEventObjectHashMap=new LinkedHashMap<>();
// ...

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.myevents,
                container, false);


        return inflater.inflate(R.layout.myevents, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(), "entered1122", Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        user_key=new ArrayList<>();
        user_key_1=new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        Toast.makeText(getActivity(), "entered", Toast.LENGTH_SHORT).show();
        //layoutManager = new LinearLayoutManager(getActivity());
        createEventObjects = new ArrayList<CreateEventObject>();
        mLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayout);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences shared = getActivity().getSharedPreferences(SponsorStudent.PREFS_NAME, Context.MODE_PRIVATE);
        String channel = shared.getString("which_user", null);
        System.out.println(channel);
        final DatabaseReference cre=FirebaseDatabase.getInstance().getReference("CREATE_EVENT");
        if(channel.equals("student")) {

            flag=1;
            mPostReference = FirebaseDatabase.getInstance().getReference("CREATE_EVENT")
                    .child(user_1.getUid());
        }
        else{
            flag=0;
            mPostReference = FirebaseDatabase.getInstance().getReference("CREATE_EVENT");
           // ds=new DataSnapshot(FirebaseDatabase.getInstance().getReference("CREATE_EVENT"));
        }
        //Toast.makeText(getActivity(), "after reference", Toast.LENGTH_SHORT).show();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreateEventObject createEventObject;

                //Toast.makeText(getActivity(), "entered11", Toast.LENGTH_SHORT).show();
               /* Map<String, CreateEventObject> td = (HashMap<String, CreateEventObject>) dataSnapshot.getValue();
                //List<Object> values = (List<Object>) td.values();
                for (Map.Entry<String, CreateEventObject> entry : td.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue().getAbout_the_event());
                    createEventObjects.add(entry.getValue());
                    System.out.println(createEventObjects.toString());

                }

                System.out.println(createEventObjects.toString());
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();

*/
                for (final DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    if(flag==0)
                    {
                        DatabaseReference db1=cre.child(messageSnapshot.getKey());
                        cre.child(messageSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {

                                for(DataSnapshot db: dataSnapshot1.getChildren()){

                                    CreateEventObject message = db.getValue(CreateEventObject.class);
                                    System.out.println(message.getAbout_the_event());
                                    System.out.println();
                                    createEventObjects.add(message);
                                    createEventObjectHashMap.put(db.getKey(), message);
                                    user_key.add(db.getKey());
                                    user_key_1.add(messageSnapshot.getKey());

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else{
                        CreateEventObject message = messageSnapshot.getValue(CreateEventObject.class);
                        System.out.println(message.getAbout_the_event());
                        System.out.println();
                        createEventObjects.add(message);
                        createEventObjectHashMap.put(messageSnapshot.getKey(), message);
                        user_key_1.add(messageSnapshot.getKey());
                    }


                }

                // recyclerView.setItemAnimator(new DefaultItemAnimator());

                adapter = new CustomAdapter((LinkedHashMap<String, CreateEventObject>) createEventObjectHashMap,getActivity(),user_key,user_key_1,flag);

                //mLayout = new LinearLayoutManager(getActivity());
                //  recyclerView.setLayoutManager(mLayout);
                // here u have to add

                recyclerView.setAdapter(adapter);

                //adapter.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("good", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mPostReference.addValueEventListener(postListener);

    }
}
