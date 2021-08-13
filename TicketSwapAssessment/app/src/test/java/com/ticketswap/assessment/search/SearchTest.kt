package com.ticketswap.assessment.search

import android.security.keystore.UserNotAuthenticatedException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ticketswap.assessment.CoroutineTestRule
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.api.Resource
import com.ticketswap.assessment.features.search.SearchQuery
import com.ticketswap.assessment.features.search.SearchViewModel
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment.MediaType.ARTIST
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment.MediaType.TRACK
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.models.SearchResponse
import com.ticketswap.assessment.search.data.getSearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class SearchTest {

    private lateinit var viewModel: SearchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TestSearchRepository

    @Mock
    lateinit var responseObserver: Observer<Resource<out SearchResponse?>>

    @Mock
    lateinit var trackObserver: Observer<List<Item>>

    @Mock
    lateinit var artistObserver: Observer<List<Item>>

    @Before
    fun setup() {
        viewModel = SearchViewModel(repository)
        viewModel.searchResult.observeForever(responseObserver)
        viewModel.subscribeTo(TRACK).observeForever(trackObserver)
        viewModel.subscribeTo(ARTIST).observeForever(artistObserver)
    }

    @Test
    fun search_livedata_success_track_artist() {
        val data = getSearchResponse()
        val success = Resource.Success(data)
        val loading = Resource.Loading<SearchResponse>()

        coroutineRule.testDispatcher.runBlockingTest {
            `when`(repository.searchSong(SearchQuery("song", 0, setOf(ARTIST, TRACK))))
                .thenReturn(flow {
                    emit(loading)
                    emit(success)
                })

            viewModel.search("song")
            verify(responseObserver).onChanged(loading)
            verify(responseObserver).onChanged(success)
            verifyNoMoreInteractions(responseObserver)

            // Reset data on search
            verify(trackObserver).onChanged(argThat(ItemsMatcher(listOf())))
            verify(artistObserver).onChanged(argThat(ItemsMatcher(listOf())))

            // Getting actual data
            verify(trackObserver).onChanged(argThat(ItemsMatcher(success.data!!.tracks!!.items)))
            verify(artistObserver).onChanged(argThat(ItemsMatcher(success.data!!.artists!!.items)))

            verifyNoMoreInteractions(trackObserver)
            verifyNoMoreInteractions(artistObserver)
        }
    }

    @Test
    fun search_livedata_failed() {
        val loading = Resource.Loading<SearchResponse>()
        val error = Resource.Error<SearchResponse>(Action.FAILED, IOException("Unknown"))

        coroutineRule.testDispatcher.runBlockingTest {
            `when`(repository.searchSong(SearchQuery("song", 0, setOf(ARTIST, TRACK))))
                .thenReturn(flow {
                    emit(loading)
                    emit(error)
                })

            viewModel.search("song")
            verify(responseObserver).onChanged(loading)
            verify(responseObserver).onChanged(error)
            verifyNoMoreInteractions(responseObserver)

            // Reset data on search
            verify(trackObserver).onChanged(argThat(ItemsMatcher(listOf())))
            verify(artistObserver).onChanged(argThat(ItemsMatcher(listOf())))

            verifyNoMoreInteractions(trackObserver)
            verifyNoMoreInteractions(artistObserver)
        }
    }

    @Test
    fun search_livedata_unauthorised() {
        val loading = Resource.Loading<SearchResponse>()
        val error = Resource.Error<SearchResponse>(
            Action.UNAUTHORISED,
            UserNotAuthenticatedException("Not authorised")
        )

        coroutineRule.testDispatcher.runBlockingTest {
            `when`(repository.searchSong(SearchQuery("song", 0, setOf(ARTIST, TRACK))))
                .thenReturn(flow {
                    emit(loading)
                    emit(error)
                })

            viewModel.search("song")
            verify(responseObserver).onChanged(loading)
            verify(responseObserver).onChanged(error)
            verifyNoMoreInteractions(responseObserver)

            // Reset data on search
            verify(trackObserver).onChanged(argThat(ItemsMatcher(listOf())))
            verify(artistObserver).onChanged(argThat(ItemsMatcher(listOf())))

            verifyNoMoreInteractions(trackObserver)
            verifyNoMoreInteractions(artistObserver)
        }
    }

    class ItemsMatcher(private val expected: List<Item>?) : ArgumentMatcher<List<Item>> {
        override fun matches(argument: List<Item>?): Boolean {
            if (expected == null && argument == null) return true
            if (expected == null) return false
            if (argument == null) return false
            if (expected.size != argument.size) return false
            for (i in expected.indices) {
                if (expected[i].id != argument[i].id) return false
            }
            return true
        }
    }

    class ResourceStatusMatcher<T>(private val action: Resource<T>) : ArgumentMatcher<Resource<T>> {
        override fun matches(argument: Resource<T>?): Boolean {
            argument ?: return false
            return argument.action == action.action
        }

    }

}