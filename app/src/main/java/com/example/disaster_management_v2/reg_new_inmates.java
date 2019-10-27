package com.example.disaster_management_v2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class reg_new_inmates extends Fragment{


    public EditText name,afname,aadhar,mobile,gender;

    EditText totcount;

    EditText namea[]=new EditText[10];
    EditText agea[]=new EditText[10];
    EditText gendera[]=new EditText[10];
    int count;
    public LinearLayout myLayout;
    public Button add,savebtn;
    DatabaseReference mref,mreg;
    FirebaseAuth mauth;
    long maxid;

    String n,a,ph,g;

    private static final String TAG = "info" ;
    final int c=3;
    List<EditText> allEds = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_reg_new_inmates,null);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add= view.findViewById(R.id.addbtn);
        savebtn=view.findViewById(R.id.save);
        mref= FirebaseDatabase.getInstance().getReference().child("Affected_People");

        afname=view.findViewById(R.id.affectname);
        aadhar=view.findViewById(R.id.aadhar);
        mobile=view.findViewById(R.id.mobile);
        gender=view.findViewById(R.id.gender_new_inmate);
        mreg=FirebaseDatabase.getInstance().getReference().child("Affected_People").child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("OtherFamilyMembers");
        mauth=FirebaseAuth.getInstance();
        n=afname.getText().toString();
        a=aadhar.getText().toString();
        ph=mobile.getText().toString();
        g=gender.getText().toString();
        totcount=view.findViewById(R.id.totaleditreq);


        myLayout = (LinearLayout)getView().findViewById(R.id.myLayout);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mreg.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            maxid=dataSnapshot.getChildrenCount();
                            Log.i(TAG,"on save btn clicked "+maxid);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // if(!(totcount.getText().toString().isEmpty())) {

                if (afname.getText().length()==0) {
                    afname.setError("Please Enter Your Name");
                    afname.requestFocus();

                } else if (aadhar.getText().length()==0) {
                    aadhar.setError("Please Enter Aadhar UID");
                    aadhar.requestFocus();

                } else if (aadhar.getText().length() != 12) {
                    aadhar.setError("Aadhar number should be 12 digits!!!");
                    aadhar.requestFocus();
                } else if (mobile.getText().length()==0) {
                    mobile.setError("Please Enter your phone number!!!");
                    mobile.requestFocus();
                } else if (mobile.getText().length() != 10) {
                    mobile.setError("Phone number should be 10 digits!!!");
                    mobile.requestFocus();
                } else if (gender.getText().length()==0) {
                    gender.setError("Enter Your Gender!!");
                    gender.requestFocus();
                } else {

                    count = Integer.valueOf(totcount.getText().toString());
                    mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Name").setValue(afname.getText().toString());
                    mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Gender").setValue(gender.getText().toString());
                    mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("PhoneNo").setValue(mobile.getText().toString());
                    for (int i = 0; i < count; i++) {
                        namea[i] =new EditText(getActivity());
                        gendera[i] = new EditText(getActivity());
                        agea[i] = new EditText(getActivity());
                        myLayout = (LinearLayout)view.findViewById(R.id.myLayout);
                        namea[i].setHint("Enter the name of the family member");

                        gendera[i].setHint("Enter Gender of the Members");
                        agea[i].setHint("Enter the age");
                        namea[i].setId(i);
                        gendera[i].setId(i);
                        agea[i].setId(i);

                        LinearLayout.LayoutParams abc = new LinearLayout.LayoutParams(

                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        myLayout.addView(namea[i], abc);
                        myLayout.addView(gendera[i], abc);
                        myLayout.addView(agea[i], abc);

                    }
                }
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < count; i++) {
                    if (namea[i].getText().toString().isEmpty() && gendera[i].getText().toString().isEmpty() && agea[i].getText().toString().isEmpty()) {
                        namea[i].setError("Please Enter name!!");
                        namea[i].requestFocus();
                        gendera[i].setError("Please Enter gender here!!!");
                        agea[i].setError("Please Enter age here!!");
                        Toast.makeText(getContext(), "All Fields are necessary!!!", Toast.LENGTH_LONG).show();
                    } else if (namea[i].getText().length()==0) {
                        namea[i].setError("Please Enter Your Name!!");
                        namea[i].requestFocus();

                    } else if (gendera[i].getText().length()==0) {
                        gendera[i].setError("Please Enter your Gender!!!");
                        gendera[i].requestFocus();
                    } else if (agea[i].getText().length()==0) {
                        agea[i].setError("Enter Your Age!!");
                        agea[i].requestFocus();
                    } else {

                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Name").setValue(namea[i].getText().toString());
                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Gender").setValue(gendera[i].getText().toString());
                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Age").setValue(agea[i].getText().toString());
                        Intent a = new Intent(getActivity(), MainActivity.class);
                        startActivity(a);
                    }
                }
            }
        });
    }






    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);




//        add= findViewById(R.id.addbtn);
//        savebtn=findViewById(R.id.save);
//        mref= FirebaseDatabase.getInstance().getReference().child("Affected_People");
//
//        afname=findViewById(R.id.affectname);
//        aadhar=findViewById(R.id.aadhar);
//        mobile=findViewById(R.id.mobile);
//        gender=findViewById(R.id.gender_new_inmate);
//        mreg=FirebaseDatabase.getInstance().getReference().child("Affected_People").child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("OtherFamilyMembers");
//        mauth=FirebaseAuth.getInstance();
//        n=afname.getText().toString();
//        a=aadhar.getText().toString();
//        ph=mobile.getText().toString();
//        g=gender.getText().toString();
//
//
//        myLayout = (LinearLayout)findViewById(R.id.myLayout);
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mreg.addValueEventListener(new ValueEventListener() {
//
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists())
//                        {
//                            maxid=dataSnapshot.getChildrenCount();
//                            Log.i(TAG,"on save btn clicked "+maxid);
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//              // if(!(totcount.getText().toString().isEmpty())) {
//
//                    if (afname.getText().length()==0) {
//                        afname.setError("Please Enter Your Name");
//                        afname.requestFocus();
//
//                    } else if (aadhar.getText().length()==0) {
//                        aadhar.setError("Please Enter Aadhar UID");
//                        aadhar.requestFocus();
//
//                    } else if (aadhar.getText().length() != 12) {
//                        aadhar.setError("Aadhar number should be 12 digits!!!");
//                        aadhar.requestFocus();
//                    } else if (mobile.getText().length()==0) {
//                        mobile.setError("Please Enter your phone number!!!");
//                        mobile.requestFocus();
//                    } else if (mobile.getText().length() != 10) {
//                        mobile.setError("Phone number should be 10 digits!!!");
//                        mobile.requestFocus();
//                    } else if (gender.getText().length()==0) {
//                        gender.setError("Enter Your Gender!!");
//                        gender.requestFocus();
//                    } else {
//
//                        count = Integer.valueOf(totcount.getText().toString());
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Name").setValue(afname.getText().toString());
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Gender").setValue(gender.getText().toString());
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("PhoneNo").setValue(mobile.getText().toString());
//                        for (int i = 0; i < count; i++) {
//                            namea[i] = new EditText(reg_new_inmates.this);
//                            gendera[i] = new EditText(reg_new_inmates.this);
//                            agea[i] = new EditText(reg_new_inmates.this);
//                            myLayout = (LinearLayout) findViewById(R.id.myLayout);
//                            namea[i].setHint("Enter the name of the family member");
//
//                            gendera[i].setHint("Enter Gender of the Members");
//                            agea[i].setHint("Enter the age");
//                            namea[i].setId(i);
//                            gendera[i].setId(i);
//                            agea[i].setId(i);
//
//                            LinearLayout.LayoutParams abc = new LinearLayout.LayoutParams(
//
//                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                    LinearLayout.LayoutParams.WRAP_CONTENT
//                            );
//
//                            myLayout.addView(namea[i], abc);
//                            myLayout.addView(gendera[i], abc);
//                            myLayout.addView(agea[i], abc);
//
//                        }
//                    }
//            }
//        });
//        savebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                for (int i = 0; i < count; i++) {
//                    if (namea[i].getText().toString().isEmpty() && gendera[i].getText().toString().isEmpty() && agea[i].getText().toString().isEmpty()) {
//                        namea[i].setError("Please Enter name!!");
//                        namea[i].requestFocus();
//                        gendera[i].setError("Please Enter gender here!!!");
//                        agea[i].setError("Please Enter age here!!");
//                        Toast.makeText(reg_new_inmates.this, "All Fields are necessary!!!", Toast.LENGTH_LONG).show();
//                    } else if (namea[i].getText().length()==0) {
//                        namea[i].setError("Please Enter Your Name!!");
//                        namea[i].requestFocus();
//
//                    } else if (gendera[i].getText().length()==0) {
//                        gendera[i].setError("Please Enter your Gender!!!");
//                        gendera[i].requestFocus();
//                    } else if (agea[i].getText().length()==0) {
//                        agea[i].setError("Enter Your Age!!");
//                        agea[i].requestFocus();
//                    } else {
//
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Name").setValue(namea[i].getText().toString());
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Gender").setValue(gendera[i].getText().toString());
//                        mref.child(mauth.getInstance().getCurrentUser().getUid()).child(aadhar.getText().toString()).child("Other Family Members").child(String.valueOf(i)).child("Age").setValue(agea[i].getText().toString());
//                        Intent a = new Intent(reg_new_inmates.this, MainActivity.class);
//                        startActivity(a);
//                    }
//                }
//            }
//        });
//    }



