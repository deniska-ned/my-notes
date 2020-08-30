package com.nedoluzhko.mynotes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nedoluzhko.mynotes.database.WordDao
import com.nedoluzhko.mynotes.database.WordDatabase
import com.nedoluzhko.mynotes.database.WordEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var wordDao: WordDao
    private lateinit var db: WordDatabase
    private lateinit var allWords: LiveData<List<WordEntity>>

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, WordDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        wordDao = db.wordDao
        allWords = wordDao.getAllWords()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertWord() {
        val word = WordEntity(word = "Cake")
        wordDao.insert(word)
        val word2 = WordEntity(word = "Pineapple")
        wordDao.insert(word2)
    }

//    @Test
//    fun useAppContext() {
////         Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.nedoluzhko.mydatabase", appContext.packageName)
//    }

}