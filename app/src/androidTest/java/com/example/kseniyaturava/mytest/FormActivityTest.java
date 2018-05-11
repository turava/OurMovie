package com.example.kseniyaturava.mytest;


import android.app.Activity;
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
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FormActivityTest {

    /*
        This Test doesn't need login registration, so it starts in FormActivity directly showing all fields to be filled
    */

    @Rule
    public ActivityTestRule<FormActivity> mActivityTestRule = new ActivityTestRule<>(FormActivity.class);

    @Test
    public void formActivityTest() {

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Fill all fields except one
        //Title

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etTitulo),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                2)));
        appCompatEditText2.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Year

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etAño),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                4)));
        appCompatEditText3.perform(scrollTo(), replaceText("1965"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Genre of the movie

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.listaGenero),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                6)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Director

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.etDirector),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                9)));
        appCompatEditText5.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.etDirector), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                9)));
        appCompatEditText6.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Actor 1

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.etActor1),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                11)));
        appCompatEditText7.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.etActor1), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                11)));
        appCompatEditText8.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Actor 2

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.etActor2),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                13)));
        appCompatEditText9.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.etActor2), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                13)));
        appCompatEditText10.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Actor 3

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.etActor3),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                15)));
        appCompatEditText11.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.etActor3), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                15)));
        appCompatEditText12.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Actor 4

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.etActor4),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                17)));
        appCompatEditText13.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.etActor4), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                17)));
        appCompatEditText14.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Leave the description field empty and press Button Send -> receive message error

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.sendForm), withText("Enviar"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                20)));
        appCompatButton2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Fill the empty field description

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.etDescripcion),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                19)));
        appCompatEditText15.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.etDescripcion),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                19)));
        appCompatEditText16.perform(scrollTo(), replaceText("a"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Press Button Send -> message ok, the new movie is in the database

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.sendForm), withText("Enviar"),
                        childAtPosition(
                                allOf(withId(R.id.layout_content_vert),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                20)));
        appCompatButton3.perform(scrollTo(), click());

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
