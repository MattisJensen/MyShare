package dk.sdu.myshare.model.database.mock

data class MockDBGroup (
    val id: Int,
    var name: String,
    var groupBalance: Double,
    var members: MutableList<Int>,
)