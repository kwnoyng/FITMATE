<template>
  <div class="row" @click="moveDetail">
    <img
      :src="`https://img.youtube.com/vi/${video.v_id}/0.jpg`"
      width="450px"
    />
    <span class="title">{{ video.title }}</span>
    <div class="binding2">
      <span class="category">{{ video.category }} 운동</span>
      <span>{{ video.v_writer }}</span>
    </div>
    <div class="binding2">
      <span class="regdate">{{ video.reg_date }}</span>
      <span>조회수 : {{ video.view_cnt }}</span>
    </div>
    <!-- <p>좋아요수 : {{ video.favorite_cnt }}</p> -->
  </div>
</template>

<script>
export default {
  name: "SearchVideoResultRow",
  props: {
    video: Object,
  },
  methods: {
    moveDetail() {
      this.$router.push({
        name: "VideoDetail",
        params: { id: this.video.v_id },
      });
    },
  },
};
</script>

<style scoped>
img {
  border-radius: 20px;
  margin: 0;
  padding: 0;
}

div {
  cursor: pointer;
}

.row {
  font-size: smaller;
  display: inline;
  margin: 0%;
}

.title {
  text-align: start;
  width: auto;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 25px;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  margin: 0%;
}

.binding2 {
  width: auto;
  display: flex;
  justify-content: space-between;
}

.category,
.regdate {
  margin-right: 3%;
}
</style>
