package dk.sdu.myshare.data.database.mock.tables

data class MockDBGroup (
    val id: Int,
    var name: String,
    var groupBalance: Double,
    var members: MutableList<Int>,
)