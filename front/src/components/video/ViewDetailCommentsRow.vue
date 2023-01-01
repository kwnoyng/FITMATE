<template>
  <div class="comment">
    <div class="profile-pic-container">
      <img
        class="profile-pic"
        v-if="loadedVideoSearch"
        :src="require('../../assets/profileImgs/' + comment.img + '.png')"
        alt=""
      />
      {{ comment.nickname }}
    </div>
    <div>
      <textarea
        class="comment-text"
        cols="30"
        rows="3"
        v-model="tmpContent"
        :readonly="isReadonly"
      ></textarea>
      <div class="regdate">{{ comment.reg_date }}</div>
    </div>
    <div>
      <div v-if="myUser.u_id === comment.u_id" class="btns">
        <button @click="updateComment">수정</button>
        <button @click="deleteComment">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  name: "ViewDetailCommentsRow",
  props: {
    comment: Object,
  },
  data() {
    return {
      tmpContent: "",
      isReadonly: true,
      img: "",
    };
  },
  methods: {
    updateComment() {
      if (this.isReadonly) {
        this.isReadonly = false;
        return;
      }
      this.isReadonly = true;
      axios
        .put(`commentApi/`, null, {
          params: {
            c_id: this.comment.c_id,
            content: this.tmpContent,
          },
        })
        .then(() => {
          console.log(this.video);
          axios
            .get(`commentApi/`, { params: { v_id: this.video.v_id } })
            .then((res) => this.$store.commit("SET_COMMENTS", res.data));
        });
    },
    deleteComment() {
      axios
        .delete(`commentApi/${this.comment.c_id}`)
        .then(() => this.$store.commit("DELETE_COMMENT", this.comment.c_id));
    },
  },
  computed: {
    ...mapState(["myUser", "video", "loadedVideoSearch"]),
  },
  created() {
    this.tmpContent = this.comment.content;
  },
};
</script>

<style scoped>
textarea {
  width: 600px;
  height: 70px;
  border: 0px;
}

.comment {
  display: flex;
  /* align-items: center; */
  margin-top: 2%;
}

.comment-text {
  overflow: hidden;
  resize: none;
}

.profile-pic-container {
  display: flex;
  flex-direction: column;
}

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

button {
  margin-left: 0.6rem;

  /* margin: 0px 20px; */
}

.btns {
  height: 100px;
  /* margin-left: 2%;
  width: 30px; */
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  gap: 0.5rem;
}

.regdate {
  text-align: end;
  /* font-size: x-large; */
}
</style>
