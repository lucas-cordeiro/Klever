package app.bluepay.domain

import app.bluepay.domain.helper.ErrorHandlerImpl
import app.bluepay.domain.helper.HelperRepositoryImpl
import compre_e_instale.android.domain.error.ErrorEntity
import compre_e_instale.android.domain.helper.Result
import compre_e_instale.android.domain.repository.HelperRepository
import compre_e_instale.android.domain.usecase.helper.GetHelperUseCase
import compre_e_instale.android.domain.usecase.helper.GetHelperUseCaseImpl
import compre_e_instale.android.domain.usecase.helper.UpdateHelperUseCase
import compre_e_instale.android.domain.usecase.helper.UpdateHelperUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HelperTest {
    private val helperRepository: HelperRepository = HelperRepositoryImpl()
    private val errorHandler = ErrorHandlerImpl()

    private val getHelperUseCase: GetHelperUseCase = GetHelperUseCaseImpl(
        helperRepository,
        errorHandler
    )
    private val updateHelperUseCase: UpdateHelperUseCase = UpdateHelperUseCaseImpl(
        helperRepository,
        errorHandler
    )

    @Test
    fun isVaildGetHelper(){
        runBlocking {
            val resultGetHelper = getHelperUseCase.getCurrentHelper().first()
            assertTrue(resultGetHelper is Result.Success)
            val helper = (resultGetHelper as Result.Success).data
            assertEquals(helper.id, 1L)
            assertEquals(helper.firstName, "Lucas")
        }
    }

    @Test
    fun isVaildNotFoundHelper(){
        runBlocking {
            updateHelperUseCase.updateCurrentHelper(null)
            val resultGetHelper = getHelperUseCase.getCurrentHelper().first()
            assertTrue(resultGetHelper is Result.Error)
            val error = (resultGetHelper as Result.Error).error
            assertTrue(error is ErrorEntity.ApiError.NotFound)
        }
    }
}