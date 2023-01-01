<template>
  <div>
    <h1 class="detailtitle">DETAIL VIDEO</h1>
    <div class="container">
      <iframe
        :src="`https://www.youtube.com/embed/${video.v_id}`"
        frameborder="0"
        width="800px"
        height="450px"
      ></iframe>
      <div class="text">
        <span class="video-detail-title">{{ video.title }}</span>
        <br />
        <br />
        <div class="binding">
          <span class="category">{{ video.category }} 운동</span>
          <span>{{ video.v_writer }}</span>
        </div>
        <div class="binding">
          <span class="regdate">{{ video.reg_date }}</span>
          <span>조회수 {{ video.view_cnt }}</span>
        </div>

        <div class="like">
          <p>좋아요 {{ videoFavoriteUsers.length }}</p>

          <button v-if="checkIfFavorite()" @click="unfavorite">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-heart-fill"
              viewBox="0 0 16 16"
            >
              <path
                fill-rule="evenodd"
                d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"
              />
            </svg>
          </button>
          <button v-else @click="favorite">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-heart"
              viewBox="0 0 16 16"
            >
              <path
                d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"
              />
            </svg>
          </button>
        </div>
        <hr />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(["video", "myUser", "videoFavoriteUsers"]),
  },
  watch: {
    videoFavoriteUsers() {},
  },
  methods: {
    checkIfFavorite() {
      for (let user of this.videoFavoriteUsers) {
        if (user.u_id === this.myUser.u_id) return true;
      }
      return false;
    },
    favorite() {
      axios
        .post(`favoriteApi/favoriteUp`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
          },
        })
        .then(() => this.$store.commit("FAVORITE"));
    },
    unfavorite() {
      axios
        .post(`favoriteApi/favoriteDown`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
          },
        })
        .then(() => this.$store.commit("UNFAVORITE"));
    },
  },
};
</script>

<style scoped>
svg {
  margin-top: 0.65rem;
}

.detailtitle {
  font-weight: 400;
  margin: 2rem auto;
  opacity: 60%;
}

.container {
  width: 800px;
  margin: auto;
}

iframe {
  margin: 0px 0px;
}
.binding {
  width: auto;
  display: flex;
  justify-content: space-between;
}

.text {
  text-align: start;
  /* width: 800px; */
  margin: auto;
}

.like {
  display: flex;
  gap: 2%;
  justify-content: end;
}

.video-detail-title {
  width: 800px;
}
</style>
