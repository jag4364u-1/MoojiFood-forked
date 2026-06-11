package com.mooji.cod.moojifood.mainScreen

import com.mooji.cod.moojifood.model.Food
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FoodAdapterTest {

    private lateinit var adapter: FoodAdapter
    private lateinit var data: ArrayList<Food>
    private val fakeFoodEvents = object : FoodAdapter.foodEvents {
        override fun onFoodClicked(food: Food, position: Int) {}
        override fun onFoodLongClicked(food: Food, position: Int) {}
    }

    private fun createFood(
        subject: String = "Pizza",
        price: String = "10",
        distance: String = "2",
        city: String = "Seattle",
        urlImage: String = "https://example.com/image.png",
        numOfRating: Int = 100,
        rating: Float = 4.5f
    ) = Food(
        txtSubject = subject,
        txtPrice = price,
        txtDistance = distance,
        txtCity = city,
        urlImage = urlImage,
        numOfRating = numOfRating,
        rating = rating
    )

    @Before
    fun setUp() {
        data = arrayListOf(
            createFood(subject = "Burger", price = "8"),
            createFood(subject = "Sushi", price = "15"),
            createFood(subject = "Taco", price = "5")
        )
        adapter = spyk(FoodAdapter(data, fakeFoodEvents))
        every { adapter.notifyItemInserted(any()) } just runs
        every { adapter.notifyItemRemoved(any()) } just runs
        every { adapter.notifyItemChanged(any()) } just runs
        every { adapter.notifyDataSetChanged() } just runs
    }

    @Test
    fun getItemCount_returnsDataSize() {
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun getItemCount_emptyList_returnsZero() {
        val emptyAdapter = FoodAdapter(arrayListOf(), fakeFoodEvents)
        assertEquals(0, emptyAdapter.itemCount)
    }

    @Test
    fun addFood_insertsAtBeginning() {
        val newFood = createFood(subject = "Pasta", price = "12")

        adapter.addFood(newFood)

        assertEquals(4, adapter.itemCount)
        assertEquals("Pasta", data[0].txtSubject)
    }

    @Test
    fun addFood_shiftsExistingItems() {
        val newFood = createFood(subject = "Pasta")

        adapter.addFood(newFood)

        assertEquals("Pasta", data[0].txtSubject)
        assertEquals("Burger", data[1].txtSubject)
        assertEquals("Sushi", data[2].txtSubject)
        assertEquals("Taco", data[3].txtSubject)
    }

    @Test
    fun removeFood_removesItemAndDecrementsCount() {
        val foodToRemove = data[1]

        adapter.removeFood(foodToRemove, 1)

        assertEquals(2, adapter.itemCount)
        assertFalse(data.contains(foodToRemove))
    }

    @Test
    fun removeFood_firstItem() {
        val foodToRemove = data[0]

        adapter.removeFood(foodToRemove, 0)

        assertEquals(2, adapter.itemCount)
        assertEquals("Sushi", data[0].txtSubject)
    }

    @Test
    fun removeFood_lastItem() {
        val foodToRemove = data[2]

        adapter.removeFood(foodToRemove, 2)

        assertEquals(2, adapter.itemCount)
        assertEquals("Sushi", data[1].txtSubject)
    }

    @Test
    fun updateFood_replacesItemAtPosition() {
        val updatedFood = createFood(subject = "Updated Burger", price = "20")

        adapter.updateFood(updatedFood, 0)

        assertEquals("Updated Burger", data[0].txtSubject)
        assertEquals("20", data[0].txtPrice)
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun updateFood_middlePosition() {
        val updatedFood = createFood(subject = "Ramen", price = "13")

        adapter.updateFood(updatedFood, 1)

        assertEquals("Burger", data[0].txtSubject)
        assertEquals("Ramen", data[1].txtSubject)
        assertEquals("Taco", data[2].txtSubject)
    }

    @Test
    fun setData_replacesAllItems() {
        val newList = arrayListOf(
            createFood(subject = "Salad", price = "7"),
            createFood(subject = "Soup", price = "6")
        )

        adapter.setData(newList)

        assertEquals(2, adapter.itemCount)
        assertEquals("Salad", data[0].txtSubject)
        assertEquals("Soup", data[1].txtSubject)
    }

    @Test
    fun setData_withEmptyList_clearsData() {
        adapter.setData(arrayListOf())

        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun setData_clearsPreviousData() {
        val newList = arrayListOf(createFood(subject = "Steak"))

        adapter.setData(newList)

        assertEquals(1, adapter.itemCount)
        assertEquals("Steak", data[0].txtSubject)
    }

    @Test
    fun addFood_toEmptyAdapter() {
        val emptyData = arrayListOf<Food>()
        val emptyAdapter = spyk(FoodAdapter(emptyData, fakeFoodEvents))
        every { emptyAdapter.notifyItemInserted(any()) } just runs
        val newFood = createFood(subject = "Waffle")

        emptyAdapter.addFood(newFood)

        assertEquals(1, emptyAdapter.itemCount)
        assertEquals("Waffle", emptyData[0].txtSubject)
    }
}
