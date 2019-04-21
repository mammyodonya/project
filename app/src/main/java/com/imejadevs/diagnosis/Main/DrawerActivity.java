package com.imejadevs.diagnosis.Main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.imejadevs.diagnosis.Frags.About;
import com.imejadevs.diagnosis.Frags.Fitness;
import com.imejadevs.diagnosis.Frags.Legal;
import com.imejadevs.diagnosis.Frags.PHR;
import com.imejadevs.diagnosis.Frags.Rate;
import com.imejadevs.diagnosis.Frags.UserAccount;
import com.imejadevs.diagnosis.Frags.Videos;
import com.imejadevs.diagnosis.History.History;
import com.imejadevs.diagnosis.MainActivity;
import com.imejadevs.diagnosis.R;
import com.imejadevs.diagnosis.web.webview;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mauth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerActivity.this, History.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(getString(R.string.app_name));

        PHR phr = new PHR();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, phr, "Home");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            createDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setTitle("Settings");
            //SQL sqLiteTest = new SQL();
           // replaceFrag(sqLiteTest);
            return true;
        } if (id == R.id.rate) {
            setTitle("Rate Us");
            Rate legal = new Rate();
            replaceFrag(legal);
            return true;
        } if (id == R.id.about) {
            setTitle("About The App");
            About about=new About();
            replaceFrag(about);
            return true;
        }
       if (id==R.id.dev){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String s = "imeldaodonya2019@gmail.com";
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                i.putExtra(Intent.EXTRA_TEXT, "Email body");
                i.setType("message/rfc822");
                Intent choser = Intent.createChooser(i, "Launch Email");
                startActivity(choser);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            setTitle("Update PHR");
            PHR phr = new PHR();
            replaceFrag(phr);
        } else if (id == R.id.nav_gallery) {
            setTitle("Sample Videos");
            Videos vid = new Videos();
            replaceFrag(vid);
        } else if (id == R.id.nav_logout) {
            mauth.signOut();
            Intent intent = new Intent(DrawerActivity.this, MainActivity.class);
            startActivity(intent);
            DrawerActivity.this.
                    finish();

        } else if (id == R.id.nav_slideshow) {
            setTitle("Account");
            UserAccount account = new UserAccount();
            replaceFrag(account);

        } else if (id == R.id.nav_manage) {
            setTitle("Legal Note");
            Legal legal = new Legal();
            replaceFrag(legal);

        } else if (id == R.id.nav_fit) {
                setTitle("KEEP FIT");
                Fitness account = new Fitness();
                replaceFrag(account);

        } else if (id == R.id.nav_share) {
            Intent odonya = new Intent(Intent.ACTION_SEND);
            odonya.setType("text/plain");
            String url = "https://play.google.com/store/apps/details?id=com.imejadevs.diagnosis";
            odonya.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(odonya);
        }
        else if (id == R.id.dev) {
            Log.i ("send email", "");
            String[] TO ={"imedaodonya2019@gmail.com"};
            Intent mail= new Intent(Intent.ACTION_SEND);
            mail.setData(Uri.parse("mailto:"));
            mail.setType("text/plain");
            mail.putExtra(Intent.EXTRA_EMAIL,TO);
            mail.putExtra(Intent.EXTRA_SUBJECT,"Your subject");
            mail.putExtra(Intent.EXTRA_TEXT,"Email message goes here");
            try {
                startActivity(Intent.createChooser(mail,"send mail..."));
                finish();
                Log.i("Email sent successfully","");
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(context, "There is no email client installed", Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.nav_facebook) {
//linking to facebook
            Intent intent=new Intent(DrawerActivity.this, webview.class);
            Bundle bundle=new Bundle();
            bundle.putString("name","Facebook");
            bundle.putString("url","https://www.facebook.com/Odonya.Imm");
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            createDialog();
            //System.exit(0);
        } else if (id == R.id.action_settings) {


        } else if (id == R.id.nav_twitter){

            Intent intent=new Intent(DrawerActivity.this, webview.class);
            Bundle bundle=new Bundle();
            bundle.putString("name","Twitter");
            bundle.putString("url","https://www.twitter.com/OdonyaImm");
            intent.putExtras(bundle);
            startActivity(intent);
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    private void replaceFrag(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "Fragment");
        fragmentTransaction.commit();
    }

    private void createDialog() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Are you sure you want to exit?");

        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the


        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {

                        DrawerActivity.super.onBackPressed();

                    }

                }

        );

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                // We do nothing

            }

        });

        alertDlg.show();

    }
}
