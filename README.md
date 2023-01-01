<p align="middle" >
  <img width="200px;" src="https://decsnota.sirv.com/FITMATE_LOGO.png"/>
</p>
<h1 align="middle">Fit Mate</h1>

## Why FitMate ?

함께 운동하며 얻는 행복감이 기대수명을 높인다는 사실을 혹시 알고 있나요?<br>
혼자하는 운동이 기대수명을 1.5년 늘리는 반면 테니스는 무려 9.7년, 배드민턴은 6.2년, 축구는 4.7년이나 수명을 늘려준다고 합니다.<br>
그렇기 때문에 사람들이 혼자가 아니라, 다른 사람들과 함께 운동할 수 있도록 돕고자 이 웹사이트를 만들었습니다.<br>
주변 유저 검색 기능을 통해 자신이 사는 동네에서 같이 운동할 수 있는 운동 메이트를 찾을 수 있습니다.<br><br>
이 웹사이트를 통해 유저들이 사람들과 함께 운동하면서 몸과 마음의 건강을 모두 챙길 수 있길 바랍니다.

> A sound mind in a sound body.
> \_Juvenalis

## Team 👩‍👦‍👦

|                                 [임영묵](https://github.com/Youngmook-Lim)                                 |                                  [권오영](https://github.com/kwnoyng)                                   |                                    [남기정](https://github.com/NAM-Kijung)                                    |
| :--------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------: |
| <img src="https://decsnota.sirv.com/profile-pic-character_LimYoungMook.jpg?v=4" width=200px alt="임영묵" > | <img src="https://decsnota.sirv.com/profile-pic-character_KwonOYoung.jpg?v=4" width=200px alt="권오영"> |    <img src="https://decsnota.sirv.com/profile-pic-character_NamKiJung.jpg?v=4" width=200px alt="남기정">     |
|                 팀장<br> DB설계<br> FrontEnd 메인 <br> BackEnd 서브 <br> API 선정 및 적용                  |                    DB 설계<br> FrontEnd 서브<br> BackEnd 메인 <br> README 작성 <br>                     | 아이디어 도출 및 기획<br> DB 설계<br> FrontEnd 서브 <br> FrontEnd 디자인 설계 및 구현 <br> 각종 오픈소스 적용 |

## Tech 👨‍💻

Fit Mate uses a number of tech to work properly:

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-000B1D.svg?style=for-the-badge&logo=mysql&logoColor=white)

## Tool 🛠️

Fit Mate uses a number of collaborative tools:

![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Github](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Trello](https://img.shields.io/badge/trello-0052CC?style=for-the-badge&logo=trello&logoColor=white)

### Project Notion link

https://befitting-sunspot-2c7.notion.site/SSAFY-FINAL-PROJECT-af4af351d04844b49f5ebb92c0e1a791

## API 🤖

Fit Mate uses a number of API for project:

![youtube](https://img.shields.io/badge/youtube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)
![kakao](https://img.shields.io/badge/kakao-FFCD00?style=for-the-badge&logo=kakao&logoColor=white)

- YouTube Data API v3
  - YouTube 영상 검색
- Kakao Map API
  - 카카오맵 활용 및 마커
- Kakao Local Rest API
  - 주소 > 경도·위도 변환
- Kakao 우편번호 서비스 API
  - 회원가입 시 주소 입력
- 공공데이터포털 기상청\_단기예보 API
  - 현재시각 날씨 표시

## Features 🧰

### User

#### 1. 회원가입

- 아이디, 닉네임, 핸드폰 번호, 이메일 중복 검사
- 비밀번호 두 번 입력으로 일치 확인
- Kakao 우편번호 서비스 API 적용하여 정확한 주소 입력

#### 2. 회원정보 수정

- 수정시 닉네임, 핸드폰 번호, 이메일 중복 검사

#### 3. 회원 상세보기

#### 4. 로그인

- JWT를 이용한 로그인 처리

#### 5. 로그아웃

- 10분 타이머 설정하여 자동 로그아웃 처리

#### 6. 팔로잉 & 팔로워

- 유저간 팔로잉 & 팔로워

#### 7. 회원탈퇴 <br><br>

### Video

#### 1. YouTube 영상 검색

- 10개의 카테고리를 선택하여 원하는 운동 부위의 YouTube 영상 검색
- 전체 / 제목 / 채널명으로 검색
- 제목 / 조회수 / 좋아요수 / 업로드일 기준 정렬하여 검색

#### 2. 영상 상세보기

#### 3. 영상 좋아요 기능

#### 4. 영상 댓글

- 댓글 작성
- 댓글 수정
- 댓글 삭제<br><br>

### Mate

#### 1. 가까운 유저 검색 기능

- Kakao Map API와 Kakao Local REST API 적용하여 Kakao Map에 반경 X km 떨어진 유저 마커
- 반경 1 / 3 / 5 / 10 km를 클라이언트에서 설정하여 원하는 거리의 다양한 유저를 검색
- 남성은 파란색 여성은 분홍색으로 성별 표시
- 마커 클릭 시 해당 유저의 정보와 프로필 이동 처리<br><br>

### Message

#### 1. 메세지 송·수신

- 받은 메세지, 보낸 메세지, 메세지 보내기
- 받거나 보낸 모든 메세지 열람
- 특정 유저를 검색하여 해당 유저에게 받거나 보낸 기록 열람
- 메세지 보내기 창에서 유저 닉네임 입력 시 자동완성 기능
- 유저의 프로필에서 메세지 보내기 버튼 클릭 시 닉네임창에 해당 유저의 닉네임 자동 입력<br><br>

### ETC

#### 1. Codepen 날씨 아이콘 오픈소스 활용

#### 2. HTML5UP 템플릿 오픈소스 활용<br><br>

## Licence 🔑

SSAFY 8th
