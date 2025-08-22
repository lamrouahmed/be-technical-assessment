package travel.cupid.mapper

import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.model.HotelPolicy

fun CupidHotel.Policy.toEntity() = HotelPolicy().apply {
    this.name = this@toEntity.name
    this.description = this@toEntity.description
    this.type = this@toEntity.policyType
    this.externalId = this@toEntity.id
    this.childAllowed = this@toEntity.childAllowed
    this.petsAllowed = this@toEntity.petsAllowed
    this.parking = this@toEntity.parking
}
