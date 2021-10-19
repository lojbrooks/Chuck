package com.lojbrooks.chuck

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import com.lojbrooks.chuck.data.remote.JokeDto
import com.lojbrooks.chuck.data.remote.JokesResponseDto
import com.lojbrooks.chuck.fakes.FakeChuckNorrisApi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import javax.inject.Inject


@HiltAndroidTest
class JokeListTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Inject
    lateinit var api: FakeChuckNorrisApi


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {
        scenario.close()
    }

    @Test
    fun shouldShowJokes() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.onNodeWithText("Once death had a near Chuck Norris experience.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Love does not hurt. Chuck Norris does.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Movie trivia: The movie \"Invasion U.S.A\" is, in fact, a documentary.").assertIsDisplayed()
    }

    @Test
    fun whenFetchJokesFails_shouldShowError() {
        api.getJokesResponse = Response.error(500, "".toResponseBody())
        scenario = ActivityScenario.launch(MainActivity::class.java)

        composeTestRule.onNodeWithText("Failed to fetch jokes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try again").assertIsDisplayed()
    }

    @Test
    fun whenClickTryAgainAndFetchJokesSucceeds_shouldShowJokes() {
        api.getJokesResponse = Response.error(500, "".toResponseBody())
        scenario = ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.onNodeWithText("Try again").assertIsDisplayed()

        api.getJokesResponse = Response.success(JokesResponseDto("", listOf(
            JokeDto(1, "Chuck Norris is the reason why Waldo is hiding.")
        )))
        composeTestRule.onNodeWithText("Try again").performClick()

        composeTestRule.onNodeWithText("Chuck Norris is the reason why Waldo is hiding.").assertIsDisplayed()
    }

    @Test
    fun whenClickTryAgainAndFetchJokesFails_shouldShowError() {
        api.getJokesResponse = Response.error(500, "".toResponseBody())
        scenario = ActivityScenario.launch(MainActivity::class.java)

        composeTestRule.onNodeWithText("Try again").performClick()

        composeTestRule.onNodeWithText("Failed to fetch jokes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try again").assertIsDisplayed()    }

}
