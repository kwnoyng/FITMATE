<template>
  <div id="app">
    <router-view @start-timer="startTimer" />
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "APP",
  data() {
    return {};
  },
  methods: {
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
    startTimer() {
      this.$store.commit("RESET_TIMER");
      this.$store.commit("SET_TIME", this.LOGOUT_TIME);
      const localTimer = setInterval(this.tick, 1000);
      this.$store.commit("SET_TIMER", localTimer);
      console.log("Timer started from App.vue");
    },
  },
  computed: {
    ...mapState(["min", "sec", "time", "timer", "LOGOUT_TIME"]),
  },
};
</script>

<style scoped>
@import "@/assets/css/main.css";
@import "@/assets/css/fontawesome-all.min.css";
</style>
