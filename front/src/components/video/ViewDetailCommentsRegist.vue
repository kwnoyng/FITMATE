<template>
  <div class="commentflexbox">
    <img
      class="profile-pic"
      v-if="loadedVideoSearch"
      :src="require('../../assets/profileImgs/' + myUser.img + '.png')"
      alt=""
    />
    <textarea
      class="comment-text"
      cols="30"
      rows="3"
      v-model.trim="curComment"
      placeholder="내용을 입력하세요"
    >
    </textarea>
    <button class="btn" @click="registComment">등록</button>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  name: "ViewDetailCommentsRegist",
  data() {
    return {
      curComment: "",
    };
  },
  methods: {
    registComment() {
      if (!this.curComment) return;
      axios
        .post(`commentApi/`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
            content: this.curComment,
          },
        })
        .then(() => {
          axios
            .get(`commentApi/`, { params: { v_id: this.video.v_id } })
            .then((res) => this.$store.commit("SET_COMMENTS", res.data));
        })
        .then(() => (this.curComment = ""));
    },
  },
  computed: {
    ...mapState([
      "API_URL",
      "myUser",
      "video",
      "comments",
      "loadedVideoSearch",
    ]),
  },
  watch: {
    // loadedVideoSearch(newVal) {
    //   if (newVal) {
    //   }
    // },
  },
};
</script>

<style scoped>
.profile-pic {
  width: 80px;
  height: 80px;
  margin: 0 20px;
  border-radius: 50%;
  overflow: hidden;
  display: inline-block;
}
.profile-pic img {
  max-width: 100%;
  max-height: 100%;
}
.commentflexbox {
  display: flex;
  align-items: center;
  margin-bottom: 0.7rem;
}

.comment-text {
  overflow: hidden;
  resize: none;
}

textarea {
  width: 600px;
  height: 70px;
}

.btn {
  height: 70px;
  margin-left: 0.5rem;
  /* width: 30px; */
}
</style>
