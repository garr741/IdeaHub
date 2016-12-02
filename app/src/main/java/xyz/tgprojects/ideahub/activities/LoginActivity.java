package xyz.tgprojects.ideahub.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.tgprojects.ideahub.R;

public class LoginActivity extends AppCompatActivity {

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

    @BindView(R.id.logged_in_view)
    TextView loggedInText;

    @BindView(R.id.login_action_button)
    Button loginActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
