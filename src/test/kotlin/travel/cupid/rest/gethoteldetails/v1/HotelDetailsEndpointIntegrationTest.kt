package travel.cupid.rest.gethoteldetails.v1

import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import travel.cupid.BaseIntegrationTest
import travel.cupid.common.error.HotelApiErrors
import travel.cupid.i18n.AppMessageSource
import travel.cupid.repository.HotelRepository

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HotelDetailsEndpointIntegrationTest : BaseIntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var messageSource: AppMessageSource

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @MockitoSpyBean
    lateinit var hotelRepository: HotelRepository

    @Test
    @DisplayName("should return 404 when the requested hotel is not found")
    fun getHotelDetails() {
        val invalidId = 0
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hotels/{id}", invalidId).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HotelApiErrors.HOTEL_NOT_FOUND.code))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(messageSource.getMessage(HotelApiErrors.HOTEL_NOT_FOUND.msgKey)))
    }

    @Test
    @DisplayName("should return 200 when the requested hotel by external id is found")
    @Order(1)
    fun getExternalHotelDetailsShouldReturnTheCorrectHotel() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hotels/providers/{provider}/{externalId}", "cupid", 111).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address", org.hamcrest.Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.photos", org.hamcrest.Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.policies", org.hamcrest.Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.rooms", org.hamcrest.Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description", org.hamcrest.Matchers.notNullValue()))

        wiremock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3.0/property/111")))

        val hotels = hotelRepository.findAll()
        Assertions.assertThat(hotels).hasSize(1)
        Assertions.assertThat(hotels[0].id).isEqualTo(1)
        Assertions.assertThat(hotels[0].externalId).isEqualTo(111)
        Assertions.assertThat(hotels[0].provider).isEqualTo("cupid")

        Assertions.assertThat(redisTemplate.opsForValue().get("external-hotel::111:cupid:en")).isNotNull()
        Assertions.assertThat(redisTemplate.opsForValue().get("hotel::1:en")).isNotNull()
    }

    @Test
    @DisplayName("should return the hotel from the cache if it is already cached")
    @Order(2)
    fun getExternalHotelDetailsShouldReturnTheCorrectHotelFromCache() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hotels/providers/{provider}/{externalId}", "cupid", 111).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.notNullValue()))

        wiremock.verify(0, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3.0/property/111")))
    }
}
