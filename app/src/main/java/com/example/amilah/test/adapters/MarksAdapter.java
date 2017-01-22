package com.example.amilah.test.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amilah.test.R;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.models.Marks;
import com.example.amilah.test.models.Subject;
import com.example.amilah.test.utill.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by amilah on 21-Dec-16.
 */

public class MarksAdapter extends ArrayAdapter<Marks> {

    ArrayList<Marks> marksArrayList;
    Context context;
    ImageButton add, delete;
    Marks marks;
    Spinner spinner;
    EditText etMarks;
    Boolean isNew;
    String sMarks;
    int selectedSubject;

    public MarksAdapter(Context context, ArrayList<Marks> marksArrayList, Boolean isNew) {
        super(context, R.layout.row, marksArrayList);
        this.marksArrayList = marksArrayList;
        this.context = context;
        this.isNew = isNew;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
//        marks = getItem(position);
//        Log.i("=======Marks_start====",""+marks.getMarks()+marks.getSubject());
//        if(convertView==null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
//            convertView.setTag(position);
//        }
//        spinner = (Spinner)convertView.findViewById(R.id.spSubject);
//        add = (ImageButton)convertView.findViewById(R.id.btnAdd);
//        delete = (ImageButton)convertView.findViewById(R.id.btnRemove);
//        etMarks = (EditText)convertView.findViewById(R.id.etMarks);
//        Log.i("isNewBefore--",""+isNew);
//        sMarks = etMarks.getText().toString();
//        if(position==0){delete.setVisibility(View.INVISIBLE);}
//        if (isNew==false)
//        {
//            spinner.setSelection(marks.getSubID());
//            Log.i("isNewBefore--in",""+isNew);
//        }
//        etMarks.setText(marksArrayList.get(position).getMarks());
//
//        addSubjectsToSpinner(-1);
//
//        etMarks.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                marks.setMarks(etMarks.getText().toString());
//            }
//        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int posi, long id) {
//                try {
//                    selectedSubject = parent.getSelectedItemPosition();
//                    marks.setSubID(selectedSubject);
//                    Log.i("selectedSubject--",""+selectedSubject);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinner.setSelection(marks.getSubID());
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences preferences = getContext().getSharedPreferences("USER_INFO",Context.MODE_PRIVATE);
//                int id = preferences.getInt("id",0);
//                Log.i("====List Size1====",""+  marksArrayList.size());
//                Marks m = new Marks();
//                m.setsID(id);
//                m.setmID(marksArrayList.size()+1);
//                marksArrayList.add(m);
//                notifyDataSetChanged();
//                Constant.KEY_NEW_ITEM = m.getmID();
//                Toast.makeText(context, "Added New Result",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                marksArrayList.remove(marks);
//                notifyDataSetChanged();
//                Toast.makeText(context, "Deleted!",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Log.i("====List Size2====",""+marksArrayList.size());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row, parent, false);
        final EditText subMarks = (EditText) rowView.findViewById(R.id.etMarks);
        sMarks = subMarks.getText().toString();

        subMarks.setText("" + marksArrayList.get(position).getMarks());

        ArrayList<Subject> lables = null;
        try {
            lables = loadSubjectSpinner();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<Subject> dataAdapter = new ArrayAdapter<Subject>(getContext(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerSubject = (Spinner) rowView.findViewById(R.id.spSubject);
        spinnerSubject.setAdapter(dataAdapter);

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int posi, long id) {
                try {
                    marksArrayList.get(position).setSubID(((Subject) spinnerSubject.getSelectedItem()).getSubID());
                    Log.i("aaa", "aaa" + marksArrayList.get(position).getSubID());
                    final int vid = ((Subject) spinnerSubject.getSelectedItem()).getSubID();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerSubject.setSelection(marksArrayList.get(position).getSubID() - 1);

        subMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (subMarks.getText().toString() == "") {
                    subMarks.setError("Input Subject Marks");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (subMarks.getText().toString() == "") {
                        subMarks.setError("Input Vehicle no");
                    } else {

                        marksArrayList.get(position).setMarks(subMarks.getText().toString());
                    }
                } catch (Exception e) {
                    subMarks.setText("");
                }
            }
        });
        add = (ImageButton) rowView.findViewById(R.id.btnAdd);
        delete = (ImageButton) rowView.findViewById(R.id.btnRemove);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(position);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to delete this record?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                remove(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
        //hide the delete button
        if (marksArrayList.size() == 1) {
            if (isNew == false) {
                delete.setEnabled(false);
                delete.setVisibility(View.GONE);
            }
            delete.setVisibility(View.INVISIBLE);
        } else {
            delete.setVisibility(View.VISIBLE);
            if (isNew == false) {
                delete.setEnabled(true);
                //delete.setVisibility(View.GONE);
            }
        }
        return rowView;
    }

    public ArrayList<Subject> loadSubjectSpinner() throws Exception {

        SharedPreferences preferences = getContext().getSharedPreferences("USER_INFO", getContext().MODE_PRIVATE);
        int id = preferences.getInt("id", 0);
        DBHelper dbHelper = new DBHelper(getContext());
        return dbHelper.getAllSubjects(-1);

    }

    public void remove(int pos) {
        // TODO Auto-generated method stub
        if(isNew)
        {
            marksArrayList.remove(pos);
        }
        else {
            DBHelper dbHelper = new DBHelper(getContext());
            int response = dbHelper.deleteMarks(marksArrayList.get(pos).getmID());
            marksArrayList.remove(pos);
            if(response>0) {
                Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(), "Delete Failed", Toast.LENGTH_LONG).show();
            }
        }
        notifyDataSetChanged();
    }

    public void add(int pos) {
        // TODO Auto-generated method stub
        if (marksArrayList.size() >= 10) {
            new AlertDialog.Builder(context)
                    .setMessage("Cannot add more than 10 records")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            Marks marksUnit = new Marks();
            marksUnit.setMarks(" ");
            marksUnit.setmID(marksArrayList.get(pos).getmID()+1);
            if (isValidInput(marksArrayList))
            {
                marksArrayList.add(marksUnit);
                Constant.KEY_NEW_ITEM = marksUnit.getmID();
            }
            else {
                Toast.makeText(getContext(),"Duplicate Subjects! Please Change",Toast.LENGTH_LONG).show();
            }

            notifyDataSetChanged();
        }
    }

    public boolean isValidInput(ArrayList<Marks> mInputs)
    {
        Iterator<Marks> iterator = mInputs.iterator();
        final Set<Integer> subjects = new HashSet<>();
        Marks marksToCheck = new Marks();
        while (iterator.hasNext()){
            marksToCheck = iterator.next();
            subjects.add(marksToCheck.getSubID());
        }
        if(subjects.size()< mInputs.size())
        {
            return false;
        }
        return true;
    }
}
