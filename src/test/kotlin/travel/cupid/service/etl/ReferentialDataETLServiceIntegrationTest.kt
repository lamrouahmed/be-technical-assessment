package travel.cupid.service.etl

import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import travel.cupid.BaseIntegrationTest
import travel.cupid.repository.AmenityRepository
import travel.cupid.repository.FacilityRepository
import travel.cupid.service.ReferentialDataETLService

class ReferentialDataETLServiceIntegrationTest : BaseIntegrationTest() {

    @Autowired
    lateinit var referentialDataETLService: ReferentialDataETLService

    @Autowired
    lateinit var amenityRepository: AmenityRepository

    @Autowired
    lateinit var facilityRepository: FacilityRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @BeforeEach
    fun setUp() {
        amenityRepository.deleteAll()
        facilityRepository.deleteAll()
        redisTemplate.connectionFactory?.connection?.serverCommands()?.flushAll()
    }

    @Test
    @DisplayName("should sync room amenities from external API and cache them in Redis")
    fun syncRoomAmenities_shouldSyncAndCacheAmenities() {
        referentialDataETLService.syncRoomAmenities()
        Assertions.assertThat(amenityRepository.count()).isGreaterThan(0)
        wiremock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3.0/property/room_amenities")))
        Assertions.assertThat(redisTemplate.opsForValue().get("amenities::33,42,129,70,45")).isNotNull()
    }

    @Test
    @DisplayName("should sync facilities from external API and cache them in Redis")
    fun syncFacilities_shouldSyncAndCacheFacilities() {
        referentialDataETLService.syncFacilities()
        Assertions.assertThat(facilityRepository.count()).isGreaterThan(0)
        wiremock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3.0/property/facilities")))
        Assertions.assertThat(redisTemplate.opsForValue().get("facilities::2263,1390,2266,2411,2010")).isNotNull()
    }
}
