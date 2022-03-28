package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ViewAllPosActivityTest {
    MainActivity mainActivity;
    ViewAllPosActivity viewAllPosActivity;

    @Rule
    public ActivityTestRule<ViewAllPosActivity> viewAllPosActivityActivityTestRule =
            new ActivityTestRule(ViewAllPosActivity.class);


    @Before
    public void setUp() throws Exception {
        viewAllPosActivity = viewAllPosActivityActivityTestRule.getActivity();
    }

    @Test
    public void addNegativeExperiences(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        Button backToMain = (Button) viewAllPosActivity.findViewById(R.id.leaveResourcePageNeg);

        viewAllPosActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                backToMain.performClick();
            }
        });
        mainActivity = (MainActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(mainActivity);
    }
}
