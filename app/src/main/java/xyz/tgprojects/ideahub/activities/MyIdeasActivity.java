package xyz.tgprojects.ideahub.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xyz.tgprojects.ideahub.R;
import xyz.tgprojects.ideahub.adapters.IdeaAdapter;
import xyz.tgprojects.ideahub.models.Idea;
import xyz.tgprojects.ideahub.utilities.ApiRoutes;
import xyz.tgprojects.ideahub.utilities.ApiService;

public class MyIdeasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener{

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.activity_my_ideas)
    DrawerLayout drawer;

    @BindView(R.id.my_ideas_recyclerview)
    RecyclerView myIdeasRecyclerView;

    private IdeaAdapter ideaAdapter;
    private CompositeSubscription subs;
    private ApiRoutes apiRoutes;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ideas);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.my_ideas);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        apiRoutes = ApiService.getApi();
        subs = new CompositeSubscription();
        myIdeasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ideaAdapter = new IdeaAdapter();
        myIdeasRecyclerView.setAdapter(ideaAdapter);
        getMyIdeas();
        authStateListener = this;
        screenInit();
    }

    private void getMyIdeas() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Snackbar.make(getView(), "You must be signed in", Snackbar.LENGTH_SHORT).show();
            return;
        }
        subs.add(apiRoutes.getUserIdeas(user.getUid())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Idea>>() {
                @Override
                public void call(List<Idea> ideas) {
                    ideaAdapter.swapData(ideas);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.d("MyIdeasActivity", throwable.getMessage());
                }
            })
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private View getView() {
        return findViewById(android.R.id.content);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_idea_hub) {
            startActivity(new Intent(this, IdeaHubActivity.class));
        } else if (id == R.id.menu_my_ideas) {

        } else if (id == R.id.menu_my_favorites) {
            startActivity(new Intent(this, MyFavoritesActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void screenInit() {
        View headerView = navigationView.getHeaderView(0);
        TextView navBarHeaderText = (TextView) headerView.findViewById(R.id.nav_bar_header_text);
        if (firebaseUser != null) {
            navBarHeaderText.setText(firebaseUser.getEmail());
        } else {
            navBarHeaderText.setText(R.string.to_login);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        screenInit();
    }
}
