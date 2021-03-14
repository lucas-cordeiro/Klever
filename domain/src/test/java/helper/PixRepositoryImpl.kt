package app.bluepay.domain.helper

import compre_e_instale.android.domain.model.pix.PixFees
import compre_e_instale.android.domain.model.pix.PixKey
import compre_e_instale.android.domain.model.pix.PixPayload
import compre_e_instale.android.domain.repository.PixRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PixRepositoryImpl : PixRepository {

    private val _newOnPix: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override fun isNewOnPix() = _newOnPix

    override suspend fun updateNewOnPix(newOnPix: Boolean) {
        _newOnPix.value = newOnPix
    }

    override fun isNewOnKeys(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNewOnKeys(newOnKeys: Boolean) {
        TODO("Not yet implemented")
    }


    override suspend fun getPixFees(): PixFees {
        return PixFees(
                legalPersonPaymentFee = 0.01,
                legalPersonReceivementFee = 0.01
        )
    }

    override suspend fun getPixPayloadByQRCode(userId: Long, bankAccountId: String, qrCode: String): PixPayload? {
        TODO("Not yet implemented")
    }

    override suspend fun addPixKey(userId: Long, bankAccountId: String, pixKey: PixKey): PixKey {
        TODO("Not yet implemented")
    }

    override suspend fun claimPixKey(userId: Long, bankAccountId: String, pixKey: PixKey): PixKey {
        TODO("Not yet implemented")
    }

    override suspend fun updateClaimedPixKey(userId: String, claimed: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getRandomPix(userId: Long): String {
        TODO("Not yet implemented")
    }

    override suspend fun removePixKey(userId: Long, bankAccountId: String, pixKeyId: String, authCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removePixKeyLocal(pixKeyId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getPixKeys(userId: Long, bankAccountId: String): List<PixKey>? {
        TODO("Not yet implemented")
    }

    override suspend fun getPixKeysByBankAccountIdLocal(bankAccountId: String): Flow<List<PixKey?>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePixKeysLocal(bankAccountId: String, keys: List<PixKey>) {
        TODO("Not yet implemented")
    }
}