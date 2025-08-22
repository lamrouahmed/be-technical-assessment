package travel.cupid.mapper

import travel.cupid.connectors.cupid.model.CupidHotelReview
import travel.cupid.model.Hotel
import travel.cupid.model.HotelReview
import java.time.Instant

fun CupidHotelReview.toEntity(hotel: Hotel): HotelReview = HotelReview().apply {
    externalId = reviewId
    country = this@toEntity.country
    type = this@toEntity.type
    headline = this@toEntity.headline
    language = this@toEntity.language
    pros = this@toEntity.pros
    cons = this@toEntity.cons
    source = this@toEntity.source
    name = this@toEntity.name
    reviewedAt = Instant.parse(date)
    this.hotel = hotel
}
