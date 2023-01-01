<template>
  <div>
    <div class="title">
      <div class="titleflex">
        <h1 class="fitmatetitle">FIND YOUR FITMATE</h1>
        <span>You can look up all users within the set distance.</span>
      </div>
      <div>
        <span>조회할 거리를 설정해주세요</span>
        <select id="dist-option" v-model="dist">
          <option value="1">1km</option>
          <option value="3">3km</option>
          <option value="5">5km</option>
          <option value="10">10km</option>
        </select>
      </div>
    </div>
    <div id="map"></div>
    <transition>
      <div v-if="showSpinner" class="spinner-container">
        <svg
          class="spinner"
          xmlns="http://www.w3.org/2000/svg"
          x="-268.2"
          y="511.1"
          viewBox="-2794 0 2000.2 1999.5"
        >
          <defs>
            <clipPath id="clip">
              <path
                class="map"
                d="M-4424.7-.3C-4527.2 27-4668.7-15-4721 102c105.8-8.6 76.5 143.8 158 184.4 67.7-51 175.3-108.4 178.6-222.8 47.7-20.6-31-43.4-40.3-64zm2222.5 0C-2304.7 27-2446.2-15-2498.5 102c105.8-8.6 76.4 143.8 158 184.4 67.7-51 175.2-108.4 178.6-222.8 48-20.6-31-43.4-40-64zM-4785 19.2c-63.4 43-173.6 44.8-231.5 92.5v393.6c24 38.5 55.3 71 97.4 90.3 65 54 178 49.7 140 142.4 74 89 56 167.3 39 282.8 48 178.4 37-14 116-74.5 63-63 189-184.8 56-230.3-55-33.2-104-108-206-67.4-20-46.4-166-86.2-50-121.2 71 65.2 67-74.4 112-107.6 176-16.7 9-193.3-56-130.5 56 147.2-189-42-33-30.7 0-122.6 106 79 127-50.2-196-43.5 37-186-69-187.4zm2222.4 0C-2643.6 74-2801 62-2828 164c113 73-151 0-206.7 7.7-101.4 20.5-3.3 215-21 172.4 85.4-90 187.4-12 203.8 22 26 90 67 189 156 230 65 54 178 50 141 143 74 89 57 168 39 283 49 179 37-14 116-74 63-63 190-185 56-230-55-33-103-108-206-67-19-46-166-86-50-121 71 66 68-74 112-107 176-16 9-193-55-130 56 148-188-42-33-30 0-122 106 79 128-50-195-43 37-186-68-187zm-1311 77.4C-4006 152.4-3910 208.6-4059 251c18.8-33.7-4.3-96.6-138-56.8-130.4 80 17.6 190.4 17 68.5 28.6-41 96.8 54.3-7.7 69-87-43.7-84 104.7-153.5 99-11.4 108.6 123.8-50.2 151.4 23-27.8-98.4 68.4 84 75.5 32.6 112-15.5-87.6 60.4-111.4.6-75.2-51-216 89-146.2 168 85 42 161 28 163 141-39 105 92 216 145 68-14-68 116-210 60-218-117-69-59-190-3-13 178-16-75-130 85-88 87-4 86 196 155 48 64-92 56 129 84 127 91 75 87 44 68 12 20 22 91 2 35-66-35 28-43 49-37 61-12-19-29-37-32-32-43-81 11-44 73-134 35-126 46-45 109-128 115-62-57-103 87-133 95-15 26 133 112-9 73-18 170-40 147-71-90-51-185-27-282-51 0-23-301 24-188.2-43-36-78-181.3 53-250 27-83 88-126.2-6-27.5-59zm2190.8 15.8c-85 50.5-19 100.2-153.8 138.7 19-33-4.2-96-138-56-130.3 80 17.7 191 17.2 69 28.5-41 96.7 55-7.8 69-87-43-84 105-153.5 99-11.4 109 123.8-50 151.4 23-28-98 68.4 84 75.5 33 112-15-87.6 61-111.4 1-75.2-50-215.8 89-146 168 84.6 43 160.2 29 162.4 141-39 105 91.5 217 145 68-14-68 116.3-209 60.3-218-117-69-59.3-189-3-12 177.8-16-75.2-129 85-87 6 0 11.7 1 17 2V441c-7.4 6.4-32.5-19.3-2-21.4 1 2.3 1.5 4 2 6V192c-29.8.8-36.5-37.8 0-72.8zM-4379.3 223c-162 20.2 84.8 93.2 0 0zm2222.5 0c-162 20.2 84.8 93.2 0 0zm-2159.6 78c-49.5 60.2 96.7 121.6 0 0zm2222.5 0c-49 60.2 97 121.6 0 0zm-2254 29.7c-51 63.6 59 44 0 0zm2223 0c-51 63.6 59 44 0 0zm-1228 56.7c-96 25 38 42 0 0zm-745 15.4c142 1.6-55 89.4-1 2zm2223 0c142 1.6-55 89.4-1 2zm-2085 9.4c26 45.6-39 140.6 0 0zm2223 0c26 45.6-39 140.6 0 0zm-1690 4c-112 120.7 1 117.2 16 10l-15-9.4zm-477 2.4c20 49.4-41 2.8 0 0zm-305 37.3c-36 18 34 40 0 0zm2223 0c-36 18 34 40 0 0zm-1188 57c6 73 85 2 0 0zm-264 4c8 97 66 29 0 0zm-1310 17c-5 37 44 32 0 0zm2223 0c-6 37 43 32 0 0zm-581 14c43 95 204 26 33 13l-33-14zm-1679 15c-10 58 90 5 0 0zm2223 0c-10 58 90 5 0 0zm-2163 20c21 54 138 37 0 0zm2223 0c21 54 138 37 0 0zm-1029 118h-5c-65 128 59-1 6-1zm42 14c25 117 207 21 18 4l-17-5zm7 69c-99 15-166 182-54 155 64-23 194 98 182-65-53-122-58-40-128-91zm-505 7c-120 79 50 125.3 0 0zm2223 0c-120 79 50 125.3 0 0zm-1131 30c-56 45 71 46.3 0 0zm43 37c-58 44.4 73 32 0 0zm-386 56c-10 56-55 200 22 63.4 1-25-4-45-22-63z"
              />
            </clipPath>
          </defs>
          <circle cx="-1793.9" cy="999.8" r="611.2" fill="#957AC3" />
          <circle
            cx="-2238.4"
            cy="555.4"
            r="555.6"
            fill="#fff"
            clip-path="url(#clip)"
            transform="translate(444.495 448.4)"
          />
          <path
            class="logo"
            fill="#957AC3"
            d="M-1460.5 310.8l181 266.7 150-96.8-323-165.8 382 137 99-41zm334.3 175.2l-53.8 34.6 101 52.7z"
          />
        </svg>
        <div class="spinner-text">
          <h2>We are looking for nearby users.</h2>
          <h2>Please hold on while we find your mate!</h2>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import axios from "../util/http-common.js";
export default {
  name: "MateView",
  computed: {
    myUser() {
      return this.$store.state.myUser;
    },
  },
  watch: {
    dist() {
      this.getNearbyUsers();
    },
  },
  data() {
    return {
      nearbyUsers: {},
      dist: "1",
      showSpinner: true,
    };
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const myPosition = new kakao.maps.LatLng(this.myUser.y, this.myUser.x);
      // const myPosition = new kakao.maps.LatLng(33.450701, 126.570667);

      const zoom = {
        1: 6,
        3: 7,
        5: 8,
        10: 9,
      };

      const options = {
        center: myPosition,
        level: zoom[this.dist],
      };

      this.map = new kakao.maps.Map(container, options);

      // 반경 xKm 원 그리기
      const circle = new kakao.maps.Circle({
        center: myPosition,
        radius: this.dist * 1000,
        strokeWeight: 0,
        // strokeColor: "#ff6b6b",
        // strokeColor: "#9775fa",
        strokeOpacity: 1,
        strokeStyle: "solid",
        // fillColor: "#ffa8a8",
        fillColor: "#d0bfff",
        fillOpacity: 0.7,
      });
      circle.setMap(this.map);

      for (const jsonUser in this.nearbyUsers) {
        const dist = this.nearbyUsers[jsonUser].toFixed(1);
        const user = JSON.parse(jsonUser);
        // console.log(user);
        // user.y = 33.450701;
        // user.x = 126.570667;

        // 마커 표시
        let imgSrc;
        if (user.u_id === this.myUser.u_id) {
          imgSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
        } else if (user.gender === "M") {
          imgSrc = "https://decsnota.sirv.com/pin_blue.png";
        } else {
          imgSrc = "https://decsnota.sirv.com/pin_red.png";
        }

        let imgSize;
        if (user.u_id === this.myUser.u_id) {
          imgSize = new kakao.maps.Size(24, 35);
        } else {
          imgSize = new kakao.maps.Size(32, 32);
        }
        // const imgOption = {};
        const markerImage = new kakao.maps.MarkerImage(
          imgSrc,
          imgSize
          // imgOption
        );
        const position = new kakao.maps.LatLng(user.y, user.x);
        const marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });
        marker.setMap(this.map);

        // 커스텀 오버레이(말풍선)
        const content = document.createElement("div");
        content.classList.add("label");

        const img = document.createElement("img");
        img.classList.add("profileimg");
        img.src = `https://decsnota.sirv.com/${user.img}.png`;
        content.appendChild(img);

        const info = document.createElement("p");
        info.classList.add("nickname");
        info.textContent = `${user.nickname}`;
        content.appendChild(info);

        const distance = document.createElement("p");
        distance.classList.add("infotext");
        distance.textContent = `Distance ${dist}km`;
        content.appendChild(distance);

        const buttons = document.createElement("div");
        buttons.classList.add("buttonflex");
        content.appendChild(buttons);

        const link = document.createElement("button");
        link.textContent = "Profile";
        link.classList.add("profilebtn");
        link.onclick = () => {
          this.$router.push({
            name: "UserViewMain",
            params: {
              id: user.u_id ? user.u_id : 0,
            },
          });
        };
        buttons.appendChild(link);

        const closeBtn = document.createElement("button");
        closeBtn.textContent = "X";
        closeBtn.classList.add("closeBtn");
        closeBtn.onclick = () => {
          customOverlay.setMap(null);
        };
        buttons.appendChild(closeBtn);

        // ":to",
        //   `{name: 'UserViewMain',
        //   params: {
        //     id: ${user.u_id} ? ${user.u_id} : 0,
        //   },}`

        // const content = `
        // <div class ="label">
        //   <span><strong>${user.nickname}</strong></span>
        //   <button onclick="closeOverlay(customOverlay)">X</button>
        //   <br/>
        //   <span>나와의 거리 : ${dist}km</span>
        // </div>
        // `;

        const customOverlay = new kakao.maps.CustomOverlay({
          position: position,
          content: content,
          yAnchor: 1.3,
        });

        if (user.u_id === this.myUser.u_id) continue;
        kakao.maps.event.addListener(marker, "click", () => {
          customOverlay.setMap(this.map);
        });
      }
      this.setMapUi();
    },
    setMapUi() {
      // 지도 줌
      const zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      // 지도 유형 (지도, 위성)
      // const mapTypeControl = new kakao.maps.MapTypeControl();
      // this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    },
    getNearbyUsers() {
      const token = sessionStorage.getItem("access-token");
      if (token) {
        const payload = this.parseJWT(token);
        axios
          .get(`userApi/detail`, { params: { id: payload.id } })
          .then((res) => this.$store.commit("SET_MY_USER", res.data))
          .then(() => {
            axios({
              url: "userApi/findAll",
              method: "GET",
              params: {
                id: this.myUser.u_id,
                dist: this.dist,
              },
            })
              .then((res) => {
                this.nearbyUsers = res.data;
                console.log(this.nearbyUsers);
                // const myUserJson = JSON.stringify(this.myUser);
                // this.nearbyUsers[myUserJson] = 0;
              })
              .then(() => {
                if (!window.kakao || !window.kakao.maps) {
                  const script = document.createElement("script");
                  script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_JS_API_KEY}`;
                  /* global kakao */
                  script.addEventListener("load", () => {
                    console.log("만들어짐");
                    kakao.maps.load(this.initMap);
                  });
                  document.head.appendChild(script);
                } else {
                  console.log(kakao);
                  this.initMap();
                }
              });
          });
      }
    },
    parseJWT(token) {
      var base64Url = token.split(".")[1];
      var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      var jsonPayload = decodeURIComponent(
        window
          .atob(base64)
          .split("")
          .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
          .join("")
      );
      return JSON.parse(jsonPayload);
    },
  },
  created() {
    if (!sessionStorage.getItem("access-token")) {
      alert("로그아웃 되었습니다 - 로그인 페이지로 이동합니다.");
      this.$router.push("/login");
      return;
    }
  },
  mounted() {
    this.getNearbyUsers();
    setTimeout(() => {
      this.showSpinner = false;
    }, 3000);
    // const myUserJson = JSON.stringify(this.myUser);
    // this.nearbyUsers[myUserJson] = 0;

    // console.log(this.nearbyUsers);
    // if (!window.kakao || !window.kakao.maps) {
    //   const script = document.createElement("script");
    //   script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_JS_API_KEY}`;

    //   /* global kakao */
    //   script.addEventListener("load", () => {
    //     kakao.maps.load(this.initMap);
    //     console.log("만들어짐");
    //   });

    //   document.head.appendChild(script);
    // } else {
    //   console.log(kakao);
    //   this.initMap();
    // }
  },
};
</script>

<style>
#dist-option {
  padding-left: 12px;
}

.titleflex {
  text-align: start;
}

.infotext {
  margin: 0;
  font-size: 18px !important;
  /* font-family: ; */
}

.nickname {
  font-size: 20px !important;
  color: rgb(87, 87, 87);
  font-weight: 800;
  margin: 0;
}

.fitmatetitle {
  font-weight: 400;
  margin: 20px auto;
}

#map {
  width: 1200px;
  height: 600px;
  margin: 20px auto 30px auto;
  max-width: inherit;
  border-radius: 9px;
}

.title {
  display: flex;
  width: 1200px;
  justify-content: space-between;
  align-items: center;
  margin: 0 auto;
  max-width: inherit;
}

.profileimg {
  width: 80px;
  border-radius: 50px;
}

.buttonflex {
  display: flex;
  gap: 2px;
}

.closeBtn {
  background-color: rgb(182, 179, 179);
  font-family: "Source Sans Pro", Helvetica, sans-serif;
  font-weight: 500;
  /* line-height: 1.65; */
  /* appearance: none; */
  border-radius: 8px;
  color: white;
  outline: 0;
  text-decoration: none;
  width: 30px;
  height: 2.75em;
  margin-top: 2px;
  max-width: inherit;
  box-shadow: none;
  font-weight: 600;
}
.closeBtn:hover {
  border-bottom-color: rgb(87, 87, 87);
  background-color: rgba(0, 0, 0, 0.068);
}

.profilebtn {
  background-color: rgb(182, 179, 179);
  font-family: "Source Sans Pro", Helvetica, sans-serif;
  font-weight: 500;
  /* line-height: 1.65; */
  /* appearance: none; */
  border-radius: 8px;
  color: white;
  outline: 0;
  text-decoration: none;
  width: 70px;
  height: 2.75em;
  margin-top: 2px;
  max-width: inherit;
  box-shadow: none;
  font-weight: 600;
}
.profilebtn:hover {
  border-bottom-color: rgb(87, 87, 87);
  background-color: rgba(0, 0, 0, 0.068);
}

.distance {
  display: flex;
}

.distancetext {
  width: 600px;
  margin: auto;
  max-width: inherit;
  text-align: end;
}

.title select {
  padding: 5px;
}

.label {
  background-color: rgb(255, 255, 255);
  padding: 8px 16px;
  border-radius: 9px;
  color: rgb(65, 64, 64);
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.8s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}

.spinner-container {
  position: fixed;
  z-index: 9990;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #dddd;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  backdrop-filter: blur(10px);
  /* border-radius: 1em; */
}

.spinner-text {
  position: fixed;
  top: 75%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.spinner-text h2 {
  color: rgb(99, 99, 99);
  line-height: 1.2;
  font-weight: 600;
}

.spinner {
  z-index: 9999;
  position: fixed;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30vh;
  height: 30vh;
}
.spinner .map {
  animation: 1s map ease-in-out infinite;
}
.spinner .logo {
  animation: 1s plane ease-in-out infinite;
  transform-origin: -1800px 1000px;
}

@keyframes map {
  from {
    transform: translateX(0px);
  }
  to {
    transform: translateX(2222px);
  }
}

@keyframes plane {
  from {
    transform: rotate(360deg);
  }
  to {
    transform: rotate(0deg);
  }
}
</style>
