package travel.cupid.mapper

import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.model.HotelPhoto
import travel.cupid.model.HotelRoomPhoto

fun CupidHotel.Photo.toHotelPhoto() = HotelPhoto().apply {
    url = this@toHotelPhoto.url
    hdUrl = this@toHotelPhoto.hdUrl
    description = this@toHotelPhoto.imageDescription
    isMain = this@toHotelPhoto.mainPhoto
    score = this@toHotelPhoto.score
}

fun CupidHotel.Photo.toHotelRoomPhoto() = HotelRoomPhoto().apply {
    url = this@toHotelRoomPhoto.url
    hdUrl = this@toHotelRoomPhoto.hdUrl
    description = this@toHotelRoomPhoto.imageDescription
    isMain = this@toHotelRoomPhoto.mainPhoto
    score = this@toHotelRoomPhoto.score
}
