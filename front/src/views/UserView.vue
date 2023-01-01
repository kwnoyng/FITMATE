<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";
export default {
  computed: {
    ...mapState(["myUser", "curUser"]),
  },
  data() {
    return {
      curId: this.$route.params.id,
    };
  },
  watch: {
    $route() {
      this.getUserDetail();
    },
  },
  methods: {
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
    getUserDetail() {
      const token = sessionStorage.getItem("access-token");
      if (token) {
        const payload = this.parseJWT(token);
        // console.log(payload);
        axios
          .get(`userApi/detail`, { params: { id: payload.id } })
          .then((res) => this.$store.commit("SET_MY_USER", res.data))
          .then(() => {
            axios
              .get(`userApi/detail`, { params: { id: this.$route.params.id } })
              .then((res) => this.$store.commit("SET_CUR_USER", res.data));

            axios
              .get(`followApi/`, { params: { from_user: this.myUser.u_id } })
              .then((res) => {
                console.log(res.data);
                this.$store.commit("SET_MY_USER_FOLLOWING", res.data);
              });

            axios
              .get(`followApi/`, { params: { to_user: this.$route.params.id } })
              .then((res) =>
                this.$store.commit("SET_CUR_USER_FOLLOWERS", res.data)
              );

            axios
              .get(`followApi/`, {
                params: { from_user: this.$route.params.id },
              })
              .then((res) =>
                this.$store.commit("SET_CUR_USER_FOLLOWING", res.data)
              );

            axios
              .get(`videoApi/favoriteVideos`, {
                params: { id: this.$route.params.id },
              })
              .then((res) =>
                this.$store.commit("SET_CUR_FAVORITE_VIDEOS", res.data)
              );
          })
          .then(() => this.$store.commit("SET_LOADED_USER_INFO"));
      }
    },
  },

  created() {
    if (!sessionStorage.getItem("access-token")) {
      alert("로그아웃 되었습니다 - 로그인 페이지로 이동합니다.");
      this.$router.push("/login");
    } else {
      this.getUserDetail();
    }
  },
};
</script>

<style></style>
