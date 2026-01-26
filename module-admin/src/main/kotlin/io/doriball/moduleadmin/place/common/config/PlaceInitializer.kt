package io.doriball.moduleadmin.place.common.config

import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceMongoRepository
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.modulecore.domain.enums.PlaceType
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceRegionDocument
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class PlaceInitializer(
    val placeMongoRepository: PlaceMongoRepository,
    val placeRegionMongoRepository: PlaceRegionMongoRepository,
) {

    @PostConstruct
    fun init() {
        if (placeRegionMongoRepository.findAll().isEmpty()) {
            val list = listOf(
                "서울",
                "평택",
                "수원",
                "안양",
                "인천",
                "원주",
                "광주",
                "목포",
                "대구",
                "부산",
                "울산",
                "진주",
                "창원",
                "군포",
                "대전",
                "성남",
                "천안",
            )
            for ((index, region) in list.withIndex()) {
                placeRegionMongoRepository.save(PlaceRegionDocument(regionNo = index, name = region))
            }
        }

        if (placeMongoRepository.findAll().isEmpty()) {
            // 서울 (0)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 용산",
                    regionNo = 0,
                    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
                    mapInformation = null,
                    sns = null
                )
            )
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드냥 역삼",
                    regionNo = 0,
                    address = "서울시 강남구 논현로 77길 9, 태원빌딩 4층",
                    mapInformation = null,
                    sns = null
                )
            )
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "아현 카드킹덤",
                    regionNo = 0,
                    address = "서울시 마포구 신촌로 254 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "신사 포카드",
                    regionNo = 0,
                    address = "서울시 강남구 압구정로10길 14 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "강동 카드타운",
                    regionNo = 0,
                    address = "서울시 강동구 천호대로168길 35 코오롱상가 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "이수 듀얼파크",
                    regionNo = 0,
                    address = "서울시 동작구 동작대로33가길 5 삼정빌딩 지하 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "당산 카드냥",
                    regionNo = 0,
                    address = "서울시 영등포구 당산로38길 4 401호",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "목동 듀얼파크",
                    regionNo = 0,
                    address = "서울시 양천구 신정중앙로 94 목마빌딩 지하",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "도곡 듀얼샵",
                    regionNo = 0,
                    address = "서울시 강남구 논현로36길 27 지하",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "서울 COEX",
                    regionNo = 0,
                    type = PlaceType.ETC,
                    address = "서울시 강남구 영동대로 513",
                    mapInformation = null,
                    sns = null
                )
            )

            // 평택 (1)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드홀릭 평택",
                    regionNo = 1,
                    address = "경기도 평택시 평택2로 16 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 수원 (2)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드플래닛 수원",
                    regionNo = 2,
                    address = "경기도 수원시 영통구 청명로 73-1 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 안양 (3)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "범계 트레이너스",
                    regionNo = 3,
                    address = "경기도 안양시 동안구 평촌대로217번길 15 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 인천 (4)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드팝 인천",
                    regionNo = 4,
                    address = "인천광역시 부평구 부평동 201-117 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 원주 (5)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드스페이스 원주",
                    regionNo = 5,
                    address = "강원도 원주시 능라동길 42-602호",
                    mapInformation = null,
                    sns = null
                )
            )

            // 광주 (6)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 화성스토어TCG 광주",
                    regionNo = 6,
                    address = "광주광역시 동구 중앙로160번길 22, 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "광주 김대중컨벤션센터",
                    regionNo = 6,
                    type = PlaceType.ETC,
                    address = "광주광역시 서구 상무누리로 30 김대중컨벤션센터",
                    mapInformation = null,
                    sns = null
                )
            )

            // 목포 (7)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "목포 TCG 팩토리",
                    regionNo = 7,
                    address = "전남 목포시 비파로51번길 20 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 대구 (8)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 트레이너 스쿨 대구",
                    regionNo = 8,
                    address = "대구광역시 달서구 성당동 488-1 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "대구 EXCO",
                    regionNo = 8,
                    type = PlaceType.ETC,
                    address = "대구광역시 북구 엑스코로 10",
                    mapInformation = null,
                    sns = null
                )
            )

            // 부산 (9)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 카드베이스 부산",
                    regionNo = 9,
                    address = "부산광역시 동래구 명륜로 129번길 54, 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "동래 티씨지샵 셔플",
                    regionNo = 9,
                    address = "부산광역시 동래구 충렬대로 134-1 제일빌딩 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            placeMongoRepository.save(
                toPlaceDocument(
                    name = "부산 BEXCO",
                    regionNo = 9,
                    type = PlaceType.ETC,
                    address = "부산광역시 해운대구 APEC로 55",
                    mapInformation = null,
                    sns = null
                )
            )

            // 울산 (10)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "포켓몬 카드샵 TCG 팩토리 울산",
                    regionNo = 10,
                    address = "울산광역시 중구 번영로 454-1 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 진주 (11)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "진주 TCG 팩토리",
                    regionNo = 11,
                    address = "경남 진주시 진양호로547번길 5 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 창원 (12)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "창원 티씨지짐",
                    regionNo = 12,
                    address = "경남 창원시 의창구 남산로17번길 2 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 군포 (13)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "금정 배틀시티",
                    regionNo = 13,
                    address = "경기도 군포시 산본천로 199번길5 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 대전 (14)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "대전 듀얼몰",
                    regionNo = 14,
                    address = "대전광역시 서구 문정로170번길 193",
                    mapInformation = null,
                    sns = null
                )
            )

            // 성남 (15)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "수내 카드빈",
                    regionNo = 15,
                    address = "경기도 성남시 분당구 황새울로 214번길 8 ,802호",
                    mapInformation = null,
                    sns = null
                )
            )

            // 천안 (16)
            placeMongoRepository.save(
                toPlaceDocument(
                    name = "천안 카드빌리지",
                    regionNo = 16,
                    address = "천안시 서북구 불당1길 49, 2층 201호",
                    mapInformation = null,
                    sns = null
                )
            )
        }
    }

    private fun toPlaceDocument(
        name: String,
        regionNo: Int,
        type: PlaceType = PlaceType.STORE,
        address: String,
        mapInformation: String?,
        sns: String?
    ): PlaceDocument =
        PlaceDocument(
            name = name,
            regionNo = regionNo,
            type = type,
            address = address,
            mapInformation = mapInformation,
            sns = sns
        )

}