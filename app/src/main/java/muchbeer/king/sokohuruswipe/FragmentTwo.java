package muchbeer.king.sokohuruswipe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.CheckedInputStream;

import muchbeer.king.sokohuruswipe.connectdata.Communicator;

public class FragmentTwo extends Fragment {

    OnHeadlineSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void sendToFragmentThree(String  name);
    }

    //Communintation btn Fragment

 //   commInterfaceData mCallback;
    private Button sendData;

    String getNam, getPric, getContac;

    //Fragment SharedPrefences
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CONTACT = "contact";

    EditText edt_Name, edt_Price, edt_Phone;
    private String phone,price,name;
    private TextView txtTitle, txPrice, txContact;
    String pete;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_two, container, false);



        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


//

        txtTitle = (TextView) view.findViewById(R.id.txTitle);
       // txPrice = (TextView) view.findViewById(R.id.txPrice);
      //  txContact = (TextView) view.findViewById(R.id.txContact);

        sendData = (Button) view.findViewById(R.id.sendInformation);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Get Application", Toast.LENGTH_LONG).show();
            //     editor.commit();

                //   mCallback.onFragmentThree("Check met");
            }
        });





//Shared me

        return  view;

    }

    // newInstance constructor for creating fragment with arguments
    public static FragmentTwo newInstance(String nameItem, String price, String contact) {
        FragmentTwo fragmentSecond = new FragmentTwo();
        Bundle args = new Bundle();
nameItem = "George";
        // args.putInt("someInt", page);
        args.putString("item", nameItem);
        args.putString("price", price);
        args.putString("contact", contact);

        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("SOKO HRU", "This is onPause FragmentTwo");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("SOKO HRU", "This is onDestroyView FragmentTwo");
      //  editor.remove(KEY_NAME);
      //  editor.remove(KEY_CONTACT);
     //   editor.remove(KEY_PRICE);
     //   editor.commit();



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentThree fragmentThree = new FragmentThree();
        Bundle args = new Bundle();
       String nameItem = "George";
        // args.putInt("someInt", page);
     //   args.putString("item", nameItem);
      //  args.putString("price", price);
       // args.putString("contact", contact);

      //  setArguments(args);

    }


    @Override
    public void onStop() {
        Log.d("SOKO HRU", "This is onStop FragmentTwo");
        super.onStop();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("SOKO HRU", "This Detach FragmentTwo");

          }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("SOKO HRU", "This is onStart FragmentTwo");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SOKO HURU", "This onResume FragmentTwo");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SOKO HURU", "This is onDestroy FragmentTwo");

    }


}
