package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    private DrawerLayout mDrawerLayout;

    public TextView showEmail;
    private GoogleApiClient googleApiClient;

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setNavigationViewListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setLogo(R.drawable.napkeennlogoforhome);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

       googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


       mAuth = FirebaseAuth.getInstance();
    //    FirebaseUser user = mAuth.getCurrentUser();
//        String email = user.getEmail();
  //      showEmail = findViewById(R.id.emailnavbar);
    //    showEmail.setText(email);
       final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Home.this);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //get current user

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (account == null) {
                    //logOutt();
                } else if
                    (user == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                       // startActivity(new Intent(Home.this, LoginActivity.class));
                       // finish();
                    }else{

                    }

            }};

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_restaurant).setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_cafe).setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bar).setText(R.string.tab_label3));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_cake).setText("Dessert"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);




        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        photoImageView = (ImageView) findViewById(R.id.imageUser);
        nameTextView = (TextView) findViewById(R.id.NamaUserDrawer);
        emailTextView = (TextView) findViewById(R.id.emailnavbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        if(isOnline()){
        }else{
            Toast.makeText(Home.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

        mAuth = FirebaseAuth.getInstance();

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
        break;
            case R.id.nav_nearby:
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                break;
            case R.id.nav_tempat_terbaik:
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                break;
            case R.id.nav_bantuan:
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                break;
            case R.id.nav_tentang:
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                break;
            case R.id.nav_tambah_restoran:
                Intent tmbhrestoran = new Intent(Home.this, TambahRestoran.class);
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                startActivity(tmbhrestoran);
                break;
            case R.id.nav_pengaturan:
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();;
                break;
            case R.id.nav_Keluar:
                new AlertDialog.Builder(this)
                        .setTitle("Keluar")
                        .setMessage("Anda yakin ingin keluar?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                 logOutt();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                break;
            default: break;
        }
        return (super.onOptionsItemSelected(item));
    }

    private void logOutt() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    //   Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item =  menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    //sign out method
    public void signOut() {
        mAuth.signOut();
        startActivity(new Intent(Home.this, LoginActivity.class));
        finish();

    }
        @Override
        protected void onResume() {
            super.onResume();

        }

        @Override
        public void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(authListener);
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (opr.isDone()) {
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }

        @Override
        public void onStop() {
            super.onStop();
            if (authListener != null) {
                mAuth.removeAuthStateListener(authListener);
            }
        }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

           // nameTextView.setText(account.getDisplayName());
  //          emailTextView.setText(account.getEmail());

    //        Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);

        } else {
            goLogInScreen();
        }
    }
}








