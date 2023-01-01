<template>
  <div>
    <h1 class="video">VIDEO SEARCH</h1>
    <search-video-input></search-video-input>
    <search-video-result></search-video-result>
  </div>
</template>

<script>
import SearchVideoInput from "./SearchVideoInput.vue";
import SearchVideoResult from "./SearchVideoResult.vue";
export default {
  components: {
    SearchVideoInput,
    SearchVideoResult,
  },
};
</script>

<style scoped>
.video {
  font-weight: 400;
  margin: 2rem auto;
  opacity: 60%;
}
</style>
