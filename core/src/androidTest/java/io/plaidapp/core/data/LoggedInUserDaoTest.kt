/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.core.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import io.plaidapp.core.designernews.data.users.model.LoggedInUser
import io.plaidapp.test.shared.LiveDataTestUtil

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoggedInUserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var loggedInUserDao: LoggedInUserDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        loggedInUserDao = database.loggedInUserDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetLoggedInUser() {
        val user = LoggedInUser(
            id = 1L,
            displayName = "Loggy L",
            firstName = "Loggy",
            lastName = "Loggerson",
            portraitUrl = "www")
        loggedInUserDao.insertLoggedInUser(user)

        val userFromDb = LiveDataTestUtil.getValue(loggedInUserDao.getLoggedInUser())
        assertEquals(user, userFromDb)
    }

    @Test
    fun replaceLoggedInUser() {
        val user = LoggedInUser(
            id = 1L,
            displayName = "Loggy L",
            firstName = "Loggy",
            lastName = "Loggerson",
            portraitUrl = "www")
        loggedInUserDao.insertLoggedInUser(user)

        val updatedUser = user.copy(displayName = "LL Cool L")
        loggedInUserDao.insertLoggedInUser(updatedUser)

        val userFromDb = LiveDataTestUtil.getValue(loggedInUserDao.getLoggedInUser())
        assertEquals(updatedUser, userFromDb)
    }
}
