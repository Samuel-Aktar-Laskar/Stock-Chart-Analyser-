package com.example.tradingai.business.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant


@RunWith(AndroidJUnit4::class)
@SmallTest
class stock_database_test {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StockDatabase
    private lateinit var dao: StockDao

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StockDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.stockDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addStockInDatabase()= runBlockingTest{
        val stock = StockCacheEntity(
            symbol = "IFF",
            name = "Tata MOtors",
            currency = "Dollar",
            marketClose = "at 323",
            marketOpen = "at 234",
            matchScore = "23",
            region = "assam",
            timezone = "kjstdklf",
            type = "fdskjfklds:"
        )

        dao.InsertStock(stock)

        delay(250)
        val allStocks = dao.GetWatchlistStoks()

        assertThat(allStocks).contains(stock)
    }


}