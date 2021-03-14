package app.bluepay.domain

import app.bluepay.domain.helper.ErrorHandlerImpl
import app.bluepay.domain.helper.PixRepositoryImpl
import compre_e_instale.android.domain.helper.Result
import compre_e_instale.android.domain.usecase.pix.NewOnPixUseCase
import compre_e_instale.android.domain.usecase.pix.NewOnPixUseCaseImpl
import compre_e_instale.android.domain.usecase.pix.PixFeesUseCase
import compre_e_instale.android.domain.usecase.pix.PixFeesUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.roundToInt

class PixTest {

    private val pixRepository = PixRepositoryImpl()
    private val errorHandler = ErrorHandlerImpl()

    private val newOnPixUseCase: NewOnPixUseCase = NewOnPixUseCaseImpl(pixRepository, errorHandler)
    private val pixFeesUseCase: PixFeesUseCase = PixFeesUseCaseImpl(pixRepository, errorHandler)

    @Test
    fun isValidNewOnPix(){
        runBlockingTest {
            val isNewOnPix = newOnPixUseCase.isNewOnPix().first()
            assertTrue(isNewOnPix is Result.Success)
            assertTrue(((isNewOnPix as Result.Success).data))
        }
    }

    @Test
    fun isValidOldOnPix(){
        runBlockingTest {
            newOnPixUseCase.updateNewOnPix(false)
            val isNewOnPix = newOnPixUseCase.isNewOnPix().first()
            assertTrue(isNewOnPix is Result.Success)
            assertFalse(((isNewOnPix as Result.Success).data))
        }
    }

    @Test
    fun isValidGetPixFees(){
        runBlockingTest {
            val result = pixFeesUseCase.getPixFees()
            assertTrue(result is Result.Success)
            val pixFees = (result as Result.Success).data
            assertEquals(pixFees.physicalPersonPaymentFee.roundToInt(), 0)
            assertEquals(pixFees.legalPersonPaymentFee.roundToInt(), 1)
        }
    }
}