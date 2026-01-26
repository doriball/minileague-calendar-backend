# PokecaGO Backend

### 기술 스택
- Backend
  - Kotlin
  - Spring Boot 4.0.2
  - Spring Data MongoDB
  - Spring Security
  - Spring Batch
  - MongoDB (Standalone, Docker on host)
  - Redis (Standalone, Docker on host)
  - Thymeleaf (Admin)
- Infrastructure
  - Ubuntu Server 24.04 LTS
  - Docker
  - Kubernetes (K3s)
  - Tailscale
  - Nginx
  - Cloudflare (DNS, Tunnel)
  - Let's Encrypt
  - Cert-Manager
  - Contour Gateway
- CI / CD
  - GitHub Actions
  - DockerHub
  - ArgoCD
  - Helm

### 프로젝트 구조
```aiignore
.github                # GitHub Actions 모음
docker                 # Docker 컨테이너 모음
module-admin           # 어드민
module-api             # 공통 API 모듈
module-auth            # 인증 API (추후 추가 개발 예정)
module-batch           # 배치
module-calendar        # 캘린더 API
module-core            # 코어 모듈 (도메인 모델, 공유용 코드, 유틸 등)
module-infrastructure  # 인프라 모듈 (영속성 엔티티, 공통 인프라 유틸 등)
```

### 아키텍처
- 포트 앤드 어댑터 아키텍처 일부 적용
- 어드민, 캘린더 API의 경우 도메인 별로 패키지 분리
- 도메인 패키지는 각각 adapter, application으로 구성
  - adapter : in, out으로 분류
    - in : 웹 계층
    - out : 영속성 계층과 기타 인프라 계층. 영속성 계층은 facade 역할을 하는 adapter와 이를 구성하는 기술 종속적인 구현체 멤버(Repository 등)로 구성
  - application : dto, port, service 패키지로 분류
    - dto : UseCase의 출력 모델
    - port : in, out으로 분류
      - in.UseCase : 입력 포트
      - in.dto : UseCase의 입력 모델
      - out : 애플리케이션이 외부 시스템에 의존하기 위한 출력 포트
    - service : port.in의 UseCase의 구현체
- application 영역에서 사용하는 도메인 모델은 module-core에 존재
- 도메인 모델 <-> 영속성 엔티티 변환 로직은 module-infrastructure와 각 영속성 adapter에 존재
  - 각 영속성 adapter : 도메인 모델 -> 영속성 엔티티로 변환 (C/U/D), 저장 방식/부분 업데이트 등 도메인별 요구에 맞춰 구현
  - module-infrastructure : 영속성 엔티티 -> 도메인 모델로 단순 매핑만 수행하는 공통 유틸 존재 (Read)

### CI / CD 플로우
```aiignore
1. 브랜치 코드 푸시 감지
2. GitHub Actions -> doriball-github-actions의 CI / CD Composite Action 호출
  - Gradle Test / Build
  - Docker Build & Push, 이미지 태그 추출
  - GitOps 리포지토리의 environments 내 values.yml 이미지 태그 변경
3. ArgoCD Auto Sync로 GitOps repo 변경 감지 후 배포
```