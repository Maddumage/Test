package com.example.amilah.test.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.amilah.test.R;
import com.example.amilah.test.adapters.HomeAdapter;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.models.Student;
import com.example.amilah.test.utill.Constant;

import java.util.ArrayList;

public class HomeFragment extends ListFragment {

    private HomeAdapter homeAdapter;
    View view;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<Student> list ;
    Student student;
    int deleteResponse = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment,container,false);


        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO",getActivity().MODE_PRIVATE);
        int id = preferences.getInt("id",0);
        list = dbHelper.getAllStudent(id);
        if (list.size()>0) {
            homeAdapter = new HomeAdapter(getContext(),list);

        setListAdapter(homeAdapter);
    }
        else
            Toast.makeText(getContext(),"No marks",Toast.LENGTH_SHORT).show();

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Student Details");
      listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student;
                student = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.KEY_STUDENT,student);
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_navigation,registerFragment).commit();
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
                        student = list.get(position);
                        deleteResponse = dbHelper.deleteStudent(student);
                        list.remove(position);
                        homeAdapter.notifyDataSetChanged();
                        if(deleteResponse==1)
                        {
                            Toast.makeText(getContext(),"Item Removed",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getContext(),"Item Not Removed",Toast.LENGTH_LONG).show();
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

        inflater.inflate(R.menu.navigation, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //Log.i("hdd","sss"+searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.i("jj","jj"+query);
//                DBHelper dbHelper = null;
//                marksArrayList = dbHelper.searchStudent(query);
//                homeAdapter = new HomeAdapter(getContext(), marksArrayList);
//
//                setListAdapter(homeAdapter);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                homeAdapter.filter(searchQuery.toString().trim());
                listView.invalidate();
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
       // super.onCreateOptionsMenu(menu, inflater);
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

}

