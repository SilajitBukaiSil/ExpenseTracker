package silajit.com.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;


public final class DefaultIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(SampleSlide.newInstance(R.layout.splash_screen));
        addSlide(SampleSlide.newInstance(R.layout.login_screen));
        addSlide(SampleSlide.newInstance(R.layout.signin_screen));
        addSlide(SampleSlide.newInstance(R.layout.navigation_screen));
        addSlide(SampleSlide.newInstance(R.layout.registration_screen));

        showStatusBar(true);

        setDepthAnimation();

    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
        finish();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
