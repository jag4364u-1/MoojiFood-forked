package com.mooji.cod.moojifood.model

import androidx.room.*

interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)

    // میتوانیم در ایجا شخصی سازی کنیم مثلا: منتها چیز هایی را باید بنویسیم که عمومیت داره اینجا فقط مربوط به FoodDao هست
    //@Query("select * from table_food order by id desc")
    //fun getAllFood(): List<Food>

}

@Dao
interface FoodDao : BaseDao<Food> {
    //میشود بجای استفاده از INSERT V UPDATE از این استفاده کرد
    // و جایی که ایتم وجود داشت جایگذاری را انجام میدهد
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(food: Food)


//    @Insert
//    fun insertFood(food: Food) //این تابع را پاک میکنم چون در BaseDao وجود دارد

    @Insert
    fun insertAllFood(data: ArrayList<Food>)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllData()

    @Query("select * from table_food order by id desc")
    fun getAllFood(): List<Food>

    @Query(
        "select * from table_food " +
                "where txtSubject like '%' || :searching || '%'"
    )
    fun searchFood(searching: String): List<Food>
}


//نکته مهم در اینجا این است که اگر query بزنیم تابع تعریف شده ما باید چیزی را برگرداند اما
//در حالتی که خودش annotation تعریف شده دارد مثل @Delete نیازی نیست تابع چیزی را برگرداند