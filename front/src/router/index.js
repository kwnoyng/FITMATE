import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import VideoSearchView from "../views/VideoSearchView.vue";
import UserView from "../views/UserView.vue";
import VideoSearch from "../components/video/VideoSearch.vue";
import VideoDetail from "../components/video/VideoDetail.vue";
import UserViewMain from "../components/user/UserViewMain.vue";
import UserViewUpdate from "../components/user/UserViewUpdate.vue";
import MateView from "../views/MateView.vue";
import MessageView from "../views/MessageView.vue";
import MessageReceived from "../components/message/MessageReceived.vue";
import MessageSent from "../components/message/MessageSent.vue";
import MessageSend from "../components/message/MessageSend.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/register",
    name: "RegisterView",
    component: RegisterView,
  },
  {
    path: "/message",
    name: "MessageView",
    component: MessageView,
    children: [
      {
        path: "received",
        name: "MessageReceived",
        component: MessageReceived,
      },
      {
        path: "sent",
        name: "MessageSent",
        component: MessageSent,
      },
      {
        path: "send/:id",
        name: "MessageSend",
        component: MessageSend,
      },
    ],
  },
  {
    path: "/home",
    name: "HomeView",
    component: HomeView,
    redirect: "/home/mate",
    children: [
      {
        path: "mate",
        name: "MateView",
        component: MateView,
      },

      {
        path: "videosearch",
        name: "VideoSearchView",
        component: VideoSearchView,
        redirect: "/home/videosearch/search",
        children: [
          {
            path: "search",
            name: "VideoSearch",
            component: VideoSearch,
          },
          {
            path: "detail/:id",
            name: "VideoDetail",
            component: VideoDetail,
          },
        ],
      },
      {
        path: "user/:id",
        // name: "UserView",
        component: UserView,
        children: [
          {
            path: "",
            name: "UserViewMain",
            component: UserViewMain,
          },
          {
            path: "update",
            name: "UserViewUpdate",
            component: UserViewUpdate,
          },
        ],
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
