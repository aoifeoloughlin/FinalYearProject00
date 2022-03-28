package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PositivePageActivityTest {
    MainActivity mainActivity;
    PositivePageActivity positivePageActivity;

    @Rule
    public ActivityTestRule<PositivePageActivity> positivePageActivityTestRule =
            new ActivityTestRule(PositivePageActivity.class);


    @Before
    public void setUp() throws Exception {
        positivePageActivity = positivePageActivityTestRule.getActivity();
    }

    @Test
    public void addPositiveExperiences(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        Button addExperience = (Button) positivePageActivity.findViewById(R.id.addPositiveExp);
        Button removeExperience = (Button) positivePageActivity.findViewById(R.id.removePositiveExp);
        Button submitExperiences = (Button) positivePageActivity.findViewById(R.id.submitPositiveExp);
        String testExp1 = "Happy Testing Experience 01";


        positivePageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText exp1 = positivePageActivity.findViewById(R.id.positiveExp01);
                exp1.setText(testExp1);
                addExperience.performClick();
                addExperience.performClick();
                removeExperience.performClick();
                removeExperience.performClick();
                submitExperiences.performClick();
            }
        });
        mainActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(mainActivity);
    }
}
