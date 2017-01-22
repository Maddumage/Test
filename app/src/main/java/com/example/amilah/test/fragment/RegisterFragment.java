package com.example.amilah.test.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.amilah.test.R;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.models.Student;
import com.example.amilah.test.utill.Constant;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by amilah on 20-Dec-16.
 */

public class RegisterFragment extends Fragment{

    private TextInputLayout input_layout_name,input_layout_dob,input_layout_address,input_layout_contact,input_layout_email;
    private EditText input_name,input_dob,input_address,input_contact,input_email;
    String name,dob,address,contact,email;
    Button btnRegistration,btnUpdate;
    ImageButton btnBirthday;
    View view;
    DBHelper dbHelper;
    Student studentObj;
    Boolean isNew = true;
    int updateResponse=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle!=null) {
           studentObj = bundle.getParcelable(Constant.KEY_STUDENT);
            isNew = false;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.register_fragment, container, false);

        input_layout_name = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        input_layout_dob = (TextInputLayout) view.findViewById(R.id.input_layout_dob);
        input_layout_address = (TextInputLayout) view.findViewById(R.id.input_layout_address);
        input_layout_contact = (TextInputLayout) view.findViewById(R.id.input_layout_contact);
        input_layout_email = (TextInputLayout) view.findViewById(R.id.input_layout_email);

        input_name = (EditText) view.findViewById(R.id.input_name);
        input_dob = (EditText) view.findViewById(R.id.input_dob);
        input_address = (EditText) view.findViewById(R.id.input_address);
        input_contact = (EditText) view.findViewById(R.id.input_contact);
        input_email = (EditText) view.findViewById(R.id.input_email);

        dbHelper = new DBHelper(getActivity());

        btnRegistration = (Button) view.findViewById(R.id.btnRegister);
       // btnBirthday = (ImageButton)view.findViewById(R.id.btnDate);

        input_name.addTextChangedListener(new MyTextWatcher(input_name));
        input_dob.addTextChangedListener(new MyTextWatcher(input_dob));
        input_address.addTextChangedListener(new MyTextWatcher(input_address));
        input_contact.addTextChangedListener(new MyTextWatcher(input_contact));
        input_email.addTextChangedListener(new MyTextWatcher(input_email));

        input_dob.setInputType(InputType.TYPE_NULL);
        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });
            //if data available then enable edit
            if (!isNew){
                btnRegistration.setText("Edit");

                //add data to form from db
                input_name.setText(studentObj.getSname());
                input_dob.setText(studentObj.getDob());
                input_address.setText(studentObj.getAddress());
                input_contact.setText(studentObj.getContact());
                input_email.setText(studentObj.getEmail());
                //disable editText layouts
                disableInput();

            }

            btnRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNew) {
                        Student student;
                        student = getUserInput();
                        if (isSubmitForm()){
                            dbHelper.addStudent(student);
                            goToHomeFragment();
                        }
                        else
                        return;
                    }
                    else {
                        enableInput();
                        btnRegistration.setText("Update");
                        //btnUpdate.setVisibility(View.VISIBLE);
                        btnRegistration.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Student student;
                                student = getUserInput();
                                student.setSid(studentObj.getSid());
                                if(isSubmitForm()) {
                                    updateResponse = dbHelper.updateStudent(student);
                                    if (updateResponse > 0) {
                                        Toast.makeText(getContext(), "Update Success", Toast.LENGTH_LONG).show();
                                        goToHomeFragment();
                                    } else
                                        Toast.makeText(getContext(), "Update Failed!", Toast.LENGTH_LONG).show();
                                }
                                else
                                    return;//go to form
                            }
                        });
                    }
                }
            });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registration");

    }
    public void disableInput()
    {
        input_name.setEnabled(false);
        input_address.setEnabled(false);
        input_dob.setEnabled(false);
        input_contact.setEnabled(false);
        input_email.setEnabled(false);
    }
    public void enableInput()
    {
        input_name.setEnabled(true);
        input_address.setEnabled(true);
        input_dob.setEnabled(true);
        input_contact.setEnabled(true);
        input_email.setEnabled(true);
    }
    public Student getUserInput()
    {
        int id;
        name = input_name.getText().toString();
        dob = input_dob.getText().toString();
        address = input_address.getText().toString();
        contact = input_contact.getText().toString();
        email = input_email.getText().toString();
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        id = preferences.getInt("id",1);
        Student student = new Student();
        student.setSname(name);
        student.setAddress(address);
        student.setEmail(email);
        student.setContact(contact);
        student.setDob(dob);
        student.setUid(id);
        return student;
    }

    public void goToHomeFragment()
    {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_navigation,homeFragment).commit();
    }

    private boolean isSubmitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        if (!validateAddress()) {
            return false;
        }
        if (!validateContact()){
            return false;
        }
        if (!validateDob()){
            return false;
        }

        else
            return true;
    }

    private boolean validateName() {
        if (input_name.getText().toString().trim().isEmpty()) {
            input_layout_name.setError(getString(R.string.err_msg_name));
            requestFocus(input_name);
            return false;
        } else {
            input_layout_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = input_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input_layout_email.setError(getString(R.string.err_msg_email));
            requestFocus(input_email);
            return false;
        } else {
            input_layout_email.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (input_address.getText().toString().trim().isEmpty()) {
            input_layout_address.setError(getString(R.string.err_msg_address));
            requestFocus(input_address);
            return false;
        } else {
            input_layout_address.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateContact() {
        if (input_contact.getText().toString().trim().isEmpty()) {
            input_layout_contact.setError(getString(R.string.err_msg_contact));
            requestFocus(input_contact);
            return false;
        } else {
            input_layout_contact.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDob() {
        if (input_dob.getText().toString().trim().isEmpty()) {
            input_layout_dob.setError(getString(R.string.err_msg_dob));
            requestFocus(input_dob);
            return false;
        } else {
            input_layout_dob.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_address:
                    validateAddress();
                    break;
                case R.id.input_contact:
                    validateContact();
                    break;
                case R.id.input_dob:
                    validateDob();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    //create a class to make DateTimePicker Dialog
    public  class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        //set selected date to EditText
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            int age = getAge(year,month,day);
            if(age>5)
            {
                input_dob.setText(day + "/" + (month + 1) + "/" + year);
            }
            else {
                input_layout_dob.setError("Enter Valid Date");
                //Toast.makeText(getContext(),"Your age is not valid!",Toast.LENGTH_LONG).show();
            }

        }
    }
    private int getAge(int year, int month, int day)
    {
//set up date of birth
        Calendar calDOB = Calendar.getInstance();
        calDOB.set( year, month, day );
//setup calNow as today.
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new java.util.Date());
//calculate age in years.
        int ageYr = (calNow.get(Calendar.YEAR) - calDOB.get(Calendar.YEAR));
// calculate additional age in months, possibly adjust years.
        int ageMo = (calNow.get(Calendar.MONTH) - calDOB.get(Calendar.MONTH));
        if (ageMo < 0)
        {
//adjust years by subtracting one
            ageYr--;
        }
        return ageYr;
    }
}
