package com.example.kseniyaturava.mytest;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ForoActivityTest {

    /*
        Test doesn't work without login registration
    */

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void foroActivityTest() {
        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.user),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("p"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("p"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btLogin), withText("Log in"),
                        childAtPosition(
                                allOf(withId(R.id.loginForm),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ForoActivity is not available from the bottomNavigation, so we access it through MovieActivity
        //and we will get to MovieActivity through HomeCategoryActivity

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.img_comedy), withContentDescription("img_category"),
                        childAtPosition(
                                allOf(withId(R.id.layout_comedy),
                                        childAtPosition(
                                                withId(R.id.scroll_category),
                                                1)),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Show MovieActivity ok

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.layout_container),
                                1)))
                .atPosition(0);
        linearLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Click on Button foro -> go to ForoActivity ok

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_foro), withText("Foro"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.

        //Show ForoActivity ok with the comments of that movie

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.input_message),
                        childAtPosition(
                                allOf(withId(R.id.layout_input),
                                        childAtPosition(
                                                withId(R.id.layout_type),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Write a new comment "Hola" in the bottom field and press Button Send

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.input_message),
                        childAtPosition(
                                allOf(withId(R.id.layout_input),
                                        childAtPosition(
                                                withId(R.id.layout_type),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView3.perform(replaceText("Hola"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.btSend), withContentDescription("icon_sendMessage"),
                        childAtPosition(
                                allOf(withId(R.id.layout_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Press Button reply of a comment already written: this doesn't work correctly because the button may have a
        //different position with respect to its parent and this creates an ambiguity error. So we leave all this code
        //commented but with the steps taken

        /*
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.btReply), withContentDescription("boton"),
                        childAtPosition(
                                allOf(withId(R.id.layout_botones),
                                        childAtPosition(
                                                withId(R.id.layout_postBox),
                                                2)),
                                0),
                        isDisplayed()));
        imageButton.perform(click());
        */

        //WRITE A NEW COMMENT "Bien" AND PRESS BUTTON SEND-REPLY

        /*
        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.btReply), withContentDescription("boton"),
                        childAtPosition(
                                allOf(withId(R.id.layout_botones),
                                        childAtPosition(
                                                withId(R.id.layout_postBox),
                                                2)),
                                0),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction autoCompleteTextView = onView(
                allOf(withId(R.id.input_messageReply),
                        childAtPosition(
                                allOf(withId(R.id.layout_acordeon),
                                        childAtPosition(
                                                withId(R.id.layout_postBox),
                                                3)),
                                1),
                        isDisplayed()));
        autoCompleteTextView.perform(replaceText("Bien"), closeSoftKeyboard());

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.btSendReply), withContentDescription("icon_sendMessage"),
                        childAtPosition(
                                allOf(withId(R.id.layout_acordeon),
                                        childAtPosition(
                                                withId(R.id.layout_postBox),
                                                3)),
                                2),
                        isDisplayed()));
        imageButton3.perform(click());
        */

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Press Button Movie Info on the top menu to go back to MovieActivity

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_info), withContentDescription("TODO"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
