package xyz.tgprojects.ideahub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xyz.tgprojects.ideahub.R;
import xyz.tgprojects.ideahub.adapters.IdeaAdapter;
import xyz.tgprojects.ideahub.models.Idea;
import xyz.tgprojects.ideahub.utilities.ApiRoutes;
import xyz.tgprojects.ideahub.utilities.ApiService;

public class IdeaHubActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.idea_hub_recyclerview)
    RecyclerView ideaHubRecyclerView;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ApiRoutes apiRoutes;
    private CompositeSubscription subs;
    private IdeaAdapter adapter;
    private FirebaseUser firebaseUser;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_hub);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ButterKnife.bind(this);
        apiRoutes = ApiService.getApi();
        subs = new CompositeSubscription();
        headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        toolbar.setTitle(R.string.idea_hub);
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
        authStateListener = this;

        ideaHubRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IdeaAdapter();
        ideaHubRecyclerView.setAdapter(adapter);
        ideaHubRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        screenInit();
        getIdeas();
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

        } else if (id == R.id.menu_my_ideas) {
            startActivity(new Intent(this, MyIdeasActivity.class));
        } else if (id == R.id.menu_my_favorites) {
            startActivity(new Intent(this, MyFavoritesActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getIdeas() {
        subs.add(apiRoutes.getIdeas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Idea>>() {
                    @Override
                    public void call(List<Idea> ideas) {
                        adapter.swapData(ideas);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }

    private void screenInit() {
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
