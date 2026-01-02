package io.doriball.moduleadmin.store.common.config

import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreMongoRepository
import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class StoreInitializer(
    val storeMongoRepository: StoreMongoRepository,
    val storeRegionMongoRepository: StoreRegionMongoRepository,
) {

    @PostConstruct
    fun init() {
        if (storeRegionMongoRepository.findAll().isEmpty()) {
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
                "창원"
            )
            for ((index, region) in list.withIndex()) {
                storeRegionMongoRepository.save(StoreRegionDocument(regionNo = index, name = region))
            }
        }

        if (storeMongoRepository.findAll().isEmpty()) {
            // 서울 (0)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 용산",
                    regionNo = 0,
                    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
                    mapInformation = null,
                    sns = null
                )
            )
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드냥 역삼",
                    regionNo = 0,
                    address = "서울시 강남구 논현로 77길 9, 태원빌딩 4층",
                    mapInformation = null,
                    sns = null
                )
            )
            storeMongoRepository.save(
                toStoreDocument(
                    name = "아현 카드킹덤",
                    regionNo = 0,
                    address = "서울 마포구 신촌로 254 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "신사 포카드",
                    regionNo = 0,
                    address = "서울 강남구 압구정로10길 14 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "강동 카드타운",
                    regionNo = 0,
                    address = "서울 강동구 천호대로168길 35 코오롱상가 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "장한평 카드킹덤 마린포드",
                    regionNo = 0,
                    address = "서울 동대문구 장한로 37 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "이수 듀얼파크",
                    regionNo = 0,
                    address = "서울 동작구 동작대로33가길 5 삼정빌딩 지하 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "당산 카드냥",
                    regionNo = 0,
                    address = "서울 영등포구 당산로38길 4 401호",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "목동 듀얼파크",
                    regionNo = 0,
                    address = "서울 양천구 신정중앙로 94 목마빌딩 지하",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "도곡 듀얼샵",
                    regionNo = 0,
                    address = "서울 강남구 논현로36길 27 지하",
                    mapInformation = null,
                    sns = null
                )
            )

            // 평택 (1)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드홀릭 평택",
                    regionNo = 1,
                    address = "경기도 평택시 평택2로 16 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 수원 (2)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드플래닛 수원",
                    regionNo = 2,
                    address = "경기도 수원시 영통구 청명로 73-1 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 안양 (3)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "범계 트레이너스",
                    regionNo = 3,
                    address = "경기 안양시 동안구 평촌대로217번길 15 3층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 인천 (4)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드팝 인천",
                    regionNo = 4,
                    address = "인천광역시 부평구 부평동 201-117 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 원주 (5)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드스페이스 원주",
                    regionNo = 5,
                    address = "강원도 원주시 능라동길 42-602호",
                    mapInformation = null,
                    sns = null
                )
            )

            // 광주 (6)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 화성스토어TCG 광주",
                    regionNo = 6,
                    address = "광주광역시 동구 중앙로160번길 22, 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 목포 (7)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "목포 TCG 팩토리",
                    regionNo = 7,
                    address = "전남 목포시 비파로51번길 20 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 대구 (8)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 트레이너 스쿨 대구",
                    regionNo = 8,
                    address = "대구광역시 달서구 성당동 488-1 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 부산 (9)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 카드베이스 부산",
                    regionNo = 9,
                    address = "부산광역시 동래구 명륜로 129번길 54, 4층",
                    mapInformation = null,
                    sns = null
                )
            )

            storeMongoRepository.save(
                toStoreDocument(
                    name = "동래 티씨지샵 셔플",
                    regionNo = 9,
                    address = "부산 동래구 충렬대로 134-1 제일빌딩 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 울산 (10)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "포켓몬 카드샵 TCG 팩토리 울산",
                    regionNo = 10,
                    address = "울산광역시 중구 번영로 454-1 1층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 진주 (11)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "진주 TCG 팩토리",
                    regionNo = 11,
                    address = "경남 진주시 진양호로547번길 5 2층",
                    mapInformation = null,
                    sns = null
                )
            )

            // 창원 (12)
            storeMongoRepository.save(
                toStoreDocument(
                    name = "창원 티씨지짐",
                    regionNo = 12,
                    address = "경남 창원시 의창구 남산로17번길 2 2층",
                    mapInformation = null,
                    sns = null
                )
            )
        }
    }

    private fun toStoreDocument(
        name: String,
        regionNo: Int,
        address: String,
        mapInformation: String?,
        sns: String?
    ): StoreDocument =
        StoreDocument(
            name = name,
            regionNo = regionNo,
            address = address,
            mapInformation = mapInformation,
            sns = sns
        )

}