package com.example.filmmaker.db

import androidx.room.*

@Entity(tableName = "table_film",
    foreignKeys = [ForeignKey(
        entity = Directors::class,
        parentColumns = ["did"],
        childColumns = ["directorId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("directorId"), Index("title")])
 data class Films(@ColumnInfo(name = "directorId") var directorsId: Int,
                 @ColumnInfo(name = "title") var title : String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    var id : Int = 0
}