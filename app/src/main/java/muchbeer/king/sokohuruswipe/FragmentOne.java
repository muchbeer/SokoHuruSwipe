package muchbeer.king.sokohuruswipe;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment implements View.OnClickListener {

    private static final int SHARING_CODE = 1;
    private static final String TAG_POSITION = "position";

    private TextView messageText;
    private TextView messagePercentage;
    private EditText title,desc;
    private Button uploadButton, btnselectpic;
    private ImageView imageview;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private ProgressBar progressBar;

    int bytesRead, count, bytesAvailable, bufferSize;

    int pStatus = 0;
    private String upLoadServerUri = null;
    private String imagepath=null;

    private Handler mHandler = new Handler();


    private String fileName;
    private String submitImage;
    private Button nextButton;

    //Fragment SharedPrefences
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;


    public static final String KEY_LINK = "link";

    @Override
    public void onClick(View view) {


        if(view==btnselectpic)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }
        else if (view==uploadButton) {

            // progressBar = new ProgressBar(view.getContext());

            //  progressBar.setVisibility(View.VISIBLE);
            // dialog = ProgressDialog.show(MainActivity.this, "", "Uploading file...", true);

            dialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            dialog.setIndeterminate(true);
            dialog.setMessage("Tafadhari subiri...");
            dialog.show();

          //  messageText.setText("uploading started.....");
            new Thread(new Runnable() {
                public void run() {



// Start lengthy operation in a background thread
                    new Thread(new Runnable() {
                        public void run() {
                            while (serverResponseCode != 200) {
                                uploadFile(imagepath);
                                //  mProgressStatus = doWork();
                                //  messagePercentage.setText(serverResponseCode);
                                // Update the progress bar
                                mHandler.post(new Runnable() {
                                    public void run() {
                                       // progressBar.setProgress(serverResponseCode);
                                        //  progressBar.setVisibility(View.VISIBLE);

                                    }
                                });
                            }
                        }
                    }).start();


                }
            }).start();
        }
    }

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_one, container, false);
        // Inflate the layout for this fragment

//Shared me


        uploadButton = (Button) view.findViewById(R.id.upLoadButton);
        btnselectpic = (Button)view.findViewById(R.id.btnCapturePicture);
        messageText  = (TextView)view.findViewById(R.id.txtTitle);

        nextButton = (Button) view.findViewById(R.id.btn_image);
        
        imageview = (ImageView)view.findViewById(R.id.imageViewPic);
        title=(EditText) view.findViewById(R.id.title);
        // desc=(EditText)findViewById(R.id.etdesc);

        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// Create a new fragment and a transaction.
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTwo newFragment = new FragmentTwo();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
                /**
                Fragment newFragment = new FragmentTwo();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();

**/
            }
        });
        // progressBar.setVisibility(View.VISIBLE);
        upLoadServerUri = "http://sokouhuru.com/uploads.php";
        ImageView img= new ImageView(getActivity());
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == MainActivity.RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();
            //Uri imagename=data.getData();
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
            Bitmap bitmap= BitmapFactory.decodeFile(imagepath);
            imageview.setImageBitmap(bitmap);
            messageText.setText("Bofya Pakua kutunza picha.");

           // messageText.setText("Uploading:" +imagepath);


        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public int uploadFile(String sourceFileUri) {

        //sourceFileUri.replace(sourceFileUri, "ashifaq");
        //

        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name=(hour+""+minute+""+second+""+day+""+(month+1)+""+year);
        String tag=name+".jpg";
        fileName = sourceFileUri.replace(sourceFileUri,tag);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        long total =0;
        int percentage;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();
            // progressBar.setVisibility(View.GONE);

            Log.e("uploadFile", "Source File not exist :" + imagepath);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("File halipo :" + imagepath);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);




                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //  int lengthOfFile = conn.getContentLength();
                while ((count = bytesRead) > 0) {

                    total +=count;

                    //   percentage = (int) (((total)/lengthOfFile)*100);
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    //   publishProgress
                    //      messagePercentage.setText("I am coming home");

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            int num = 100;
                            // progressBar.setVisibility(View.GONE);
                            // messageText.setText(msg);
                            dialog.dismiss();
                            String msg = " Umefanikiwa kuweka picha, sasa weka bidhaa.";
                            messageText.setText(msg);

                            submitImage = "http://sokouhuru.com/uploads/" + fileName;

                          //  Intent startIntent = new Intent(getActivity(), FragmentThree.class);
                            // positionSearch = getActivity().position;
                         //   startIntent.putExtra(TAG_POSITION, submitImage);
                            //    startIntent.putExtra(TAG_POSITION2, positionSearch);
                        //    startActivityForResult(startIntent, SHARING_CODE);
                            //Storing the links


                            sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(KEY_LINK, submitImage);  // Saving string
                            // Save the changes in SharedPreferences
                          editor.commit(); // commit changes


                            Toast.makeText(getActivity(), "Umefanikiwa kupakua." + fileName, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        //  progressBar.setVisibility(View.GONE);
                        messageText.setText("Jaribu tena Kupakua :  script url.");
                        Toast.makeText(getActivity(), "Tatizo la mtandao", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        //   progressBar.setVisibility(View.GONE);
                        messageText.setText("Jaribu tena kupakua ");
                        Toast.makeText(getActivity(), "Tatizo la mtandao ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload server Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            //   progressBar.setVisibility(View.GONE);
            return serverResponseCode;

        }
    }
}
