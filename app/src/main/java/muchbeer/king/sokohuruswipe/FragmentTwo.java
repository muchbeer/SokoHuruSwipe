package muchbeer.king.sokohuruswipe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentTwo extends Fragment {
    //Fragment SharedPrefences
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CONTACT = "contact";

    EditText edt_Name, edt_Price, edt_Phone;
    private String phone,price,name;


    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_two, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        edt_Name = (EditText) view.findViewById(R.id.edt_name);
        edt_Price = (EditText) view.findViewById(R.id.edt_price);
        edt_Phone = (EditText) view.findViewById(R.id.edt_phone);


        name  = edt_Name.getText().toString();
        price  = edt_Price.getText().toString();
        phone  = edt_Phone.getText().toString();

        editor = sharedpreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PRICE, price);
        editor.putString(KEY_CONTACT, phone);
        editor.commit();


        Button  btnSend = (Button) view.findViewById(R.id.sendInformation);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                name  = edt_Name.getText().toString();
                price  = edt_Price.getText().toString();
                phone  = edt_Phone.getText().toString();

                editor = sharedpreferences.edit();
                editor.putString(KEY_NAME, name);
                editor.putString(KEY_PRICE, price);
                editor.putString(KEY_CONTACT, phone);
                editor.commit();
            }
        });
//Shared me

        return  view;

    }


}
