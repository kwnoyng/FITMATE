<template>
  <div>
    <header class="header" v-if="showHeader">
      <div class="homeAndWeather">
        <div class="weather-info">
          <div class="weather hot">
            <span class="sun"></span>
            <span class="sunx"></span>
          </div>
          <div class="weather cloudy">
            <span class="cloud"></span>
            <span class="cloudx"></span>
          </div>
          <div class="weather stormy">
            <ul>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
            </ul>
            <span class="snowe"></span>
            <span class="snowex"></span>
            <span class="stick"></span>
            <span class="stick2"></span>
          </div>
          <div class="weather breezy">
            <ul>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
              <li></li>
            </ul>
            <span class="cloudr"></span>
          </div>
          <div class="weather night">
            <span class="moon"></span>
            <span class="spot1"></span>
            <span class="spot2"></span>
            <ul>
              <li></li>
            </ul>
          </div>
          <div v-if="isWeatherLoaded" class="weather-pointer" ref="pointer">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="25"
              height="25"
              fill="currentColor"
              class="bi bi-caret-up-fill"
              viewBox="0 0 16 16"
            >
              <path
                d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"
              />
            </svg>
          </div>
        </div>
        <div class="weatherMessage">
          <div>
            <span>{{ this.curDateAndTimeMessage }}</span>
          </div>
          <div class="tempAndWeather">
            <span
              ><strong>{{ this.temperature }}°C</strong>
              &nbsp;&nbsp;|&nbsp;&nbsp;
              <strong>{{ this.weatherMessage }}</strong></span
            >
          </div>
        </div>
      </div>
      <div class="menu-right">
        <div v-if="loaded" class="timer">
          <div>
            <span>자동로그아웃 : </span>
            <span>{{ min }}:</span>
            <span>{{ sec }}</span>
          </div>
          <div class="timer-button-container">
            <button class="timer-button" @click.prevent="resetTimer">
              세션갱신
            </button>
          </div>
        </div>
        <span>Welcome {{ myUser.nickname }}!</span>
        <router-link class="router-link" :to="{ name: 'MessageReceived' }"
          ><svg
            xmlns="http://www.w3.org/2000/svg"
            width="22"
            height="22"
            fill="currentColor"
            class="bi bi-envelope-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"
            /></svg
        ></router-link>
        <!-- <router-link
          class="router-link"
          :to="{
            name: 'UserViewMain',
            params: {
              id: myUser.u_id ? myUser.u_id : 0,
            },
          }"
          >🏋️‍♀️</router-link
        > -->
        <button @click.prevent="logout">로그아웃</button>
      </div>
    </header>
    <header id="header" class="alt">
      <div class="logoclass">
        <p class="pin"></p>
        <p class="pulse"></p>
      </div>

      <router-link class="to-home" :to="{ name: 'HomeView' }"
        ><h1>Fit Mate</h1></router-link
      >

      <p>
        Find your exercise mate!<br />
        Working out together makes you healthier.
      </p>
    </header>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  name: "TheHeader",
  data() {
    return {
      showHeader: true,
      weatherOption: "",
      weatherMessage: "",
      curDateAndTimeMessage: "",
      temperature: "",
      isWeatherLoaded: false,
    };
  },
  props: {
    thisIsRegister: String,
  },
  computed: {
    ...mapState(["myUser", "min", "sec", "time", "timer", "LOGOUT_TIME"]),
    loaded() {
      return this.min !== "";
    },
  },
  methods: {
    logout() {
      this.$store.commit("CLEAR_ALL");

      // JWT 토큰 지우기
      sessionStorage.removeItem("access-token");

      // 타이머 리셋
      this.$store.commit("RESET_TIMER");

      this.$router.push({ name: "LoginView" });
    },
    resetTimer() {
      this.$store.commit("RESET_TIMER");
      this.$store.commit("SET_TIME", this.LOGOUT_TIME);
      const localTimer = setInterval(this.tick, 1000);
      this.$store.commit("SET_TIMER", localTimer);
      console.log("Timer started from TheHeader.vue");
    },
    tick() {
      this.$store.commit(
        "SET_MIN",
        String(Math.trunc(this.time / 60)).padStart(2, 0)
      );
      this.$store.commit("SET_SEC", String(this.time % 60).padStart(2, 0));
      // console.log(this.time);

      if (this.time === 0) {
        alert("세션이 만료되어 로그아웃 되었습니다.");
        this.$store.commit("RESET_TIMER");
        this.$store.commit("CLEAR_ALL");

        // JWT 토큰 지우기
        sessionStorage.removeItem("access-token");
        this.$router.push({ name: "LoginView" });
      }

      this.$store.commit("DECREASE_TIME");
    },
  },

  created() {
    if (this.thisIsRegister) {
      this.showHeader = false;
      return;
    }

    const WEATHER_KEY = process.env.VUE_APP_WEATHER_API_KEY;
    const now = new Date();

    const dayMap = ["일", "월", "화", "수", "목", "금", "토"];

    this.curDateAndTimeMessage += (now.getMonth() + 1).toString() + "월 ";
    this.curDateAndTimeMessage += now.getDate().toString() + "일 ";
    this.curDateAndTimeMessage += dayMap[now.getDay()] + "요일";

    const curDate =
      now.getFullYear().toString() +
      (now.getMonth() + 1).toString().padStart(2, "0") +
      now.getDate().toString().padStart(2, "0");

    const curTime = (now.getHours() - 1).toString().padStart(2, "0") + "00";

    new Promise((resolve) => {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          resolve(pos);
        },
        (err) => {
          console.error(err.message, "💥💥💥💥💥💥");
        }
      );
    })
      .then((res) => {
        console.log(res.coords);
        axios({
          url: "weatherApi/",
          method: "GET",
          params: {
            ServiceKey: WEATHER_KEY,
            curDate,
            curTime,
            lat: Math.round(res.coords.latitude).toString(),
            lon: Math.round(res.coords.longitude).toString(),
          },
        })
          .then((res) => {
            console.log(res.data);
            const data = res.data.response.body.items.item;
            // T1H: 기온, PTY: 강수형태, SKY: 하늘
            const filteredData = data.filter(
              (el) => el.fcstTime === data[0].fcstTime
            );

            console.log(filteredData);

            this.temperature = parseInt(
              filteredData.filter((el) => el.category === "T1H")[0].fcstValue
            );

            const isNight =
              parseInt(filteredData[0].fcstTime.slice(0, 2)) >= 19 ||
              parseInt(filteredData[0].fcstTime.slice(0, 2)) < 6
                ? true
                : false;

            if (isNight) {
              this.weatherOption = 4;
            }

            const precipitation = filteredData.filter(
              (el) => el.category === "PTY"
            )[0].fcstValue;
            if (
              precipitation === "1" ||
              precipitation === "2" ||
              precipitation === "4" ||
              precipitation === "5" ||
              precipitation === "6"
            ) {
              if (!isNight) {
                this.weatherOption = 3;
              }
              this.weatherMessage = "비";
            } else if (precipitation === "3" || precipitation === "7") {
              if (!isNight) {
                this.weatherOption = 2;
              }
              this.weatherMessage = "눈";
            } else {
              if (
                filteredData.filter((el) => el.category === "SKY")[0]
                  .fcstValue === "1"
              ) {
                if (!isNight) {
                  this.weatherOption = 0;
                }
                this.weatherMessage = "맑음";
              } else {
                if (!isNight) {
                  this.weatherOption = 1;
                }
                this.weatherMessage = "흐림";
              }
            }
            this.isWeatherLoaded = true;
            console.log(this.weatherOption);
          })
          .then(() => {
            this.$refs.pointer.style.left = `${12 + this.weatherOption * 70}px`;
          });
      })
      .catch((err) => {
        console.log(err, "💔💔💔💔💔");
      });
  },
};
</script>

<style scoped>
.to-home {
  display: inline-block;
  border-bottom: none;
}

.router-link {
  color: white;
}

.timer {
  font-size: 13px;
  display: flex;
  /* flex-direction: column; */
  flex-direction: row-reverse;
  gap: 12px;
  justify-content: center;
  align-items: center;
}

.timer-button {
  padding: 0;
  min-width: 75px;
  height: 25px;
  line-height: 27px;
  font-size: 12px;
}

.weatherMessage {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 13px;
}

.weatherMessage span {
  margin: 0;
  padding: 0;
}

.weatherMessage .tempAndWeather {
  font-size: 15px;
}

/* 로고 */
.logoclass {
  display: flex;
  justify-content: center;
  padding: 0 0 0 10;
}
.pin {
  display: flex;
  justify-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50% 50% 50% 0;
  background: white;
  opacity: 70%;
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
  -ms-transform: rotate(-45deg);
  transform: rotate(-45deg);
  left: 50%;
  top: 50%;
  margin: -50px -10px 10px 0px;
  -webkit-animation-name: bounce;
  -moz-animation-name: bounce;
  -o-animation-name: bounce;
  -ms-animation-name: bounce;
  animation-name: bounce;
  -webkit-animation-fill-mode: both;
  -moz-animation-fill-mode: both;
  -o-animation-fill-mode: both;
  -ms-animation-fill-mode: both;
  animation-fill-mode: both;
  -webkit-animation-duration: 1s;
  -moz-animation-duration: 1s;
  -o-animation-duration: 1s;
  -ms-animation-duration: 1s;
  animation-duration: 1s;
}
.pin:after {
  content: "";
  width: 34px;
  height: 34px;
  margin: 8px 0 0 8px;
  background: #969292;
  border-radius: 50%;
}
.pulse {
  display: flex;
  justify-content: center;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 50%;
  height: 24px;
  width: 24px;
  left: 50%;
  top: 50%;
  margin: 13px 0px 0px -26.5px;
  -webkit-transform: rotateX(55deg);
  -moz-transform: rotateX(55deg);
  -o-transform: rotateX(55deg);
  -ms-transform: rotateX(55deg);
  transform: rotateX(55deg);
  z-index: -2;
}
.pulse:after {
  content: "";
  border-radius: 50%;
  height: 50px;
  width: 50px;
  /* position: absolute; */
  margin: -10px -20px -10px -20px;
  -webkit-animation: pulsate 1s ease-out;
  -moz-animation: pulsate 1s ease-out;
  -o-animation: pulsate 1s ease-out;
  -ms-animation: pulsate 1s ease-out;
  animation: pulsate 1s ease-out;
  -webkit-animation-iteration-count: infinite;
  -moz-animation-iteration-count: infinite;
  -o-animation-iteration-count: infinite;
  -ms-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
  opacity: 0;
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
  filter: alpha(opacity=0);
  -webkit-box-shadow: 0 0 1px 2px #89849b;
  box-shadow: 0 0 1px 2px #89849b;
  -webkit-animation-delay: 1.1s;
  -moz-animation-delay: 1.1s;
  -o-animation-delay: 1.1s;
  -ms-animation-delay: 1.1s;
  animation-delay: 1.1s;
}
@-moz-keyframes pulsate {
  0% {
    -webkit-transform: scale(0.1, 0.1);
    -moz-transform: scale(0.1, 0.1);
    -o-transform: scale(0.1, 0.1);
    -ms-transform: scale(0.1, 0.1);
    transform: scale(0.1, 0.1);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
  50% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
  }
  100% {
    -webkit-transform: scale(1.2, 1.2);
    -moz-transform: scale(1.2, 1.2);
    -o-transform: scale(1.2, 1.2);
    -ms-transform: scale(1.2, 1.2);
    transform: scale(1.2, 1.2);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
}
@-webkit-keyframes pulsate {
  0% {
    -webkit-transform: scale(0.1, 0.1);
    -moz-transform: scale(0.1, 0.1);
    -o-transform: scale(0.1, 0.1);
    -ms-transform: scale(0.1, 0.1);
    transform: scale(0.1, 0.1);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
  50% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
  }
  100% {
    -webkit-transform: scale(1.2, 1.2);
    -moz-transform: scale(1.2, 1.2);
    -o-transform: scale(1.2, 1.2);
    -ms-transform: scale(1.2, 1.2);
    transform: scale(1.2, 1.2);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
}
@-o-keyframes pulsate {
  0% {
    -webkit-transform: scale(0.1, 0.1);
    -moz-transform: scale(0.1, 0.1);
    -o-transform: scale(0.1, 0.1);
    -ms-transform: scale(0.1, 0.1);
    transform: scale(0.1, 0.1);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
  50% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
  }
  100% {
    -webkit-transform: scale(1.2, 1.2);
    -moz-transform: scale(1.2, 1.2);
    -o-transform: scale(1.2, 1.2);
    -ms-transform: scale(1.2, 1.2);
    transform: scale(1.2, 1.2);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
}
@keyframes pulsate {
  0% {
    -webkit-transform: scale(0.1, 0.1);
    -moz-transform: scale(0.1, 0.1);
    -o-transform: scale(0.1, 0.1);
    -ms-transform: scale(0.1, 0.1);
    transform: scale(0.1, 0.1);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
  50% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
  }
  100% {
    -webkit-transform: scale(1.2, 1.2);
    -moz-transform: scale(1.2, 1.2);
    -o-transform: scale(1.2, 1.2);
    -ms-transform: scale(1.2, 1.2);
    transform: scale(1.2, 1.2);
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
  }
}
@-moz-keyframes bounce {
  0% {
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
    -webkit-transform: translateY(-2000px) rotate(-45deg);
    -moz-transform: translateY(-2000px) rotate(-45deg);
    -o-transform: translateY(-2000px) rotate(-45deg);
    -ms-transform: translateY(-2000px) rotate(-45deg);
    transform: translateY(-2000px) rotate(-45deg);
  }
  60% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
    -webkit-transform: translateY(30px) rotate(-45deg);
    -moz-transform: translateY(30px) rotate(-45deg);
    -o-transform: translateY(30px) rotate(-45deg);
    -ms-transform: translateY(30px) rotate(-45deg);
    transform: translateY(30px) rotate(-45deg);
  }
  80% {
    -webkit-transform: translateY(-10px) rotate(-45deg);
    -moz-transform: translateY(-10px) rotate(-45deg);
    -o-transform: translateY(-10px) rotate(-45deg);
    -ms-transform: translateY(-10px) rotate(-45deg);
    transform: translateY(-10px) rotate(-45deg);
  }
  100% {
    -webkit-transform: translateY(0) rotate(-45deg);
    -moz-transform: translateY(0) rotate(-45deg);
    -o-transform: translateY(0) rotate(-45deg);
    -ms-transform: translateY(0) rotate(-45deg);
    transform: translateY(0) rotate(-45deg);
  }
}
@-webkit-keyframes bounce {
  0% {
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
    -webkit-transform: translateY(-2000px) rotate(-45deg);
    -moz-transform: translateY(-2000px) rotate(-45deg);
    -o-transform: translateY(-2000px) rotate(-45deg);
    -ms-transform: translateY(-2000px) rotate(-45deg);
    transform: translateY(-2000px) rotate(-45deg);
  }
  60% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
    -webkit-transform: translateY(30px) rotate(-45deg);
    -moz-transform: translateY(30px) rotate(-45deg);
    -o-transform: translateY(30px) rotate(-45deg);
    -ms-transform: translateY(30px) rotate(-45deg);
    transform: translateY(30px) rotate(-45deg);
  }
  80% {
    -webkit-transform: translateY(-10px) rotate(-45deg);
    -moz-transform: translateY(-10px) rotate(-45deg);
    -o-transform: translateY(-10px) rotate(-45deg);
    -ms-transform: translateY(-10px) rotate(-45deg);
    transform: translateY(-10px) rotate(-45deg);
  }
  100% {
    -webkit-transform: translateY(0) rotate(-45deg);
    -moz-transform: translateY(0) rotate(-45deg);
    -o-transform: translateY(0) rotate(-45deg);
    -ms-transform: translateY(0) rotate(-45deg);
    transform: translateY(0) rotate(-45deg);
  }
}
@-o-keyframes bounce {
  0% {
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
    -webkit-transform: translateY(-2000px) rotate(-45deg);
    -moz-transform: translateY(-2000px) rotate(-45deg);
    -o-transform: translateY(-2000px) rotate(-45deg);
    -ms-transform: translateY(-2000px) rotate(-45deg);
    transform: translateY(-2000px) rotate(-45deg);
  }
  60% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
    -webkit-transform: translateY(30px) rotate(-45deg);
    -moz-transform: translateY(30px) rotate(-45deg);
    -o-transform: translateY(30px) rotate(-45deg);
    -ms-transform: translateY(30px) rotate(-45deg);
    transform: translateY(30px) rotate(-45deg);
  }
  80% {
    -webkit-transform: translateY(-10px) rotate(-45deg);
    -moz-transform: translateY(-10px) rotate(-45deg);
    -o-transform: translateY(-10px) rotate(-45deg);
    -ms-transform: translateY(-10px) rotate(-45deg);
    transform: translateY(-10px) rotate(-45deg);
  }
  100% {
    -webkit-transform: translateY(0) rotate(-45deg);
    -moz-transform: translateY(0) rotate(-45deg);
    -o-transform: translateY(0) rotate(-45deg);
    -ms-transform: translateY(0) rotate(-45deg);
    transform: translateY(0) rotate(-45deg);
  }
}
@keyframes bounce {
  0% {
    opacity: 0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: alpha(opacity=0);
    -webkit-transform: translateY(-2000px) rotate(-45deg);
    -moz-transform: translateY(-2000px) rotate(-45deg);
    -o-transform: translateY(-2000px) rotate(-45deg);
    -ms-transform: translateY(-2000px) rotate(-45deg);
    transform: translateY(-2000px) rotate(-45deg);
  }
  60% {
    opacity: 1;
    -ms-filter: none;
    filter: none;
    -webkit-transform: translateY(30px) rotate(-45deg);
    -moz-transform: translateY(30px) rotate(-45deg);
    -o-transform: translateY(30px) rotate(-45deg);
    -ms-transform: translateY(30px) rotate(-45deg);
    transform: translateY(30px) rotate(-45deg);
  }
  80% {
    -webkit-transform: translateY(-10px) rotate(-45deg);
    -moz-transform: translateY(-10px) rotate(-45deg);
    -o-transform: translateY(-10px) rotate(-45deg);
    -ms-transform: translateY(-10px) rotate(-45deg);
    transform: translateY(-10px) rotate(-45deg);
  }
  100% {
    -webkit-transform: translateY(0) rotate(-45deg);
    -moz-transform: translateY(0) rotate(-45deg);
    -o-transform: translateY(0) rotate(-45deg);
    -ms-transform: translateY(0) rotate(-45deg);
    transform: translateY(0) rotate(-45deg);
  }
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2%;
}

#header.alt {
  padding: 10% 0 0 0;
}

.menu-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.homeAndWeather {
  display: flex;
  align-items: center;
  gap: 32px;
}

/* 날씨정보 */
@import url("https://fonts.googleapis.com/css?family=Raleway");
body {
  background: #fff;
}

.weather-info {
  display: inline-flex;
  gap: 20px;
  position: relative;
}

.weather-pointer {
  position: absolute;
  top: 48px;
  left: 5px;
  z-index: 9990;
}

.weather {
  position: relative;
  width: 50px;
  height: 50px;
  border-radius: 40%;
  z-index: 120;
}

.hot {
  /* position: absolute;
  top: 25%;
  left: 20%; */
  background: linear-gradient(
    to top right,
    rgba(255, 87, 34, 1) 0%,
    rgba(251, 140, 0, 1) 100%
  );
  box-shadow: 1px 1px 30px rgba(255, 111, 0, 1);
}

.sun {
  position: absolute;
  top: -10%;
  left: 65%;
  width: 25px;
  height: 25px;
  border-radius: 100%;
  background: linear-gradient(
    to bottom left,
    rgba(255, 235, 59, 1) 0%,
    rgba(249, 168, 37, 1) 90%
  );
  box-shadow: 1px 1px 30px rgba(255, 160, 0, 1);
  animation: inex 3s infinite linear;
}

.sunx {
  position: absolute;
  top: 30%;
  left: 45%;
  width: 5px;
  height: 5px;
  border-radius: 100%;
  background-color: #fff;
  opacity: 0.2;
}
.cloudy {
  /* position: absolute;
  top: 25%;
  left: 45%; */
  background: linear-gradient(
    to top right,
    rgba(63, 81, 181, 1) 0%,
    rgba(3, 155, 229, 1) 40%
  );
  box-shadow: 1px 1px 30px rgba(2, 119, 189, 1);
}
.cloud {
  position: absolute;
  top: 5%;
  left: 70%;
  width: 30px;
  height: 15px;
  border-radius: 10px;
  background-color: rgba(129, 212, 250, 1);
  box-shadow: 1px 1px 30px rgba(0, 151, 167, 1);
  animation: move 3s infinite linear;
}
.cloudx {
  position: absolute;
  top: 23%;
  left: 55%;
  width: 20px;
  height: 10px;
  border-radius: 10px;
  background-color: rgba(129, 212, 250, 1);
  animation: move 3s infinite linear;
}
.stormy {
  /* position: absolute;
  top: 25%;
  left: 70%; */
  background: linear-gradient(
    to top right,
    rgba(117, 117, 117, 1) 0%,
    rgba(33, 33, 33, 1) 90%
  );
  box-shadow: 1px 1px 30px rgba(33, 33, 33, 1);
}
.stormy li {
  position: absolute;
  list-style: none;
  width: 1px;
  height: 6px;
  border-radius: 50%;
  background-color: #777;
  box-shadow: 1px 1px 30px #555;
  animation: fall 3s infinite linear;
  opacity: 0;
}
.stormy li:nth-child(1) {
  top: 0%;
  left: 100%;
}

.stormy li:nth-child(2) {
  top: 5%;
  left: 90%;
}

.stormy li:nth-child(3) {
  top: -10%;
  left: 80%;
  animation-delay: 2s;
}

.stormy li:nth-child(4) {
  top: 10%;
  left: 100%;
  animation-delay: 2s;
}

.stormy li:nth-child(5) {
  top: 20%;
  left: 80%;
  animation-delay: 0.5s;
}

.stormy li:nth-child(6) {
  top: 35%;
  left: 70%;
  background-color: #bbb;
  animation-delay: 0.5s;
}

.stormy li:nth-child(7) {
  top: 23%;
  left: 60%;
  background-color: #bbb;
  animation-delay: 0.8s;
}

.stormy li:nth-child(8) {
  top: 5%;
  left: 70%;
  background-color: #bbb;
  animation-delay: 0.8s;
}
.snowe {
  position: absolute;
  top: 60%;
  left: 40%;
  width: 20px;
  height: 20px;
  border-radius: 60px;
  background-color: #ddd;
}
.snowex {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 10px;
  height: 10px;
  border-radius: 15px;
  background-color: #bbb;
}
.stick {
  position: absolute;
  top: 60%;
  left: 40%;
  width: 3px;
  height: 15px;
  transform: rotate(-45deg);
  background-color: #333;
  z-index: -10;
}
.stick2 {
  position: absolute;
  top: 60%;
  left: 75%;
  width: 3px;
  height: 15px;
  transform: rotate(45deg);
  background-color: #333;
  z-index: -10;
}
.breezy {
  /* position: absolute;
  top: 60%;
  left: 30%; */
  background: linear-gradient(
    to top right,
    rgba(156, 204, 101, 1) 0%,
    rgba(38, 198, 218, 1) 50%
  );
  box-shadow: 1px 1px 30px rgba(38, 198, 218, 1);
}
.cloudr {
  position: absolute;
  top: 5%;
  left: 60%;
  width: 40px;
  height: 15px;
  border-radius: 10px;
  background-color: rgba(96, 125, 139, 1);
  box-shadow: 1px 1px 30px rgba(84, 110, 122, 1);
  animation: flash 1.5s infinite linear;
}

.breezy li {
  position: absolute;
  list-style: none;
  width: 5px;
  height: 2px;
  border-radius: 10%;
  background-color: #eee;
  opacity: 0;
  animation: meteor 3s infinite linear;
  transform: rotate(-60deg);
}
.breezy li:nth-child(1) {
  top: 15%;
  left: 100%;
}

.breezy li:nth-child(2) {
  top: 15%;
  left: 110%;
  animation-delay: 0.3s;
}

.breezy li:nth-child(3) {
  top: 15%;
  left: 125%;
  animation-delay: 0.6s;
}

.breezy li:nth-child(4) {
  top: 15%;
  left: 135%;
  animation-delay: 1s;
}

.breezy li:nth-child(5) {
  top: 15%;
  left: 130%;
}

.night {
  /* position: absolute;
  top: 60%;
  left: 60%; */
  background: linear-gradient(
    to bottom right,
    rgba(63, 81, 181, 1) 0%,
    rgba(171, 71, 188, 1) 70%
  );
  box-shadow: 1px 1px 30px rgba(81, 45, 168, 1);
}
.moon {
  position: absolute;
  top: -10%;
  left: 65%;
  width: 30px;
  height: 30px;
  border-radius: 100%;
  background: rgba(255, 241, 118, 1);
  box-shadow: 1px 1px 30px rgba(224, 224, 224, 1);
}
.spot1 {
  position: absolute;
  top: 0%;
  left: 85%;
  width: 5px;
  height: 5px;
  border-radius: 100%;
  background-color: #777;
}
.spot2 {
  position: absolute;
  top: 30%;
  left: 75%;
  width: 5px;
  height: 5px;
  border-radius: 100%;
  background-color: #777;
}

.night li {
  position: absolute;
  list-style: none;
  width: 3px;
  height: 3px;
  border-radius: 100%;
  background-color: #fff;

  transform: rotate(-45deg);
}
/* .night li:nth-child(1) {
  top: 30%;
  left: 65%;
}

.night li:nth-child(2) {
  top: 35%;
  left: 53%;
} */

.night li:nth-child(1) {
  opacity: 0;
  top: 20%;
  left: 75%;
  width: 2px;
  height: 2px;
  animation: meteor 1.4s infinite linear;
  animation-delay: 1s;
}

/* .night li:nth-child(4) {
  top: 5%;
  left: 50%;
} */

/* .night li:nth-child(2) {
  opacity: 0;
  top: 20%;
  left: 55%;
  width: 1px;
  height: 1px;
  animation: meteor 1s infinite linear;
  animation-delay: 0.3s;
} */

@keyframes inex {
  50% {
    opacity: 0.4;
  }
  60% {
    opacity: 1;
  }
}
@keyframes move {
  50% {
    transform: translateX(-10px);
  }
}
@keyframes fall {
  10% {
    opacity: 0.8;
  }

  50% {
    opacity: 1;
    transform: translate(-10px, 30px);
  }
  100% {
    transform: translate(-25px, 70px);
  }
}
@keyframes flash {
  48% {
    background-color: rgba(96, 125, 139, 1);
  }
  50% {
    background-color: #fff;
  }
  55% {
    background-color: rgba(96, 125, 139, 1);
  }
  57% {
    background-color: #fff;
  }
}
@keyframes meteor {
  10% {
    opacity: 1;
  }
  80% {
    left: 10%;
    top: 75%;
    opacity: 0;
  }
}
</style>
