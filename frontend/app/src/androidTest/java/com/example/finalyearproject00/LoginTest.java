package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import android.app.Instrumentation.ActivityMonitor;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    String testEmail = "aoifesummergrinds@gmail.com";
    String testPassword = "flowers";
    LoginActivity loginactivity;
    MainActivity mainActivity;
    PositivePageActivity positivePageActivity;
    NegativePageActivity negativePageActivity;
    ViewAllPosActivity viewAllPosActivity;
    ViewAllNegActivity viewAllNegActivity;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule(LoginActivity.class);
    @Before
    public void setUp() throws Exception {

        loginactivity = loginActivityActivityTestRule.getActivity();
    }

    @Test(timeout=6000)
     public void logUserIn() {
        EditText testUsername = loginactivity.findViewById(R.id.username);
        testUsername.setText(testEmail);
        EditText testP = loginactivity.findViewById(R.id.password);
        testP.setText(testPassword);

        assertTrue(testUsername.getText().toString().equals("aoifesummergrinds@gmail.com"));
        assertTrue(testP.getText().toString().equals("flowers"));
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        final Button button = (Button) loginactivity.findViewById(R.id.submitLogin);
        loginactivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
         mainActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(mainActivity);
    }

   /* @Rule
    public ActivityTestRule<PositivePageActivity> positivePageActivityActivityTestRule =
            new ActivityTestRule(PositivePageActivity.class);
*/
    @Test
    public void clickAllPositiveExperiencesView(){
        //positivePageActivity = positivePageActivityActivityTestRule.getActivity();
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PositivePageActivity.class.getName(), null, false);

        final Button posbutton = (Button) mainActivity.findViewById(R.id.posExpView);
                // click button and open next activity.
                posbutton.performClick();

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        positivePageActivity = (PositivePageActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(positivePageActivity);

        //add a few experiences
    }


    @Test
    public void clickAllNegativeExperiencesView(){

    }



}