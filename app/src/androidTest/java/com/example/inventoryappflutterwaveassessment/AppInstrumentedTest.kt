package com.example.inventoryappflutterwaveassessment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view.MyItemRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AppInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val testAccountEmail = "aaa@gmail.com"
    private val testAccountPassword = "password"

    @Test
    fun startInstrumentedTest(){
        viewAddedItems()
    }

    @Test
    fun createAccount(){
        onView(withId(R.id.username)).perform(typeText(testAccountEmail), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText(testAccountPassword), closeSoftKeyboard())
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

        onView(withId(R.id.itemNameInput)).check(matches(withText("${name}updated Name")))
        onView(withId(R.id.itemDescInput)).check(matches(withText("${desc}updated Desc")))
        onView(withId(R.id.itemPriceInput)).check(matches(withText("9.6")))
        onView(withId(R.id.itemQtyInput)).check(matches(withText("101")))

        onView(withId(R.id.del_btn)).perform(click())
        onView(withText("No")).perform(click())

        onView(withId(R.id.del_btn)).perform(click())
        onView(withText("Okay")).perform(click())

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