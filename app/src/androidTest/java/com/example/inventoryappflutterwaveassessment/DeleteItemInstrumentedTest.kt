package com.example.inventoryappflutterwaveassessment

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view.MyItemRecyclerViewAdapter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DeleteItemInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createAccount(){
        onView(withId(R.id.username)).perform(typeText("aaa@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.login)).perform(click())
    }

    private fun addItem(name: String, desc:String, price: String, qty: String){
        onView(withId(R.id.floatingActionButton2)).perform(click())
        onView(withId(R.id.itemNameInput)).perform(typeText(name), closeSoftKeyboard())
        onView(withId(R.id.itemDescInput)).perform(typeText(desc), closeSoftKeyboard())
        onView(withId(R.id.itemPriceInput)).perform(typeText(price), closeSoftKeyboard())
        onView(withId(R.id.itemQtyInput)).perform(typeText(qty), closeSoftKeyboard())
        onView(withId(R.id.add_button)).perform(click())
    }

    private fun confirmContentEditAndDelete(position: Int, name: String, desc:String, price: String, qty: String){
        onView(withId(R.id.items_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MyItemRecyclerViewAdapter.ViewHolder>(position, click()))

        onView(withId(R.id.itemNameInput)).check(matches(withText(name)))
        onView(withId(R.id.itemDescInput)).check(matches(withText(desc)))
        onView(withId(R.id.itemPriceInput)).check(matches(withText(price)))
        onView(withId(R.id.itemQtyInput)).check(matches(withText(qty)))

        onView(withId(R.id.itemNameInput)).perform(typeText("updated Name"), closeSoftKeyboard())
        onView(withId(R.id.itemDescInput)).perform(typeText("updated Desc"), closeSoftKeyboard())
        onView(withId(R.id.itemPriceInput)).perform(typeText("00.00"), closeSoftKeyboard())
        onView(withId(R.id.itemQtyInput)).perform(typeText("1"), closeSoftKeyboard())

        onView(withId(R.id.save_btn)).perform(click())

        onView(withId(R.id.items_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MyItemRecyclerViewAdapter.ViewHolder>(position, click()))

        onView(withId(R.id.itemNameInput)).check(matches(withText("Name Test1updated Name")))
        onView(withId(R.id.itemDescInput)).check(matches(withText("Apt Description 1updated Desc")))
        onView(withId(R.id.itemPriceInput)).check(matches(withText("9.600.00")))
        onView(withId(R.id.itemQtyInput)).check(matches(withText("101")))

        onView(withId(R.id.del_btn)).perform(click())

        onView(withText("No")).perform(click())
        onView(withText("Yes")).perform(click())

    }

    @Test
    fun addItems(){
        createAccount()
        addItem("Name Test1", "Apt Description 1", "9.6", "10")
        addItem("Name Test2 ", "Apt Description 2", "99.66", "20")
        addItem("Name Test3", "Apt Description 3", "999.6", "30")
        addItem("Name Test 4", "Apt Description 4", "9.666", "40")
    }

    @Test
    fun viewAddedItems(){
        addItems()
        confirmContentEditAndDelete(0,"Name Test1", "Apt Description 1", "9.6", "10")

    }

}