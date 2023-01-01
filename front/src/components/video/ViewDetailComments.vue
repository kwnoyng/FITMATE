<template>
  <div>
    <view-detail-comments-regist></view-detail-comments-regist>
    <view-detail-comments-row
      v-for="comment in comments"
      :key="comment.c_id"
      :comment="comment"
    ></view-detail-comments-row>
  </div>
</template>

<script>
import ViewDetailCommentsRow from "@/components/video/ViewDetailCommentsRow.vue";
import ViewDetailCommentsRegist from "@/components/video/ViewDetailCommentsRegist.vue";
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["comments"]),
  },
  components: {
    ViewDetailCommentsRow,
    ViewDetailCommentsRegist,
  },
};
</script>

<style></style>
