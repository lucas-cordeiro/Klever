package app.bluepay.domain.helper

import compre_e_instale.android.domain.model.helper.Helper
import compre_e_instale.android.domain.repository.HelperRepository
import kotlinx.coroutines.flow.*

class HelperRepositoryImpl : HelperRepository {
    private var currentHelperId: Long? = 1L

    private var helpersDatabase: MutableStateFlow<MutableList<Helper>> = MutableStateFlow(ArrayList())

    private val helpersNetwork = listOf(
        Helper(
            id = 1L,
            firstName = "Lucas",
            lastName = "Cordeiro"
        )
    )

    override suspend fun updateCurrentHelperId(helperId: Long?) {
        currentHelperId = helperId
    }

    override suspend fun getCurrentHelperId() = currentHelperId

    override suspend fun getHelperByIdFromDatabase(helperId: Long) = helpersDatabase.map { it.firstOrNull { it.id == helperId } }

    override suspend fun getHelperByIdFromNetwork(helperId: Long) = helpersNetwork.firstOrNull { it.id == helperId }

    override suspend fun updateHelperToDatabase(helper: Helper): Helper {
        val helpers = helpersDatabase.first()
        helpers[helpers.indexOfFirst { it.id == helper.id }] = helper
        helpersDatabase.value = helpers
        return helper
    }
}