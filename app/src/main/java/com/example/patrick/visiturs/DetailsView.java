package com.example.patrick.visiturs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsView extends AppCompatActivity {
    private Location l;
    private GenerateLocations gl;
    private int id;
    private Button save, btnCall, btnEmail, btnMaps;
    private ImageButton imageButton;
    private EditText txtName, txtAddress, txtEmail, txtPhoneNumber, Zipcode, Number;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String LOGTAG = "Camera01";
    private ImageView imageView;
    private Bitmap imageBitmap;
    private DAL dal;
    private GoogleMap map;
    private GeoCoder g;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        dal = new DAL(this);
        gl = GenerateLocations.getInstace(this);

        int i = (int)getIntent().getExtras().getSerializable("LocationID");
        l = gl.getLocationByID(i);
        if(l == null)
        {
            l = new Location("","","","","",0,"");
            id = 0;
        }
        else
        {
            id = l.getId();
        }
        init();

    }

    private void init() {
        //Toolbar items
        Toolbar toolbar =(Toolbar)findViewById(R.id.mCustomToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hello! " + l.getName());

        //Textfields
        txtName = (EditText) findViewById(R.id.Name);
        txtAddress = (EditText) findViewById(R.id.Address);
        txtEmail = (EditText) findViewById(R.id.Email);
        txtPhoneNumber = (EditText) findViewById(R.id.Phone);
        Zipcode = (EditText)findViewById(R.id.Zipcode);
        Number = (EditText)findViewById(R.id.Number);
        imageView = (ImageView)findViewById(R.id.imageView);
        if(id!=0)
        {
            txtName.setText(l.getName());
            txtAddress.setText(l.getAddress());
            Zipcode.setText(l.getZipcode());
            Number.setText(l.getNumber());
            txtEmail.setText(l.getEmail());
            txtPhoneNumber.setText(l.getPhoneNumber());

            if(l.getImagePath() != null && !l.getImagePath().isEmpty()){
                try {

                    File imgFile = new  File(l.getImagePath());

                    if(imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageView.setImageBitmap(myBitmap);
                        mCurrentPhotoPath = l.getImagePath();

                    }
                }catch (Exception e)
                {
                    Log.e("read pic", "onCreate: ", e);
                }
            }
        }


        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        btnCall = (Button)findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCall();
            }
        });
        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail();
            }
        });
        btnMaps = (Button)findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });
    }
        //Menu starts
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int di = item.getItemId();
        if(di == R.id.action_Edit)
        {

            //Add
            if(id == 0)
            {
                l = new Location(txtName.getText().toString(),txtAddress.getText().toString(),Zipcode.getText().toString(),Number.getText().toString(),txtPhoneNumber.getText().toString(),0,txtEmail.getText().toString());
                l.setImagePath(mCurrentPhotoPath);
                g = new GeoCoder(new OnLoad() {
                    @Override
                    public void onFinish() {
                        l.setLat(g.getLat());
                        l.setLon(g.getLon());
                        gl.AddLocation(l);
                        Intent i = new Intent(DetailsView.this, MainActivity.class);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                },l);
                g.loadAll();


            }
            //Edit
            else
            {
                l = new Location(txtName.getText().toString(),txtAddress.getText().toString(),Zipcode.getText().toString(),Number.getText().toString(),txtPhoneNumber.getText().toString(),id,txtEmail.getText().toString());
                l.setImagePath(mCurrentPhotoPath);
                g = new GeoCoder(new OnLoad() {
                    @Override
                    public void onFinish() {
                        l.setLat(g.getLat());
                        l.setLon(g.getLon());
                        dal.updateLocation(l,id);
                        Intent i = new Intent(DetailsView.this, MainActivity.class);
                        setResult(RESULT_OK,i);
                        finish();
                    }
                },l);
                g.loadAll();

            }

        }
        else if(di == R.id.action_delete)
        {
            dal.deleteLocation(l.getId());
            Intent i = new Intent(DetailsView.this, MainActivity.class);
            setResult(RESULT_OK,i);
            finish();
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savemenu, menu);
        if(l.getId()==0)
        {
            menu.findItem(R.id.action_delete).setVisible(false);
            this.invalidateOptionsMenu();
        }
        return true;
    }
    //Menu ends
    //Make a phonecall
    private void MakeCall() {
        //Dial is used so you hit the "dialer", if the ACTION_CALL is used it tries to call the number.
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + l.getPhoneNumber()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    //Phonecall ends
    //Picture starts
    private void takePicture()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {

            }
            if(photoFile != null)
            {
                Uri photoUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
          try {
              File imageFile = new File(mCurrentPhotoPath);
              if(imageFile.exists())
              {
                  Bitmap myBitMap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                  imageView.setImageBitmap(myBitMap);
              }
          }
          catch (Exception e)
          {

          }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void log(String s)
    { Log.d(LOGTAG, s); }

    public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+l.getEmail()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void showMap() {
        String url = "http://maps.google.com/maps?daddr="+l.getAddress();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
}



