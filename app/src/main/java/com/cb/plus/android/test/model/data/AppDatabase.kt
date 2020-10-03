package com.cb.plus.android.test.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.utils.TypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ProductData::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "CB_Plus_Database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDataBaseCallBack(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


    class AppDataBaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.productDao())
                }
            }
        }

        private fun populateDatabase(productDao: ProductDao) {
            // Delete all content here.
            productDao.deleteAll()

            var word = ProductData(1, ProductModel("Vimto", "https://static.openfoodfacts.org/images/products/501/043/800/5107/front_fr.4.200.jpg", "22/10/20"))
            productDao.insert(word)
            word = ProductData(2, ProductModel("Water", "https://static.openfoodfacts.org/images/products/327/408/000/5003/front_en.574.200.jpg", "23/10/21"))
            productDao.insert(word)
        }
    }
}