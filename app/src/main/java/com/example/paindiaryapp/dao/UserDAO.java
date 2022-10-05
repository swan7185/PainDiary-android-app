package com.example.paindiaryapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.paindiaryapp.model.UserDailyData;
import com.example.paindiaryapp.util.DateConverter;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDAO {

    @TypeConverters(DateConverter.class)
    @Query("SELECT * FROM UserDailyData WHERE userEmail = :email ORDER BY date ASC")
    LiveData<List<UserDailyData>> getAll(String email);

    @Insert
    void insert(UserDailyData userData);

    @TypeConverters(DateConverter.class)
    @Query("SELECT * FROM UserDailyData WHERE userEmail = :email AND date = :date LIMIT 1" )
    public UserDailyData findUserData(String email, Date date);

    @Query("DELETE FROM UserDailyData")
    void deleteAll();

    @Delete
    void delete(UserDailyData userData);

    @Update
    void update(UserDailyData userData);
}
