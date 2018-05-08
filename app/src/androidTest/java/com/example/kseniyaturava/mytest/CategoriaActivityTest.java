package com.example.kseniyaturava.mytest;

import android.support.test.espresso.DataInteraction;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CategoriaActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        //Check click on Item from CategoriaActivityTest

        //MainActivity click on Categorias: works correctly
        ViewInteraction appCompatImageView = onView(
                allOf(ViewMatchers.withId(R.id.img_drama), withContentDescription("img_category"),
                        childAtPosition(
                                allOf(withId(R.id.layout_drama),
                                        childAtPosition(
                                                withId(R.id.scroll_category),
                                                0)),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        // CategoriaActivityTest, click on Item: Movie is opened correctly

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.layout_container),
                                1)))
                .atPosition(3);
        linearLayout.perform(click());





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
