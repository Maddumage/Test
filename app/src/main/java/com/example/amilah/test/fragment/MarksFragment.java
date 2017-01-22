package com.example.amilah.test.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amilah.test.R;
import com.example.amilah.test.adapters.MarksAdapter;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.models.Marks;
import com.example.amilah.test.models.Student;
import com.example.amilah.test.models.Subject;
import com.example.amilah.test.utill.Constant;

import java.util.ArrayList;
import java.util.Iterator;

public class MarksFragment extends ListFragment {

    private MarksAdapter marksAdapter;
    View view;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<Marks> marksArrayList;
    Marks marksRef;
    int deleteResponse = -1;
    Button saveMarks;
    ArrayList<Marks> mList;
    Student student;
    Subject subject;
    Boolean isNew;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
//        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO",getActivity().MODE_PRIVATE);
//        int id = preferences.getInt("id",0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            student = bundle.getParcelable(Constant.KEY_STUDENT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.marks_fragment, container, false);
            listView = (ListView) view.findViewById(android.R.id.list);
            saveMarks = (Button) view.findViewById(R.id.btnSaveMarks);


            marksArrayList = new ArrayList<Marks>();

            if(dbHelper.getAllMarks(student.getSid()).size()>0)
            {
                isNew = false;
                saveMarks.setText("Update");
                marksArrayList = dbHelper.getAllMarks(student.getSid());
                marksAdapter = new MarksAdapter(getContext(), marksArrayList, false);
                setListAdapter(marksAdapter);
            }
            else {
                isNew = true;
                saveMarks.setText("Save");
                try {
                    Marks marksObj = new Marks();
                    marksObj.setmID(1);
                    marksObj.setMarks("");
                    marksObj.setSubjectID(new Subject());
                    marksArrayList.add(marksObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                marksAdapter = new MarksAdapter(getContext(), marksArrayList, true);
                setListAdapter(marksAdapter);
            }

//            marksArrayList = dbHelper.getAllMarks(student.getSid());
//            if(marksArrayList.size()>0)
//            {
//                isNew = false;
//            }
//
//            marksAdapter = new MarksAdapter(getContext(), R.layout.row, marksArrayList, isNew);
//            setListAdapter(marksAdapter);
//            if (isNew) {
//                marksArrayList.add(new Marks());
//                marksAdapter = new MarksAdapter(getContext(), R.layout.row, marksArrayList, isNew);
//                Toast.makeText(getContext(), "No marks", Toast.LENGTH_SHORT).show();
//                saveMarks.setText("Save");
//                saveMarks.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (marksArrayList.size() > 0) {
//                            Log.i("list size--", "" + marksArrayList.size());
//                            //List<String> selections = new ArrayList<String>();
//                            for (int i = 0; i < listView.getChildCount(); i++) {
//                                marksArrayList.get(i);
//                                View listItem = listView.getChildAt(i);
//                                Spinner spinner = (Spinner) listItem.findViewById(R.id.spSubject);
//                                EditText etMarks = (EditText) listItem.findViewById(R.id.etMarks);
//                                // Get selection
//                                String mark = (String) etMarks.getText().toString();
//                                // selections.add(selection);
//                                Marks m = new Marks();
//                                int sid = student.getSid();
//                                m.setsID(sid);
//                                m.setMarks(mark);
//                                m.setSubID(spinner.getSelectedItemPosition());
//                                long responce = dbHelper.addMarks(m);
//                                Log.i("marks_to_save--", "" + spinner.getSelectedItemPosition() + "--" + mark);
//                                if (responce != -1) {
//                                    goBackToStudentList();
//                                    Toast.makeText(getContext(), "Marks Saved Success!", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(getContext(), "Marks Saved Failed!", Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        }
//                    }
//                });
//            } else {
//                saveMarks.setText("Update");
//                saveMarks.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String mark = null;
//                        ArrayList<Marks> marksToUpdate = new ArrayList<Marks>();
//                        if (marksArrayList.size() > 0) {
//                            Log.i("list size--", "" + marksArrayList.size());
//                            //List<String> selections = new ArrayList<String>();
//                            for (int i = 0; i < listView.getChildCount(); i++) {
//                                Marks mNew = marksArrayList.get(i);
//
//                                marksArrayList.get(i);
//                                View listItem = listView.getChildAt(i);
//                                Spinner spinner = (Spinner) listItem.findViewById(R.id.spSubject);
//                                EditText etMarks = (EditText) listItem.findViewById(R.id.etMarks);
//                                // Get selection
//                                mark = (String) etMarks.getText().toString();
//                                // selections.add(selection);
//
//                                int sid = student.getSid();
//                                Marks m = new Marks();
//                                m.setsID(sid);
//                                m.setmID(mNew.getmID());
//                                Log.i("mID--", "" + m.getmID() + "--" + marksArrayList.get(i).getmID()+marksArrayList.get(i).getSubID());
//                                m.setMarks(mark);
//                                m.setSubID(spinner.getSelectedItemPosition());
//                                marksToUpdate.add(m);
//                                Log.i("marks_to_save--", "" + spinner.getSelectedItemPosition()+ "--" + m.getMarks());
//                                if (Constant.KEY_NEW_ITEM==m.getmID()){dbHelper.addMarks(m);}
//                                long responce = dbHelper.updateMarks(marksToUpdate);
//                                if (responce != -1) {
//                                    goBackToStudentList();
//                                    Toast.makeText(getContext(), "Marks Update Success!", Toast.LENGTH_LONG).show();
//
//                                } else {
//                                    Toast.makeText(getContext(), "Marks Update Failed!", Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                        }
//                    }
//                });
//            }
            saveMarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long response = 0;
                    if(marksArrayList.size()>0)
                    {
                        if(isNew) {
                            Iterator<Marks> iterator = marksArrayList.iterator();

                            while (iterator.hasNext()) {
                                marksRef = iterator.next();
                                subject = marksRef.getSubject();
                                int sid = marksRef.getSubID();
                                String m = marksRef.getMarks();
                                marksRef.setMarks(m);
                                marksRef.setsID(student.getSid());
                                marksRef.setSubID(sid);
                                response = dbHelper.addMarks(marksRef);
                                Log.i("marksNew--", "" + marksRef.getSubID());
                            }
                            if(response>0) {
                                Toast.makeText(getContext(), "Save Success", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Save Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Iterator<Marks> iterator = marksArrayList.iterator();
                            while (iterator.hasNext()) {
                                marksRef = iterator.next();
                                subject = marksRef.getSubject();
                                int sid = marksRef.getSubID();
                                String m = marksRef.getMarks();
                                marksRef.setMarks(m);
                                marksRef.setsID(student.getSid());
                                marksRef.setSubID(sid);
                                if(Constant.KEY_NEW_ITEM == marksRef.getmID())
                                {
                                    response = dbHelper.addMarks(marksRef);
                                    Log.i("marksAddNew--", "" + marksRef.getSubID()+"--"+marksRef.getmID()+"--"+Constant.KEY_NEW_ITEM);
                                }
                                else
                                {Log.i("marksDel--", "" + marksRef.getmID()+"--"+Constant.KEY_DELETE_ITEM);
                                    response = dbHelper.updateMarks(marksRef);
                                    Log.i("marksUpdate--", "" + marksRef.getSubID()+marksRef.getmID()+"--"+Constant.KEY_NEW_ITEM);

                                }
                            }
                            if(response!= 0) {
                                Toast.makeText(getContext(), "Update Success", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    goBackToStudentList();
                }
            });
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Student Marks");
        listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Marks marks;
                marks = marksArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.KEY_STUDENT, (Parcelable) marks);
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_navigation, registerFragment).commit();
              /*  String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),"Position "+item,Toast.LENGTH_SHORT).show();*/

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // main code on after clicking yes\
                        marksRef = marksArrayList.get(position);
                        deleteResponse = dbHelper.deleteMarks(marksRef.getmID());
                        marksArrayList.remove(position);
                        marksAdapter.notifyDataSetChanged();
                        if (deleteResponse == 1) {
                            Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getContext(), "Item Not Removed", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.marks, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBackToStudentList()
    {
        StudentResultFragment studentResultFragment = new StudentResultFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_navigation,studentResultFragment).commit();
    }
}

