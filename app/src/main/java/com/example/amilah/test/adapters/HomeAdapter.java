package com.example.amilah.test.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.amilah.test.R;
import com.example.amilah.test.fragment.HomeFragment;
import com.example.amilah.test.models.Student;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by amilah on 21-Dec-16.
 */

public class HomeAdapter extends ArrayAdapter<Student>{

    ArrayList<Student> students;
    ArrayList<Student> arraylist;
    public HomeAdapter(Context context, ArrayList<Student> students) {
        super(context,0,students);
        this.students = students;
        arraylist = new ArrayList<Student>();
        arraylist.addAll(students);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Student s = getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.tvName);
        TextView address = (TextView)convertView.findViewById(R.id.tvAddress);
//        //ImageButton btnDelete = (ImageButton)convertView.findViewById(R.id.btnRemove);
//        //btnDelete.setTag(position);
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer index = (Integer) v.getTag();
//                arraylist.remove(index.intValue());
//                notifyDataSetChanged();
//            }
//        });
        name.setText(s.sname);
        address.setText(s.address);
        return convertView;
    }


    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        students.clear();
        if (charText.length() == 0) {
            students.addAll(arraylist);

        } else {
            for (Student studentDetails : arraylist) {
                if (charText.length() != 0 && studentDetails.getSname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    students.add(studentDetails);
                } else if (charText.length() != 0 && studentDetails.getAddress().toLowerCase(Locale.getDefault()).contains(charText)) {
                    students.add(studentDetails);
                }
            }

        }
        notifyDataSetChanged();
    }

}
