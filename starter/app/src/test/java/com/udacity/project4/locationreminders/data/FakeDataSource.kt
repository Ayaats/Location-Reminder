package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import java.util.LinkedHashMap

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {
    //as that is A fake Repo so we have to set the errors manually as its a fake data
    private var shouldReturnError = false
    //here we use the function to change the value of a flag to use it the Handle errors that can happen to the schema or the data
    fun setReturnError(value: Boolean) {
        //it is a kind of error handling but manually as we are testing fake data
        shouldReturnError = value
    }

    private val fakeData: LinkedHashMap<String, ReminderDTO> = LinkedHashMap()

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (shouldReturnError) {
            return Result.Error("Test exception")
        }

        return Result.Success(fakeData.values.toList())
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        fakeData[reminder.id] = reminder
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if (shouldReturnError) {
            return Result.Error("An unknown error occurred!")
        }

        val reminder = fakeData[id]
        if (reminder != null) {
            return Result.Success(reminder)
        }
        return Result.Error("Reminder not found!")
    }

    override suspend fun deleteAllReminders() {
        fakeData.clear()
    }
}