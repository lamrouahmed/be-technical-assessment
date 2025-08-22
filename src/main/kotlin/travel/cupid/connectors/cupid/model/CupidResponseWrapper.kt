package travel.cupid.connectors.cupid.model

data class CupidResponseWrapper<T>(
    val data: List<T>,
)
