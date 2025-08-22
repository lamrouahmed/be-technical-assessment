package travel.cupid.mapper

import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.model.*
import travel.cupid.model.embeddables.RoomSize
import travel.cupid.model.enums.RoomSizeUnit

fun CupidHotel.Room.toEntity(amenitiesById: Map<Long?, Amenity>) = HotelRoom().apply {
    amenities = this@toEntity.roomAmenities.mapNotNull { amenitiesById[it.amenitiesId] }.toMutableSet()
    externalId = this@toEntity.id
    name = this@toEntity.roomName
    description = this@toEntity.description
    maxAdults = this@toEntity.maxAdults
    maxChildren = this@toEntity.maxChildren
    maxOccupancy = this@toEntity.maxOccupancy
    size = RoomSize().apply {
        value = this@toEntity.roomSizeSquare
        unit = RoomSizeUnit.from(this@toEntity.roomSizeUnit)
    }
    beds = this@toEntity.bedTypes.map { it.toEntity() }.toMutableSet()
    photos = this@toEntity.photos.map { it.toHotelRoomPhoto() }.toMutableSet()
}

fun CupidHotel.Room.BedType.toEntity() = RoomBed().apply {
    this.type = this@toEntity.bedType
    this.size = this@toEntity.bedSize
    this.quantity = this@toEntity.quantity
    this.externalId = this@toEntity.id
}
