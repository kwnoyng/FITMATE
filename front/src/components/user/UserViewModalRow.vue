<template>
  <div class="user">
    <div class="profile-pic-container">
      <img
        class="profile-pic"
        v-if="loadedUserInfo"
        :src="require('../../assets/profileImgs/' + user.img + '.png')"
        alt="Profile Image"
      />
    </div>
    <span class="user-nickname">{{ user.nickname }}</span>
    <span class="state-message">{{ displayedMsg }} </span>
    <button class="go-to-profile-btn" @click="closeModal">Profile</button>
  </div>
</template>

<script>
import { mapState } from "vuex";
const MAX_DISPLAY_LENGTH = 20;

export default {
  name: "ViewDetailCommentsRow",
  props: {
    user: Object,
  },
  data() {
    return {};
  },
  methods: {
    closeModal() {
      this.$emit("close-modal");
    },
  },
  computed: {
    ...mapState(["myUser", "loadedUserInfo"]),
    displayedMsg() {
      let dispMsg = this.user.state_message;
      const len = dispMsg.length;
      if (len > MAX_DISPLAY_LENGTH) {
        dispMsg = dispMsg.slice(0, MAX_DISPLAY_LENGTH) + "...";
      }
      return dispMsg;
    },
  },
};
</script>

<style scoped>
.user {
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin-top: 2%;
}

.profile-pic-container {
  display: flex;
}

.profile-pic {
  width: 60px;
  height: 60px;
  margin: 0 20px;
  border-radius: 50%;
  overflow: hidden;
  display: inline-block;
}

.profile-pic img {
  max-width: 100%;
  max-height: 100%;
}

.go-to-profile-btn {
  color: inherit;
  border: 1px solid rgb(87, 87, 87);
  min-width: 30px;
  padding: 0 15px;
  color: rgb(87, 87, 87) !important;
  font-weight: 600;
  text-align: center;
}
.go-to-profile-btn:hover {
  background-color: rgba(0, 0, 0, 0.068);
}

.state-message {
  width: 200px;
  font-size: 16px;
  text-align: left;
}

.user-nickname {
  font-weight: 700;
  font-size: 16px;
}
</style>
