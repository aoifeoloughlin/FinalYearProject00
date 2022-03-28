package com.example.finalyearproject00;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertNotNull;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainPageTest {
    MainActivity mainActivity;
    PositivePageActivity positivePageActivity;
    NegativePageActivity negativePageActivity;
    ViewAllPosActivity viewAllPosActivity;
    ViewAllNegActivity viewAllNegActivity;
    PopupActivity popupActivity;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void clickAddPositiveExperiencesView(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PositivePageActivity.class.getName(), null, false);
        Button posbutton = (Button) mainActivity.findViewById(R.id.posExpView);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                posbutton.performClick();
            }
        });
        positivePageActivity = (PositivePageActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(positivePageActivity);
    }

    @Test
    public void clickAddNegativeExperiencesView(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NegativePageActivity.class.getName(), null, false);
        Button negbutton = (Button) mainActivity.findViewById(R.id.negExpView);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                negbutton.performClick();
            }
        });
        negativePageActivity = (NegativePageActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(negativePageActivity);
    }

    @Test
    public void clickAllNegativeExperiencesView(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewAllNegActivity.class.getName(), null, false);
        Button negbutton = (Button) mainActivity.findViewById(R.id.viewAllNeg);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                negbutton.performClick();
            }
        });
        viewAllNegActivity = (ViewAllNegActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(viewAllNegActivity);
    }

    @Test
    public void clickAllPositiveExperiencesView(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewAllPosActivity.class.getName(), null, false);
        Button posbutton = (Button) mainActivity.findViewById(R.id.viewAllPos);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                posbutton.performClick();
            }
        });
        viewAllPosActivity = (ViewAllPosActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(viewAllPosActivity);
    }

    @Test
    public void clickRatioPage(){
        Button ratiobutton = (Button) mainActivity.findViewById(R.id.helpfulResources);
        if(ratiobutton.isShown()){
            Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PopupActivity.class.getName(), null, false);
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ratiobutton.performClick();
                }
            });
            popupActivity = (PopupActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
            assertNotNull(popupActivity);
        }
    }


}
