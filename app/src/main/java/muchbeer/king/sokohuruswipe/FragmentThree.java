package muchbeer.king.sokohuruswipe;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import muchbeer.king.sokohuruswipe.connectdata.AppConfig;
import muchbeer.king.sokohuruswipe.connectdata.AppController;
import muchbeer.king.sokohuruswipe.connectdata.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_LINK = "link";
    private TextView txtName,txtPrice, txtContact,txtLink;
    private String name,price,contact,image;

    private static final int SHARING_CODE = 1;
    private static final String TAG_POSITION = "position";
    private String position;

    //INSERTING DATA
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private EditText edtPlace, edtDesc;
    private String place, descr;
 //   private static final String TAG = "Tell error";

    private static final String TAG = FragmentThree.class.getSimpleName();
    private TextView txtDesc, txtLocation;


    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_three, container, false);

        edtPlace = (EditText) view.findViewById(R.id.edt_place);
        edtDesc = (EditText) view.findViewById(R.id.edt_desc);



        txtName = (TextView) view.findViewById(R.id.name);
        txtPrice = (TextView) view.findViewById(R.id.price);
        txtContact = (TextView) view.findViewById(R.id.contact);
        txtLink = (TextView) view.findViewById(R.id.link);
        txtDesc = (TextView) view.findViewById(R.id.descr);
        txtLocation = (TextView) view.findViewById(R.id.location);


        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();


        //INSERTTING RECORDING
        // Progress dialog
        pDialog = new ProgressDialog(getActivity(),  R.style.AppTheme_Dark_Dialog);
        pDialog.setCancelable(false);

        // Session manager
       // session = new SessionManager(getActivity());

        // Check if user is already logged in or not


        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                place  = edtPlace.getText().toString();
                descr  = edtDesc.getText().toString();

                name  = sharedpreferences.getString(KEY_NAME, "");         // getting boolean
                price =   sharedpreferences.getString(KEY_PRICE, "");             // getting Integer
                contact =   sharedpreferences.getString(KEY_CONTACT, "");           // getting Float
                image =    sharedpreferences.getString(KEY_LINK, "me");            // getting Long
                //   pref.getString("key_name5", null);          // getting String

                             // txtLink.setText(position);

                //INSERTING STAFF
                if (!name.isEmpty() && !price.isEmpty() && !contact.isEmpty()
                        && !image.isEmpty() && !place.isEmpty() && !descr.isEmpty()) {
                    registerUser();
                } else {

                    if(name.isEmpty()) {
                        txtName.setVisibility(View.VISIBLE);
                        txtName.setText("Rudi kujaza jina la bidhaa");

                    }if (price.isEmpty()){
                        txtPrice.setVisibility(View.VISIBLE);
                        txtPrice.setText("Rudi kujaza bei ya bidhaa");
                    }if (contact.isEmpty()) {
                        txtContact.setVisibility(View.VISIBLE);
                        txtContact.setText("Rudi kujaza namba ya simu");
                    }if (image.isEmpty()) {
                        txtLink.setVisibility(View.VISIBLE);
                        txtLink.setText("Rudi kuweka picha ya bidhaa");
                    }if(place.isEmpty()) {
                        txtLocation.setVisibility(View.VISIBLE);
                        txtLocation.setText("Jaza sehemu ilipo bidhaa");
                    }if (descr.isEmpty()) {
                        txtDesc.setVisibility(View.VISIBLE);
                        txtDesc.setText("Jaza maelezo kuhusu bidhaa");
                    }
                    else {
                        txtName.setVisibility(View.GONE);
                    }

                }
            }


        });



        return view;
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Subiri Unasajiriwa ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                String pointError = response.toString();
              //  edtPlace.setText("");
              //  edtDesc.setText("");

                if(pointError.contains("Ongera")) {
                    Toast.makeText(getActivity(), "Umeweza asilimia zote", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "kuna tatizo " + pointError, Toast.LENGTH_LONG).show();
                }


             //   Toast.makeText(getActivity(),"Ongera umeweza kutangaza bidhaa yako:  " + pointError, Toast.LENGTH_LONG).show();

                //Database staff
                /**
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String price = user.getString("price");
                        String image = user.getString("image");
                        String contact = user.getString("contact");
                        String place = user.getString("place");
                        String desc = user.getString("desc");



                        // Inserting row in users table
                        db.addUser(name, price, image, contact, place, desc);


                        getActivity().finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                **/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Tafadhari jaribu tena " +
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
              //  params.put("tag", "register");
                params.put("name", name);
                params.put("price", price);
                params.put("image", image);
                params.put("contact", contact);
                params.put("place", place);
                params.put("descr", descr);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        editor.remove(KEY_LINK); // will delete key email
        editor.commit();

    }
}
