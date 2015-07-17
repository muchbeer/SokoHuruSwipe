package muchbeer.king.sokohuruswipe;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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
    private String name,price,contact,link;


    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_three, container, false);

        txtName = (TextView) view.findViewById(R.id.name);
        txtPrice = (TextView) view.findViewById(R.id.price);
        txtContact = (TextView) view.findViewById(R.id.contact);
        txtLink = (TextView) view.findViewById(R.id.link);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name  = sharedpreferences.getString(KEY_NAME, "");         // getting boolean
                price =   sharedpreferences.getString(KEY_PRICE, "");             // getting Integer
                contact =   sharedpreferences.getString(KEY_CONTACT, "");           // getting Float
                link =    sharedpreferences.getString(KEY_LINK, "");            // getting Long
                //   pref.getString("key_name5", null);          // getting String

                if (sharedpreferences.contains(KEY_NAME)) {
                    txtName.setText(name);
                }
                if (sharedpreferences.contains(KEY_PRICE)) {
                    txtPrice.setText(price);

                }

                if (sharedpreferences.contains(KEY_CONTACT)) {
                    txtContact.setText(contact);
                }
                if (sharedpreferences.contains(KEY_LINK)) {
                    txtLink.setText(link);
                }

            }
        });
        //Shared me
      /**************** Get SharedPreferences data *******************/

// If value for key not exist then return second param value - In this case null


        /************ Clear all data from SharedPreferences *****************/

    //    editor.clear();
    //   editor.commit();


        return view;
    }


}
