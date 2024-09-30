### 주요 구현사항
#### Java Backend Engineer - 과제
- 코디 완성 서비스 개발

#### 구현 기술
- SpringBoot + Gradle + JPA + H2


## API 명세

### 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API

**URL**: `/items/categories/min-price`

**Method**: GET

**요청 매개변수**:

- 없음

**응답**: 카테고리 별 최저가격인 브랜드와 상품 가격을 조회합니다.

### 단일 브랜드로 모든 카테고리 사품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API

**URL**: `/items/brands/min-price`

**Method**: GET

**요청 매개변수**:

- 없음

**응답**: 단일 브랜드 카테고리 최저가를 조회합니다.

### 카테고리 이름으로 최저, 최고 가격 브래드와 상품 가격을 조회하는 API

**URL**: `/items/categories/{category}/min-max-price`

**Method**: GET

**요청 매개변수**:

- category : 카테고리명

**응답**: 조회된 카테고리 및 최저/최고 상품 리스트를 조회합니다.

### 그 외 브랜드/카테고리 상품을 추가하는 CUD API

**URL**: `/items/{itemId}`

**Method**: POST, PUT, DELETE


# 빌드 결과물 Download link
https://github.com/muhagaa/musinsa/raw/refs/heads/master/build/libs/item-0.0.1-SNAPSHOT.jar

# 테스트 방법
- Download link를 통해 jar 파일 다운로드
- java -jar item-0.0.1-SNAPSHOT.jar

