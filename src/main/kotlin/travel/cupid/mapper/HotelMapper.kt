package travel.cupid.mapper

import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.model.*
import travel.cupid.model.embeddables.Address
import travel.cupid.model.embeddables.Coordinates
import travel.cupid.model.embeddables.Description

fun CupidHotel.toEntity(
    facilities: List<Facility>,
    amenitiesById: Map<Long?, Amenity>,
): Hotel = Hotel().apply {
    this.externalId = this@toEntity.cupidId
    this.provider = this@toEntity.provider
    this.mainImage = this@toEntity.mainImageTh
    this.coordinates = Coordinates(this@toEntity.latitude, this@toEntity.longitude)
    this.address = Address(
        street = this@toEntity.address.address,
        city = this@toEntity.address.city,
        state = this@toEntity.address.state,
        country = this@toEntity.address.country,
        postalCode = this@toEntity.address.postalCode,
    )
    this.description = Description(
        text = this@toEntity.description,
        markdown = this@toEntity.markdownDescription,
    )
    this.photos = this@toEntity.photos.map { it.toHotelPhoto() }.toMutableSet()
    this.facilities = facilities.toMutableSet()
    this.rooms = this@toEntity.rooms.map { it.toEntity(amenitiesById) }.toMutableSet()
    this.reviewsCount = this@toEntity.reviewCount
    this.policies = this@toEntity.policies.map { it.toEntity() }.toMutableSet()
}

fun CupidHotel.toTranslationEntity(hotel: Hotel, language: String): HotelTranslation = HotelTranslation().apply {
    this.description = Description(
        text = this@toTranslationEntity.description,
        markdown = this@toTranslationEntity.markdownDescription,
    )
    this.language = language
    this.hotel = hotel
}
