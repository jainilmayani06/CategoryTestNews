package com.nsv.categorytestnews.db

import androidx.room.TypeConverter
import com.nsv.categorytestnews.models.Source


class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}