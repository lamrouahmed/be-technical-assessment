package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CupidHotel(
    val hotelId: Long,
    val cupidId: Long,
    val provider: String = "cupid",
    val mainImageTh: String,
    val hotelType: String,
    val hotelTypeId: Int,
    val chain: String?,
    val chainId: Int?,
    val latitude: Double,
    val longitude: Double,
    val hotelName: String,
    val phone: String?,
    val fax: String,
    val email: String?,
    val address: Address,
    val stars: Int,
    val airportCode: String,
    val rating: Double,
    val reviewCount: Int,
    val checkin: Checkin,
    val parking: String,
    val groupRoomMin: Int?,
    val childAllowed: Boolean,
    val petsAllowed: Boolean,
    val photos: Set<Photo>,
    val description: String,
    val markdownDescription: String,
    val importantInfo: String,
    val facilities: Set<Facility>,
    val policies: Set<Policy>,
    val rooms: List<Room>,
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Address(
        val address: String,
        val city: String,
        val state: String?,
        val country: String,
        val postalCode: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Checkin(
        val checkinStart: String,
        val checkinEnd: String?,
        val checkout: String,
        val instructions: Set<String>,
        val specialInstructions: String?,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Photo(
        val url: String,
        val hdUrl: String,
        val imageDescription: String?,
        val imageClass1: String?,
        val imageClass2: String?,
        val mainPhoto: Boolean,
        val score: Float,
        val classId: Int,
        val classOrder: Int,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Facility(
        val facilityId: Long,
        val name: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Policy(
        val policyType: String,
        val name: String,
        val description: String?,
        val childAllowed: String?,
        val petsAllowed: String?,
        val parking: String?,
        val id: Long,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Room(
        val id: Long,
        val roomName: String,
        val description: String,
        val roomSizeSquare: Float,
        val roomSizeUnit: String,
        val hotelId: String,
        val maxAdults: Int,
        val maxChildren: Int,
        val maxOccupancy: Int,
        val bedRelation: String,
        val bedTypes: Set<BedType>,
        val roomAmenities: Set<RoomAmenity>,
        val photos: Set<Photo>,
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class BedType(
            val quantity: Int,
            val bedType: String,
            val bedSize: String,
            val id: Int,
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class RoomAmenity(
            val amenitiesId: Long,
            val name: String,
            val sort: Int,
        )
    }
}
