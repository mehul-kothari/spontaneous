package com.example.mehulkothari.spontaneous1;

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

import All_Users.Users;
import CreateEventPack.CreateEventObject;

/**
 * Created by mehulkothari on 3/15/2017.
 */
public class SponsorsList extends Fragment {

    ArrayList<Users> sponsors;
    ArrayList<String> user_key;
    private static RecyclerView.Adapter adapter;
    LinearLayoutManager mLayout;
    private DatabaseReference mPostReference;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.list_sponsors,
                container, false);


        return inflater.inflate(R.layout.list_sponsors, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView rc1=(RecyclerView)view.findViewById(R.id.rc1);
        sponsors = new ArrayList();
        user_key = new ArrayList();
        mLayout = new LinearLayoutManager(getActivity());
        rc1.setLayoutManager(mLayout);
        rc1.setItemAnimator(new DefaultItemAnimator());
        FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();
        mPostReference = FirebaseDatabase.getInstance().getReference("users")
                .child("sponsor");

        Toast.makeText(getActivity(), "after reference", Toast.LENGTH_SHORT).show();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Users user_info = messageSnapshot.getValue(Users.class);
                    System.out.println(user_info.getName());
                    System.out.println();
                    sponsors.add( user_info);
                    user_key.add(messageSnapshot.getKey());
                    //createEventObjectHashMap.put(messageSnapshot.getKey(),message);

                }

                // recyclerView.setItemAnimator(new DefaultItemAnimator());


                adapter = new SponsorAdapter(getActivity(),sponsors,user_key,"spon");

                //mLayout = new LinearLayoutManager(getActivity());
                //  recyclerView.setLayoutManager(mLayout);
                // here u have to add

                rc1.setAdapter(adapter);

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
