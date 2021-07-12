package id.co.kajianpro.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.data.model.Waktu
import id.co.kajianpro.utils.Converters

@Database(
    entities =[Kajian::class, Waktu::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    companion object{
        @Volatile
        private var instance: AppDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "kajian_db.db"
            ).allowMainThreadQueries().build()

    }

    abstract fun databaseDao(): DatabaseDao

}