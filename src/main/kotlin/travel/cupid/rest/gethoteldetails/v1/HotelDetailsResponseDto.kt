package travel.cupid.rest.gethoteldetails.v1

import travel.cupid.common.enums.Language
import travel.cupid.model.*
import travel.cupid.model.embeddables.Address
import travel.cupid.model.embeddables.Coordinates
import travel.cupid.model.embeddables.Description
import java.io.Serializable

data class HotelDetailsResponseDto(
    val id: Long?,
    val mainImage: String?,
    val chainId: Int?,
    val coordinates: Coordinates?,
    val address: Address?,
    val description: Description?,
    val photos: List<HotelPhotoDto>,
    val facilities: List<FacilityDto>,
    val policies: List<HotelPolicyDto>,
    val rooms: List<HotelRoomDto>,
) : Serializable {

    companion object {
        fun from(hotel: Hotel, hotelTranslation: HotelTranslation?, language: String? = Language.getPrimary()): HotelDetailsResponseDto = HotelDetailsResponseDto(
            id = hotel.id,
            mainImage = hotel.mainImage,
            chainId = hotel.chainId,
            coordinates = hotel.coordinates,
            address = hotel.address,
            description = Description(text = hotelTranslation?.description?.text ?: hotel.description?.text, markdown = hotelTranslation?.description?.markdown ?: hotel.description?.markdown),
            photos = hotel.photos.map { HotelPhotoDto.from(it) },
            facilities = hotel.facilities.map { FacilityDto.from(it, language) },
            policies = hotel.policies.map { HotelPolicyDto.from(it) },
            rooms = hotel.rooms.map { HotelRoomDto.from(it, language) },
        )
    }

    data class HotelPhotoDto(
        val id: Long?,
        val url: String?,
        val hdUrl: String?,
        val description: String?,
        val isMain: Boolean?,
        val score: Float?,
    ) : Serializable {
        companion object {
            fun from(photo: HotelPhoto) = HotelPhotoDto(
                id = photo.id,
                url = photo.url,
                hdUrl = photo.hdUrl,
                description = photo.description,
                isMain = photo.isMain,
                score = photo.score,
            )
        }
    }

    data class FacilityDto(
        val id: Int?,
        val name: String?,
        val sortOrder: Int?,
    ) : Serializable {
        companion object {
            fun from(facility: Facility, language: String? = Language.getPrimary()): FacilityDto {
                val resolvedName = if (language != null) {
                    facility.translations.find { it.language == language }?.name
                } else {
                    facility.name
                }
                return FacilityDto(
                    id = facility.id,
                    name = resolvedName,
                    sortOrder = facility.sortOrder,
                )
            }
        }
    }

    data class HotelPolicyDto(
        val id: Long?,
        val type: String?,
        val description: String?,
        val childAllowed: String?,
        val petsAllowed: String?,
        val parking: String?,
    ) : Serializable {
        companion object {
            fun from(policy: HotelPolicy) = HotelPolicyDto(
                id = policy.id,
                type = policy.type,
                description = policy.description,
                childAllowed = policy.childAllowed,
                petsAllowed = policy.petsAllowed,
                parking = policy.parking,
            )
        }
    }

    data class HotelRoomDto(
        val id: Long?,
        val name: String?,
        val description: String?,
    ) : Serializable {
        companion object {
            fun from(room: HotelRoom, language: String? = Language.getPrimary()): HotelRoomDto {
                val translation = room.translations.find { it.language == language }
                return HotelRoomDto(
                    id = room.id,
                    name = translation?.name ?: room.name,
                    description = translation?.description ?: room.description,
                )
            }
        }
    }
}
