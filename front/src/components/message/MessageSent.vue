<template>
  <div>
    <h1 class="sent">Sent Message</h1>
    <input
      @keyup.enter="getSearchedUser"
      type="text"
      placeholder="유저검색"
      v-model="searchQuery"
    />
    <button @click="getSearchedUser">검색</button>
    <message-content
      v-for="msg in sentMsgs"
      :key="msg.m_id"
      :msg="msg"
      type="sent"
    ></message-content>
  </div>
</template>

<script>
import axios from "@/util/http-common.js";
import MessageContent from "./MessageContent.vue";
import { mapState } from "vuex";

export default {
  name: "MessageSent",
  components: {
    MessageContent,
  },
  data() {
    return {
      searchQuery: "",
    };
  },
  props: {
    isMyUserLoaded: Boolean,
  },
  computed: {
    ...mapState(["myUser", "sentMsgs", "nicknames"]),
  },
  methods: {
    getSearchedUser() {
      if (!this.searchQuery) return;

      axios({
        url: `messageApi/sent/${this.nicknames[this.searchQuery]}`,
        method: "GET",
        params: {
          myId: this.myUser.u_id,
        },
      }).then((res) => {
        console.log(res.data);
        this.$store.commit("SET_SENT_MSGS", res.data);
      });
    },
  },
  watch: {
    isMyUserLoaded() {
      axios({
        url: "messageApi/received",
        method: "GET",
        params: {
          id: this.myUser.u_id,
        },
      }).then((res) => {
        this.$store.commit("SET_RECEIVED_MSGS", res.data);
      });

      axios({
        url: "messageApi/sent",
        method: "GET",
        params: {
          id: this.myUser.u_id,
        },
      }).then((res) => {
        this.$store.commit("SET_SENT_MSGS", res.data);
      });

      // this.$forceUpdate();
    },
    searchQuery(newVal) {
      if (newVal) return;
      axios({
        url: "messageApi/sent",
        method: "GET",
        params: {
          id: this.myUser.u_id,
        },
      }).then((res) => {
        this.$store.commit("SET_SENT_MSGS", res.data);
      });
    },
  },
  created() {
    axios({
      url: "messageApi/sent",
      method: "GET",
      params: {
        id: this.myUser.u_id,
      },
    }).then((res) => {
      this.$store.commit("SET_SENT_MSGS", res.data);
    });
  },
};
</script>

<style scoped>
.sent {
  font-weight: 400;
  margin-top: 20px;
}

button {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
