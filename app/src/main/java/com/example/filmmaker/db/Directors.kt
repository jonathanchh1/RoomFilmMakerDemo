package com.example.filmmaker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "table_director", indices = [Index(value = ["full_name"], unique = true)])
data class Directors(@ColumnInfo(name = "full_name") var fullname : String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did")
    var id : Int = 0

}
