package com.taomish.app.android.farmsanta.farmer.nutrisource;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.nutriadapter.SoilTestAdapter;
import com.taomish.app.android.farmsanta.farmer.datamodel.MyorderModel;
import com.taomish.app.android.farmsanta.farmer.helper.AppPrefs;

import java.util.ArrayList;

public class ReportFregmant  extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    ArrayList<MyorderModel> orderItemList;
    SoilTestAdapter adapter ;
    private FirebaseAuth mAuth;
    private String CurrentUser;
    private DatabaseReference m , root;
    public static Activity fa;
    public ReportFregmant() {
        // Required empty public constructor
    }
    private RecyclerView OrderItemRecyclerView;
    public static ReportFregmant newInstance(String param1, String param2) {
        ReportFregmant fragment = new ReportFregmant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_fregmant, container, false);
        fa= getActivity();
        //  mAuth=FirebaseAuth.getInstance();
        // CurrentUser = mAuth.getCurrentUser().getUid();
        AppPrefs appPrefs=new AppPrefs(getContext());
        CurrentUser=appPrefs.getPhoneNumber();

        OrderItemRecyclerView =  view.findViewById(R.id.orderrecycler);
        orderItemList = new ArrayList<MyorderModel>();
        adapter = new SoilTestAdapter(getActivity(),orderItemList);

        OrderItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderItemRecyclerView.setAdapter(adapter);

        CurrentUser=appPrefs.getPhoneNumber();
        DatabaseReference roott= FirebaseDatabase.getInstance("https://farmsanta-partners-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        DatabaseReference x = roott.child("soiltestbook").orderByChild("mobile_no").equalTo(CurrentUser).getRef();;
        ValueEventListener valueEventListener1 =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String Date = dataSnapshot.child("createdate").getValue().toString();
                        int nums = 1;
                        String totalPrice = "200";
                        String OrderCheck = dataSnapshot.child("status").getValue().toString();
                        String name = dataSnapshot.child("labname").getValue().toString();
                        String contact = dataSnapshot.child("mobile_no").getValue().toString();
                        String address =dataSnapshot.child("village").getValue().toString()+","+dataSnapshot.child("pincodeno").getValue().toString();
                        String orderid=dataSnapshot.child("sampleid").getValue().toString();
                        String products="Booking :\n";
                        for (DataSnapshot data : dataSnapshot.child("orderproducts").getChildren())
                        {
                            products+= "    #"+data.getKey() + "\n        Crop Name: " + data.child("crop_name").getValue().toString() + "\n        Farm Size: " + data.child("farm_size").getValue().toString()+"\n";
                        }

                        orderItemList.add( new MyorderModel(dataSnapshot.getKey(),"   Date :  " + Date ,"   Sample Number :  "+orderid,"   Total Price :  "+ totalPrice+" INR" , "   "+products,OrderCheck,name,contact,address));
                    }
                }
                else{
                    orderItemList.clear();
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        x.addListenerForSingleValueEvent(valueEventListener1);

        return view;
    }
}
