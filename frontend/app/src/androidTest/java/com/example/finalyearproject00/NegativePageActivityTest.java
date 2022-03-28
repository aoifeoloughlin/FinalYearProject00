package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;
import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NegativePageActivityTest {
    MainActivity mainActivity;
    PositivePageActivity positivePageActivity;
    NegativePageActivity negativePageActivity;

    @Rule
    public ActivityTestRule<NegativePageActivity> negativePageActivityTestRule =
            new ActivityTestRule(NegativePageActivity.class);


    @Before
    public void setUp() throws Exception {
        negativePageActivity = negativePageActivityTestRule.getActivity();
    }

    @Test
    public void addNegativeExperiences(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PositivePageActivity.class.getName(), null, false);
        Button addExperience = (Button) negativePageActivity.findViewById(R.id.addNegativeExp);
        Button removeExperience = (Button) negativePageActivity.findViewById(R.id.removeNegativeExp);
        Button submitExperiences = (Button) negativePageActivity.findViewById(R.id.submitNegativeExp);
        String testExp1 = "Sad Testing Experience 01";


        negativePageActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText exp1 = negativePageActivity.findViewById(R.id.NegativeExp01);
                exp1.setText(testExp1);
                addExperience.performClick();
                addExperience.performClick();
                removeExperience.performClick();
                removeExperience.performClick();
                submitExperiences.performClick();
            }
        });
        positivePageActivity = (PositivePageActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(positivePageActivity);
    }


    @Rule
    public ActivityTestRule<PositivePageActivity> positivePageActivityTestRule =
            new ActivityTestRule(PositivePageActivity.class);

    @Test
    public void addPositiveExperiences(){
        positivePageActivity = positivePageActivityTestRule.getActivity();
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


