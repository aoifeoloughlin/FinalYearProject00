package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ViewAllNegActivityTest {
    MainActivity mainActivity;
    ViewAllNegActivity viewAllNegActivity;

    @Rule
    public ActivityTestRule<ViewAllNegActivity> viewAllNegActivityActivityTestRule =
            new ActivityTestRule(ViewAllNegActivity.class);


    @Before
    public void setUp() throws Exception {
        viewAllNegActivity = viewAllNegActivityActivityTestRule.getActivity();
    }

    @Test
    public void addNegativeExperiences(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        Button backToMain = (Button) viewAllNegActivity.findViewById(R.id.leaveResourcePageNeg);
        viewAllNegActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                backToMain.performClick();
            }
        });
        mainActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(mainActivity);
    }

}
