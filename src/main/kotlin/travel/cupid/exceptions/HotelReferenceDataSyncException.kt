package travel.cupid.exceptions

import travel.cupid.common.error.HotelApiErrors
import travel.cupid.exceptions.handler.ThirdPartyApiException

class HotelReferenceDataSyncException : ThirdPartyApiException(HotelApiErrors.REFERENCE_DATA_SYNC_ERROR)
