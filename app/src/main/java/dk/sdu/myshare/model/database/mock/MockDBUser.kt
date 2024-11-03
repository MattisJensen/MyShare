package dk.sdu.myshare.model.database.mock

data class MockDBUser (
    val id: Int,
    var name: String,
    var email: String,
    var password: String
)