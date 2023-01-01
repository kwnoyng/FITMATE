<template>
  <div>
    <form @submit.prevent="searchVideo">
      <input type="checkbox" id="전신" value="전신" v-model="category" />
      <label for="전신">전신</label>
      <input type="checkbox" id="가슴" value="가슴" v-model="category" />
      <label for="가슴">가슴</label>
      <input type="checkbox" id="상체" value="상체" v-model="category" />
      <label for="상체">상체</label>
      <input type="checkbox" id="어깨" value="어깨" v-model="category" />
      <label for="어깨">어깨</label>
      <input type="checkbox" id="등" value="등" v-model="category" />
      <label for="등">등</label>
      <input type="checkbox" id="하체" value="하체" v-model="category" />
      <label for="하체">하체</label>
      <input type="checkbox" id="복근" value="복근" v-model="category" />
      <label for="복근">복근</label>
      <input type="checkbox" id="이두" value="이두" v-model="category" />
      <label for="이두">이두</label>
      <input type="checkbox" id="삼두" value="삼두" v-model="category" />
      <label for="삼두">삼두</label>
      <input type="checkbox" id="팔뚝" value="팔뚝" v-model="category" />
      <label for="팔뚝">팔뚝</label>
      <br />
      <div class="search">
        <select v-model="range" class="searchinput">
          <option>전체</option>
          <option>제목</option>
          <option>채널명</option>
        </select>
        <input type="text" v-model="keyword" />
      </div>
      <button class="searchbtn">검색</button>
      <input type="button" @click="clearVideos" value="CLEAR" />
    </form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {
      range: "전체",
      category: [],
      keyword: "",
    };
  },
  methods: {
    clearVideos() {
      this.$store.commit("CLEAR_VIDEOS");
    },
    searchVideo() {
      axios
        .get(
          `videoApi/video?${this.category
            .map((n) => `category=${n}`)
            .join("&")}`,
          {
            params: {
              keyword: this.keyword,
              range: this.range,
            },
          }
        )
        .then((res) => this.$store.commit("SET_VIDEOS", res.data));
    },
  },
  computed: {
    ...mapState(["videos"]),
  },
};
</script>

<style scoped>
.search {
  display: flex;
  margin: auto 6rem;
}

.searchinput {
  width: 15rem;
  margin-right: 0.5rem;
}

.searchbtn {
  min-width: 0;
  appearance: none;
  transition: background-color 0.2s ease-in-out, color 0.2s ease-in-out;
  border-radius: 8px;
  border: 0;
  cursor: pointer;
  display: inline-block;
  font-weight: 300;
  height: 2.75em;
  line-height: 2.75em;
  min-width: 9.25em;
  padding: 0 1.5em;
  text-align: center;
  text-decoration: none;
  white-space: nowrap;
  font-size: 12pt;
  margin: 2%;
  padding: 0;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
}
</style>
