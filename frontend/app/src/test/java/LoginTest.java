import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.finalyearproject00.LoginActivity;
import com.example.finalyearproject00.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void logIntoAccount() {
        String testEmail = "aoifesummergrinds@gmail.com";
        String testPassword = "flowers";
        TouchUtils.tapView(LoginActivity.this, findViewById(R.id.username));
        sendKeys(testEmail);
        TouchUtils.tapView(LoginActivity.this, findViewById(R.id.password));
        sendKeys(testPassword);

        
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        // open current activity.
        LoginActivity loginActivity = getActivity();
        Button loginButton = (Button) loginActivity.findViewById(R.id.submitLogin);
        loginActivity.runOnUiThread(new Runnable() {
          @Override                         
          public void run() {
            // click button and open next activity.
            loginButton.performClick();
          }
        });
      
        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        MainActivity mainActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(mainActivity);
        mainActivity.finish();
      }
}

