package xyz.tgprojects.ideahub.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import xyz.tgprojects.ideahub.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity implements EditText.OnEditorActionListener{

    @BindView(R.id.login_toolbar)
    Toolbar toolbar;

    @BindView(R.id.logged_in_view)
    LinearLayout loggedInView;

    @BindView(R.id.login_view_layout)
    LinearLayout loginLayout;

    @BindView(R.id.email_input)
    EditText emailInput;

    @BindView(R.id.password_input)
    EditText passwordInput;

    @BindView(R.id.confirm_password_input)
    EditText confirmPasswordInput;

    @BindView(R.id.forgot_password_button)
    Button forgotPasswordButton;

    @BindView(R.id.login_view_switcher_button)
    Button switchViewButton;

    @BindView(R.id.logged_in_textview)
    TextView loggedInText;

    @BindView(R.id.login_action_button)
    Button loginActionButton;

    private boolean isLogin = true;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseInit();
        screenInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void firebaseInit() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                screenInit();
            }
        };
    }

    private void screenInit() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        loggedInView.setVisibility(user == null ? GONE : VISIBLE);
        loginLayout.setVisibility(user == null ? VISIBLE : GONE);
        if (user == null) {
            loginActionButton.setText(isLogin ? R.string.login : R.string.register);
            confirmPasswordInput.setVisibility(isLogin ? GONE : VISIBLE);
            forgotPasswordButton.setVisibility(isLogin ? VISIBLE : GONE);
            switchViewButton.setText(isLogin ? R.string.to_create_account : R.string.to_login);
            toolbar.setTitle(isLogin ? R.string.login : R.string.register);
        } else {
            toolbar.setTitle(R.string.my_account);
        }
        passwordInput.setOnEditorActionListener(this);
        confirmPasswordInput.setOnEditorActionListener(this);
    }

    @OnClick(R.id.login_view_switcher_button) void onSwitchViewsClicked() {
        isLogin = !isLogin;
        screenInit();
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {

            return true;
        }
        return false;
    }
}
