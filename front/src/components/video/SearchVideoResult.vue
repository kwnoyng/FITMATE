<template>
  <div>
    <div v-if="videosExist()">
      <hr />

      <!-- <div>
      <button @click="sortCriteria = '제목'">제목</button>
      <button @click="sortCriteria = '조회수'">조회수</button>
      <button @click="sortCriteria = '등록일'">등록일</button>
      <button @click="sortCriteria = '좋아요수'">좋아요수</button>
    </div> -->
      <!-- pagination -->

      <!-- <input type="number" v-model="curPage" /> -->
      <div class="videoview">
        <div class="button-flexbox">
          <button @click="decreasePage">◀</button>
          <span class="page-num"> {{ curPage }} </span>
          <!-- <input v-model="curPage" /> -->
          <button @click="increasePage">▶</button>
        </div>
        <div class="select-box">
          <label for="sort">Sort</label>
          <select id="sort" v-model="sortCriteria">
            <option disabled>----</option>
            <option>제목</option>
            <option>조회수</option>
            <option>등록일</option>
            <option>좋아요수</option>
          </select>
        </div>
      </div>
      <div class="videoflexbox">
        <div v-for="video in curVideos" :key="video.id" class="videodiv">
          <search-video-result-row :video="video"></search-video-result-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SearchVideoResultRow from "@/components/video/SearchVideoResultRow.vue";
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["videos"]),
    maxPage() {
      return Math.floor((this.videos.length - 1) / this.PAGE_SIZE) + 1;
    },
  },
  components: {
    SearchVideoResultRow,
  },
  data() {
    return {
      sortCriteria: "----",
      curPage: 1,
      curVideos: [],
      PAGE_SIZE: 6,
    };
  },
  methods: {
    videosExist() {
      const res = !(this.videos.length === 0);
      return res;
    },
    increasePage() {
      if (this.curPage === this.maxPage) return;
      this.curPage++;
    },
    decreasePage() {
      if (this.curPage === 1) return;
      this.curPage--;
    },
  },
  watch: {
    sortCriteria(newVal) {
      this.$store.commit("SORT_VIDEOS", newVal);
    },
    curPage(newVal) {
      this.curVideos = this.videos.slice(
        (newVal - 1) * this.PAGE_SIZE,
        newVal * this.PAGE_SIZE
      );
    },
    videos() {
      this.curPage = 1;
      this.curVideos = this.videos.slice(
        (this.curPage - 1) * this.PAGE_SIZE,
        this.curPage * this.PAGE_SIZE
      );
    },
  },
  created() {
    this.curVideos = this.videos.slice(
      (this.curPage - 1) * this.PAGE_SIZE,
      this.curPage * this.PAGE_SIZE
    );
  },
};
</script>

<style scoped>
label {
  font-size: 2rem;
  margin-top: 1.6rem;
}

button {
  margin: auto 15px;
  width: 80px !important;
  height: 3.5em;
}

.button-flexbox {
  display: flex;
}

.select-box {
  display: flex;
  align-items: center;
  gap: 1rem;
  height: 2rem;
}

.page-num {
  font-size: 2rem;
}

#sort {
  margin-bottom: 2%;
  width: 10rem;
}
.videoview {
  margin: 2rem auto;
  display: flex;
  align-items: center;
  width: 50rem;
  justify-content: center;
  gap: 5rem;
}
.videodiv {
  width: 450px;
  margin-bottom: 8px;
}

.videoflexbox {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
</style>
