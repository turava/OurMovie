package com.example.kseniyaturava.mytest;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {

        // have1 error, and 2 correct click

        /*
        Error onClick "Novedades"
        android.support.test.espresso.AmbiguousViewMatcherException: '(with id: com.example.kseniyaturava.mytest:id/image_view and with content description:
        is "img_list" and Child at position 0 in parent Child at position 0 in parent with class name: is "android.support.v7.widget.CardView" and is displayed on the screen to the user)'
         matches multiple views in the hierarchy.
        Problem views are marked with '****MATCHES****' below.
         */

       /* ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_view), withContentDescription("img_list"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
              appCompatImageView.perform(click());



        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
       appCompatImageButton.perform(scrollTo(),click());

*/


        //MaincActivity click on "Interactua", intent ForoActivity is opened correctly
        ViewInteraction appCompatImageView2 = onView(
                allOf(ViewMatchers.withId(R.id.img_new1), withContentDescription("TODO"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)));
        appCompatImageView2.perform(scrollTo(), click());

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Click back to parent Activity
        pressBack();

       try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Click back to parent Activity
        pressBack();

        //MainActivity, click on Categorias: works correctly

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.img_comedy), withContentDescription("img_category"),
                        childAtPosition(
                                allOf(withId(R.id.layout_comedy),
                                        childAtPosition(
                                                withId(R.id.scroll_category),
                                                1)),
                                0)));
        appCompatImageView3.perform(scrollTo(), click());

        //Click back to parent Activity
        pressBack();

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
