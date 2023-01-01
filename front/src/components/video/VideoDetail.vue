<template>
  <div class="commentdetail">
    <view-detail-content></view-detail-content>
    <view-detail-comments></view-detail-comments>
  </div>
</template>

<script>
import ViewDetailContent from "./ViewDetailContent.vue";
import ViewDetailComments from "./ViewDetailComments.vue";
import { mapState } from "vuex";
import axios from "@/util/http-common.js";
export default {
  components: {
    ViewDetailContent,
    ViewDetailComments,
  },
  created() {
    let id = this.$route.params.id;
    axios
      .get(`videoApi/video/${id}`)
      .then((res) => this.$store.commit("SET_VIDEO", res.data))
      .then(() => {
        axios
          .get(`commentApi/`, { params: { v_id: this.video.v_id } })
          .then((res) => this.$store.commit("SET_COMMENTS", res.data));
      })
      .then(() => {
        axios
          .get(`favoriteApi/favoritePeople`, {
            params: {
              v_id: this.video.v_id,
            },
          })
          .then((res) => {
            console.log(res.data);
            if (!res.data) {
              this.$store.commit("SET_VIDEO_FAVORITE_USERS", []);
            } else {
              this.$store.commit("SET_VIDEO_FAVORITE_USERS", res.data);
            }
          });
      });
  },
  computed: {
    ...mapState(["video", "comments", "videoFavoriteUsers"]),
  },
};
</script>

<style scoped>
.commentdetail {
  width: 900px;
  margin: auto;
}
</style>
