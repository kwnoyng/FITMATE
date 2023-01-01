<template>
  <div>
    <the-header></the-header>
    <div class="navi">
      <the-message-nav></the-message-nav>
    </div>
    <router-view
      class="innercontainer"
      :isMyUserLoaded="isMyUserLoaded"
    ></router-view>
    <the-footer-vue></the-footer-vue>
  </div>
</template>

<script>
import TheHeader from "../components/common/TheHeader.vue";
import TheMessageNav from "../components/message/TheMessageNav.vue";
import axios from "../util/http-common.js";
import TheFooterVue from "@/components/common/TheFooter.vue";

export default {
  name: "MessageView",
  components: {
    TheHeader,
    TheMessageNav,
    TheFooterVue,
  },
  data() {
    return {
      isMyUserLoaded: false,
    };
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
  },
  created() {
    const token = sessionStorage.getItem("access-token");
    if (token) {
      const payload = this.parseJWT(token);
      // console.log(payload);
      axios
        .get(`userApi/detail`, { params: { id: payload.id } })
        .then((res) => this.$store.commit("SET_MY_USER", res.data))
        .then(() => (this.isMyUserLoaded = true));

      axios.get("userApi/getNicknames").then((res) => {
        // this.nicknames = res.data;
        this.$store.commit("SET_NICKNAMES", res.data);
      });
      this.$emit("start-timer");
    }
    // else {
    // this.$router.push({ name: "LoginView" });
    // }
  },
};
</script>

<style scoped>
.innercontainer {
  max-width: calc(100% - 4em);
  margin: auto;
  align-items: center;
  justify-content: center;
  width: 64em;
  padding: 4em 1em 1em 1em;
  text-align: center;
  display: block;
  border: 1px solid white;
  border-bottom-left-radius: 1em;
  border-bottom-right-radius: 1em;
  border-top-left-radius: 1em;
  border-top-right-radius: 1em;
}

.navi {
  display: flex;
  justify-content: center;
  margin: 0;
}
</style>
