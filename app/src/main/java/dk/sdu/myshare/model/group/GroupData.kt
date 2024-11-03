package dk.sdu.myshare.model.group

data class GroupData(
    val id: Int,
    val name: String,
    val groupBalance: Double,
    val members: List<Int>,
)