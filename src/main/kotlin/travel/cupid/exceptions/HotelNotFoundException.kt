package travel.cupid.exceptions

import org.springframework.http.HttpStatus
import travel.cupid.common.error.HotelApiErrors
import travel.cupid.exceptions.handler.ApiBusinessException

class HotelNotFoundException : ApiBusinessException(HotelApiErrors.HOTEL_NOT_FOUND, HttpStatus.NOT_FOUND)
