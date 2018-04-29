package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.security.AccessController.getContext;


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


    private GoogleApiClient googleApiClient;

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseUser mFirebaseUser;
    private CardView mCardView;

    private GoogleSignInResult result;
    String userEmail;
    String username;
    String userE;
    String usernama;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();


        setNavigationViewListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setLogo(R.drawable.napkeennlogoforhome);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        authListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

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

        if (isOnline()) {
        } else {
            Toast.makeText(Home.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

        }



    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        photoImageView = (ImageView) header.findViewById(R.id.imageUser);
        emailTextView = (TextView) header.findViewById(R.id.emailnavbar);

        nameTextView = (TextView) header.findViewById(R.id.NamaUserDrawer);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Home.this);
        if (user != null) {
            userEmail = user.getEmail();
            username = userEmail.substring(0, userEmail
                    .indexOf("@"));
            if (user.getDisplayName() != null) {
                nameTextView.setText(user.getDisplayName());
            } else {
                nameTextView.setText(username);
            }
            photoImageView.setImageURI(user.getPhotoUrl());

        } else if (account != null) {
            nameTextView.setText(account.getDisplayName());
            photoImageView.setImageURI(account.getPhotoUrl());
        } else {
            nameTextView.setText(user.getDisplayName());
            photoImageView.setImageURI(user.getPhotoUrl());

        }
    }





        @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                break;
            case R.id.nav_nearby:
                Intent t = new Intent(Home.this,TempatMakanTerdekat.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(t);
                break;
            case R.id.nav_daerah:
                Intent te = new Intent(Home.this, LocationSpinner.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(te);
                break;
            case R.id.nav_tempat_terbaik:
                Intent tempatterbaik = new Intent(Home.this, RatingTertinggi.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(tempatterbaik);
                break;
            case R.id.nav_bantuan:
                Intent bantuan = new Intent(Home.this, Bantuan.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(bantuan);
                break;
            case R.id.nav_tentang:
                Intent tentang = new Intent(Home.this, Tentang.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(tentang);
                break;
            case R.id.nav_tambah_restoran:
                Intent tmbhrestoran = new Intent(Home.this, TambahRestoran.class);
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(tmbhrestoran);
                break;
            case R.id.nav_pengaturan:
                Intent ubahpassword = new Intent(Home.this, Pengaturan.class);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                ;
                startActivity(ubahpassword);
                break;
            case R.id.nav_Keluar:
                new AlertDialog.Builder(this)
                        .setTitle("Keluar")
                        .setMessage("Anda yakin ingin keluar?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (isOnline()) {
                                    signOut();
                                } else {
                                    Toast.makeText(Home.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

                break;
            default:
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        return super.onCreateOptionsMenu(menu);
    }


    //sign out method
    public void signOut() {
        mAuth.signOut();

        //  mGoogleSignInClient.signOut();
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
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

        } else {
            goLogInScreen();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, Search.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    public void toUserProfil(View v) {
        Intent intent = new Intent(Home.this, UserProfil.class);

        startActivity(intent);
        //something TODO

    }
    public void toSearch(View v) {
        Intent search = new Intent(Home.this, Search.class);

        startActivity(search);
        //something TODO

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

    }

}








