# Zeze

## How to

### Code Convention

다음 컨벤션을 기준으로 작성되었습니다.

- **[캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java)**
- **[Naver JavaScript Style Guide](https://github.com/naver/eslint-config-naver/blob/master/STYLE_GUIDE.md)**  

#### IntelliJ Code Style 설정

- `Editor > Code Style > Java` 에서 다음을 설정
  - `Scheme > Import scheme > IntelliJ IDEA code scheme XML`
  - `(프로젝트 최상단) .zeze-java-convention.xml` 선택
  - 이름을 입력해야하는 경우 `zeze-java-convention` 입력 
  - `Scheme`에서 `zeze-java-convention` 선택

#### CheckStyle (Java)

**gradle**
- `./gradlew checkstyleMain`
- `./gradlew checkstyleTest`

**IntelliJ**
- `Plugins`에서 `CheckStyle-IDEA` 설치
- `Tools > Checkstyle` 에서 다음을 설정
  - Checkstyle Version: 8.27 이상 (8.34 권장)
  - Scan Scope: Only Java sources (including tests)
  - Configuration file 하단 **+** 버튼으로 XML 다음을 추가
    - Description: `zeze-checkstyle`
    - Use a local Checkstyle file 체크 후 `Browse > (프로젝트 최상단) .zeze-checkstyle.xml` 선택
  - Import 한 CheckStyle 체크 후 apply

#### ESLint (TypeScript)

**Yarn / NPM**
- `cd client`
- (확인만 하고 싶다면) `yarn lint` (또는 `npm run lint`)
- (수정 가능한 부분들에 대해 자동 수정을 실행한다면) `yarn lint-fix` (또는 `npm run lint-fix`)

**IntelliJ**
- `Plugins`에서 `ESLint` 설치
- `Language & Frameworks > JavaScript > Code Quality Tools > ESLint` 에서 다음을 설정
  - `Automatic ESLint Configuration` 체크
  - (저장할 때마다 자동 수정을 원한다면) `Run eslint --fix on save` 체크


### Client Build (*Server와 연동 없이* Client만 Build하는 경우)

- `cd client`
- `yarn` (또는 `npm install`)
- `yarn build`
- (`serve`가 설치되어있지 않은 경우) `yarn global add serve` (또는 `npm install -g serve`)
- `serve -s build`


### Server Build 

- (Client 빌드 후 Server의 `resources/static`으로 옮기는 경우) `./gradlew buildClient`
- `./gradlew build`
- `java -jar ./build/libs/zeze-0.0.1-SNAPSHOT.jar`
