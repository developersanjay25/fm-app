package com.example.fmapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class info extends Fragment {
    private RecyclerView recyclerView1,recyclerView2;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2;
    private ArrayList<upload2> arrayList1;
    adapter2 adapter2;
    adapter adapter1;
    ProgressBar progressBar;
    private ArrayList<examplecode> arrayList2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_fragment,null,false);




//        layoutManager1 = ;

       recyclerView1 = v.findViewById(R.id.recyclerviewlive);
       recyclerView2 = v.findViewById(R.id.recyclerview2);
       progressBar = v.findViewById(R.id.prog_info);

        adapter2 = new adapter2(arrayList1);
        recyclerView1.setAdapter(adapter2);

        recyclerview2();
        recyclerview1();

        FirebaseDatabase.getInstance().getReference("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList1.clear();
                for (DataSnapshot msnapshot: snapshot.getChildren()) {
                    upload2 upload2 =  msnapshot.getValue(upload2.class);
                    arrayList1.add(upload2);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    public void recyclerview1()
    {
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        arrayList1 = new ArrayList<>();

    }
    public void recyclerview2()
    {
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList2 = new ArrayList<>();
        arrayList2.add(new examplecode(R.drawable.img,"Manikandan","Worked on king tv","20+ live shows"));
        arrayList2.add(new examplecode(R.drawable.img,"Arun","Worked on king tv","20+ live shows"));
        adapter1 = new adapter(arrayList2);
        recyclerView2.setAdapter(adapter1);
    }
}
