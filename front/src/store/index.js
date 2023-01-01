import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    LOGOUT_TIME: 1800,
    time: "",
    timer: "",
    min: "",
    sec: "",
    API_URL: "http://localhost:9999/api",
    curUser: {},
    myUser: {},
    myUserFollowing: [],
    curUserFollowers: [],
    curUserFollowing: [],
    video: {},
    videos: [],
    curFavoriteVideos: [],
    videoFavoriteUsers: [],
    comment: {},
    comments: [],
    receivedMsgs: [],
    sentMsgs: [],
    nicknames: [],
    loadedVideoSearch: false,
    loadedUserInfo: false,
  },
  getters: {},
  mutations: {
    SET_MIN(state, payload) {
      state.min = payload;
    },
    SET_SEC(state, payload) {
      state.sec = payload;
    },
    DECREASE_TIME(state) {
      state.time--;
    },
    SET_TIME(state, payload) {
      state.time = payload;
    },
    SET_TIMER(state, payload) {
      state.timer = payload;
    },
    RESET_TIMER(state) {
      clearInterval(state.timer);
    },
    SET_CUR_USER(state, payload) {
      state.curUser = payload;
    },

    SET_MY_USER(state, payload) {
      state.myUser = payload;
    },

    SET_MY_USER_FOLLOWING(state, payload) {
      state.myUserFollowing = payload;
    },

    SET_CUR_USER_FOLLOWERS(state, payload) {
      state.curUserFollowers = payload;
    },

    SET_CUR_USER_FOLLOWING(state, payload) {
      state.curUserFollowing = payload;
    },

    ADD_FOLLOWER(state) {
      state.myUserFollowing.push(state.curUser);
    },

    ADD_CUR_USER_FOLLOWER(state) {
      state.curUserFollowers.push(state.myUser);
    },

    DELETE_FOLLOWER(state) {
      state.myUserFollowing = state.myUserFollowing.filter(
        (f) => f.u_id !== state.curUser.u_id
      );
    },

    DELETE_CUR_USER_FOLLOWER(state) {
      state.curUserFollowers = state.curUserFollowers.filter(
        (f) => f.u_id !== state.myUser.u_id
      );
    },

    SET_VIDEO(state, payload) {
      state.video = payload;
    },

    SET_VIDEOS(state, payload) {
      state.videos = payload;
    },

    SET_CUR_FAVORITE_VIDEOS(state, payload) {
      state.curFavoriteVideos = payload;
    },

    SET_VIDEO_FAVORITE_USERS(state, payload) {
      state.videoFavoriteUsers = payload;
    },

    SORT_VIDEOS(state, payload) {
      switch (payload) {
        case "제목":
          console.log(payload);
          state.videos.sort((a, b) => a.title.localeCompare(b.title));
          break;
        case "조회수":
          state.videos.sort((b, a) => a.view_cnt - b.view_cnt);
          break;
        case "등록일":
          state.videos.sort((b, a) => a.reg_date.localeCompare(b.reg_date));
          break;
        case "좋아요수":
          state.videos.sort((b, a) => a.favorite_cnt - b.favorite_cnt);
          break;
      }
    },

    CLEAR_VIDEOS(state) {
      state.videos = [];
    },

    FAVORITE(state) {
      state.videoFavoriteUsers.push(state.myUser);
    },

    UNFAVORITE(state) {
      state.videoFavoriteUsers = state.videoFavoriteUsers.filter(
        (user) => user.u_id !== state.myUser.u_id
      );
    },

    SET_COMMENTS(state, payload) {
      state.comments = payload;
    },

    DELETE_COMMENT(state, payload) {
      state.comments = state.comments.filter((c) => c.c_id !== payload);
    },

    SET_RECEIVED_MSGS(state, payload) {
      state.receivedMsgs = payload;
    },

    SET_SENT_MSGS(state, payload) {
      state.sentMsgs = payload;
    },

    CLEAR_ALL(state) {
      state.curUser = {};
      state.myUser = {};
      state.myUserFollowing = [];
      state.videos = [];
      state.video = {};
      state.comments = [];
    },

    SET_NICKNAMES(state, payload) {
      state.nicknames = payload;
    },

    SET_LOADED_VIDEO_SEARCH(state) {
      state.loadedVideoSearch = true;
    },
    SET_LOADED_USER_INFO(state) {
      state.loadedUserInfo = true;
    },
  },
  actions: {},
  modules: {},
});
