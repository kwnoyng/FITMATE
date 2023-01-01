<template>
  <div class="usermain">
    <header>
      <div class="container">
        <div class="profile">
          <div class="profile-image">
            <img v-if="!curUser.img" />
            <img
              v-else
              :src="require('../../assets/profileImgs/' + curUser.img + '.png')"
              alt=""
            />
          </div>
          <div class="profile-user-settings"></div>
          <div class="profile-stats">
            <ul>
              <li>
                <span class="profile-user-name"
                  >{{ curUser.nickname }} &nbsp;</span
                >
                <span class="user-gender" v-if="curUser.gender === 'F'">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="40"
                    height="40"
                    fill="currentColor"
                    class="bi bi-gender-female"
                    viewBox="0 0 16 16"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8zM3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5z"
                    />
                  </svg>
                </span>
                <span class="user-gender" v-if="curUser.gender === 'M'">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="40"
                    height="40"
                    fill="currentColor"
                    class="bi bi-gender-male"
                    viewBox="0 0 16 16"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2H9.5zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8z"
                    /></svg
                ></span>
              </li>
            </ul>
            <ul></ul>
            <ul class="user-info">
              <li>
                <span>{{ curUser.email }} </span>
              </li>
            </ul>
            <ul>
              <li class="follower-following-btn" @click="showFollowers">
                <span class="profile-stat-count">{{
                  curUserFollowers.length
                }}</span>
                followers
              </li>
              <li class="follower-following-btn" @click="showFollowing">
                <span class="profile-stat-count">{{
                  curUserFollowing.length
                }}</span>
                following
              </li>
            </ul>

            <ul>
              <li class="profileinfobox">
                <span>
                  <router-link
                    class="btn profile-edit-btn"
                    v-if="curUser.u_id === myUser.u_id"
                    :to="{ name: 'UserViewUpdate' }"
                    >Edit profile</router-link
                  >
                  <button
                    class="btn profile-edit-btn"
                    v-else-if="checkIfFollow()"
                    @click="unfollow"
                  >
                    Unfollow
                  </button>
                  <button class="btn profile-edit-btn" v-else @click="follow">
                    Follow
                  </button>
                  <button
                    class="btn profile-edit-btn"
                    v-if="curUser.u_id === myUser.u_id"
                    @click="deleteUser"
                  >
                    Delete account
                  </button>
                  <router-link
                    class="btn profile-edit-btn"
                    v-if="curUser.u_id !== myUser.u_id"
                    :to="{
                      name: 'MessageSend',
                      params: { id: curUser.u_id ? curUser.u_id : 0 },
                    }"
                  >
                    Send message
                  </router-link>
                </span>
              </li>
            </ul>
            <hr />

            <div class="message">
              <ul>
                <li>
                  <span class="message">{{ curUser.state_message }}</span>
                </li>
              </ul>
              <ul>
                <li>
                  <span class="message">{{
                    curUser.address | sliceAddress
                  }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!-- End of profile section -->
      </div>
      <!-- End of container -->
    </header>

    <main>
      <hr class="videoline" />
      <h1 class="videotext">{{ curUser.nickname }}'s VIDEO</h1>
      <div v-if="videosExist()" class="container">
        <div class="videoview">
          <button @click="decreasePage">◀</button>
          <span> {{ curPage }} </span>
          <!-- <input v-model="curPage" /> -->
          <button @click="increasePage">▶</button>
        </div>
        <div class="gallery">
          <div
            class="gallery-item"
            tabindex="0"
            v-for="v in curVideos"
            :key="v.v_id"
            @click="moveDetail(v.v_id)"
          >
            <img
              :src="`https://img.youtube.com/vi/${v.v_id}/0.jpg`"
              style="cursor: pointer; width=200px;"
              class="gallery-image"
            />
            <div class="gallery-item-info">
              <ul>
                <li class="gallery-item-likes">
                  <span class="visually-hidden">Likes:</span
                  ><i class="fas fa-heart" aria-hidden="true"></i>
                </li>
                <li class="gallery-item-comments">
                  <span class="visually-hidden">Comments:</span
                  ><i class="fas fa-comment" aria-hidden="true"></i>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!-- End of gallery -->
      </div>
      <div class="no-video-message" v-else>No liked videos.</div>
      <!-- End of container -->
    </main>
    <user-view-modal v-if="showModal" @close="showModal = false">
      <h3 v-if="followerOrFollowing === 0" slot="header">Followers</h3>
      <h3 v-else slot="header">Following</h3>

      <div v-if="followerOrFollowing === 0" slot="body" class="user-container">
        <user-view-modal-row
          v-for="f in curUserFollowers"
          :key="f.u_id"
          :user="f"
          @close-modal="goToProfile(f.u_id)"
        ></user-view-modal-row>
      </div>
      <div v-else slot="body" class="user-container">
        <user-view-modal-row
          v-for="f in curUserFollowing"
          :key="f.u_id"
          :user="f"
          @close-modal="goToProfile(f.u_id)"
        ></user-view-modal-row>
      </div>

      <!-- <button slot="footer">등록</button> -->
    </user-view-modal>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";
import UserViewModal from "./UserViewModal.vue";
import UserViewModalRow from "./UserViewModalRow.vue";

export default {
  name: "UserViewMain",
  components: {
    UserViewModal,
    UserViewModalRow,
  },
  data() {
    return {
      showModal: false,
      // Follower = 0, Following = 1
      followerOrFollowing: 0,
      curPage: 1,
      curVideos: [],
      PAGE_SIZE: 6,
    };
  },
  computed: {
    ...mapState([
      "curUser",
      "myUser",
      "myUserFollowing",
      "curUserFollowers",
      "curUserFollowing",
      "curFavoriteVideos",
    ]),
    maxPage() {
      return (
        Math.floor((this.curFavoriteVideos.length - 1) / this.PAGE_SIZE) + 1
      );
    },
  },
  watch: {
    curPage(newVal) {
      this.curVideos = this.curFavoriteVideos.slice(
        (newVal - 1) * this.PAGE_SIZE,
        newVal * this.PAGE_SIZE
      );
    },
    curFavoriteVideos() {
      this.curPage = 1;
      this.curVideos = this.curFavoriteVideos.slice(
        (this.curPage - 1) * this.PAGE_SIZE,
        this.curPage * this.PAGE_SIZE
      );
    },
  },
  filters: {
    sliceAddress(address) {
      if (!address) return;
      const splitAddress = address.split(" ");
      return splitAddress.slice(0, 3).join(" ");
    },
    parsePhoneNo(phone_no) {
      if (!phone_no) {
        return;
      } else if (phone_no.length == 10) {
        return (
          phone_no.substring(0, 3) +
          "-" +
          phone_no.substring(3, 6) +
          "-" +
          phone_no.substring(6)
        );
      } else if (phone_no.length == 11) {
        return (
          phone_no.substring(0, 3) +
          "-" +
          phone_no.substring(3, 7) +
          "-" +
          phone_no.substring(7)
        );
      }
    },
  },
  methods: {
    videosExist() {
      const res = !(this.curFavoriteVideos.length === 0);
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
    goToProfile(u_id) {
      this.showModal = false;
      this.$router.push({
        name: "UserViewMain",
        params: {
          id: u_id ? u_id : 0,
        },
      });
    },
    showFollowers() {
      if (this.curUserFollowers.length === 0) return;
      this.followerOrFollowing = 0;
      this.showModal = true;
    },
    showFollowing() {
      if (this.curUserFollowing.length === 0) return;
      this.followerOrFollowing = 1;
      this.showModal = true;
    },
    moveDetail(v_id) {
      this.$router.push({
        name: "VideoDetail",
        params: { id: v_id },
      });
    },
    follow() {
      axios
        .post(`followApi/`, null, {
          params: {
            from_user: this.myUser.u_id,
            to_user: this.curUser.u_id,
          },
        })
        .then(() => {
          this.$store.commit("ADD_FOLLOWER");
          this.$store.commit("ADD_CUR_USER_FOLLOWER");
        });
    },
    unfollow() {
      axios
        .delete(`followApi/`, {
          params: {
            from_user: this.myUser.u_id,
            to_user: this.curUser.u_id,
          },
        })
        .then(() => {
          this.$store.commit("DELETE_FOLLOWER");
          this.$store.commit("DELETE_CUR_USER_FOLLOWER");
        });
    },
    checkIfFollow() {
      for (let f of this.myUserFollowing) {
        if (f.u_id === this.curUser.u_id) return true;
      }
      return false;
    },
    deleteUser() {
      axios
        .delete(`userApi/delete`, { params: { id: this.myUser.u_id } })
        .then(() => {
          this.$store.commit("CLEAR_ALL");
          // 세션정보 지우기 : 로그아웃
          sessionStorage.removeItem("access-token");
        })
        .then(() => this.$router.push("/"));
    },
  },
  created() {
    this.curVideos = this.curFavoriteVideos.slice(
      (this.curPage - 1) * this.PAGE_SIZE,
      this.curPage * this.PAGE_SIZE
    );
  },
};
</script>

<style scoped>
button {
  margin: auto 15px;
  width: 80px !important;
}
.videoview {
  margin-bottom: 2%;
}

.no-video-message {
  font-size: 20px;
  margin-bottom: 2%;
}

.user-title {
  font-weight: 400;
  margin: 2rem auto;
  opacity: 60%;
}
.user-info {
  /* display: flex; */
  min-width: 400px;
}

.user-info li {
  margin-left: 50px !important;
}

.usermain {
  max-width: inherit;
  padding-left: 4.5rem;
}

h1 {
  font-weight: 300;
}

.nickname {
  display: flex;
  max-width: inherit;
  justify-content: end;
}

button {
  height: auto;
  width: 40px;
}

table {
  width: auto;
  align-items: end;
}

.message {
  font-size: 1.3rem;
}

.videotext {
  font-weight: 400;
  font-size: 2.5rem;
  opacity: 60%;
  margin: 2rem;
}

.videoline {
  margin: 0 1.8rem;
}

/* 프로필 */
/*

All grid code is placed in a 'supports' rule (feature query) at the bottom of the CSS (Line 310).

The 'supports' rule will only run if your browser supports CSS grid.

Flexbox and floats are used as a fallback so that browsers which don't support grid will still recieve a similar layout.

*/

/* Base Styles */

:root {
  font-size: 8px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

img {
  display: block;
}

.container {
  max-width: 93.5rem;
  margin: 0 auto;
  padding: 0 2rem;
}

.btn {
  display: inline-block;
  font: inherit;
  background: none;
  border: none;
  color: inherit;
  padding: 0;
  cursor: pointer;
}

.btn:focus {
  outline: 0.5rem auto #4d90fe;
}

.visually-hidden {
  position: absolute !important;
  height: 1px;
  width: 1px;
  overflow: hidden;
  clip: rect(1px, 1px, 1px, 1px);
}

/* Profile Section */

.profile {
  padding: 2rem 0;
  max-width: inherit;
}

.profile::after {
  content: "";
  display: block;
  clear: both;
}

.profile-image {
  float: left;
  width: calc(33.333% - 1rem);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 3rem;
  width: 200px;
}

.profile-image img {
  border-radius: 50%;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
  float: right;
  width: calc(66.666% - 2rem);
}

.profile-user-settings {
  margin-top: 1.1rem;
}

.profile-user-name {
  font-size: 3rem;
  font-weight: 400;
}

.profile-edit-btn {
  font-size: 1.6rem;
  line-height: 2;
  border: 0.1rem solid #dbdbdb;
  border-radius: 0.3rem;
  padding: 0.5rem 2.6rem;
  margin: 0.9rem;
  color: #fff;
}

.profile-settings-btn {
  font-size: 2rem;
  margin-left: 1rem;
}

.profile-stats {
  margin-top: 2.3rem;
  text-align: end;
  width: 300px;
}

.profile-stats li {
  display: inline-block;
  font-size: 1.6rem;
  line-height: 1.5;
  margin-left: 4rem;
  /* cursor: pointer; */
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.profile-real-name,
.profile-stat-count,
.profile-edit-btn {
  font-weight: 500;
  margin: 5px;
}

.follower-following-btn {
  cursor: pointer;
}

/* Gallery Section */

.gallery {
  display: flex;
  flex-wrap: wrap;
  justify-items: center;
  margin: -1rem -1rem;
  padding-bottom: 3rem;
}

.gallery-item {
  position: relative;
  flex: 1 0 22rem;
  margin: 1rem;
  color: #fff;
  cursor: pointer;
  max-width: 700px;
  margin: auto;
}

.gallery-item:hover .gallery-item-info,
.gallery-item:focus .gallery-item-info {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
}

.gallery-item-info {
  display: none;
  border-radius: 20px;
}

.gallery-item-info li {
  display: inline-block;
  font-size: 1.7rem;
  font-weight: 300;
}

.gallery-item-likes {
  position: absolute;
  top: 50%;
  left: 34%;
  transform: translateY(-50%);
  /* margin-right: 2.2rem; */
}

.gallery-item-comments {
  position: absolute;
  top: 50%;
  left: 51%;
  transform: translateY(-50%);
}

.gallery-item-type {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 2.5rem;
  text-shadow: 0.2rem 0.2rem 0.2rem rgba(0, 0, 0, 0.1);
}

.fa-clone,
.fa-comment {
  transform: rotateY(180deg);
}

.gallery-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px;
}

/* Media Query */

@media screen and (max-width: 40rem) {
  .profile {
    display: flex;
    flex-wrap: wrap;
    padding: 4rem 0;
    margin: auto;
  }

  .profile::after {
    display: none;
  }

  .profile-image,
  .profile-user-settings,
  .profile-bio,
  .profile-stats {
    float: none;
    width: auto;
  }

  .profile-image img {
    width: 7.7rem;
  }

  .profile-user-settings {
    flex-basis: calc(100% - 10.7rem);
    display: flex;
    flex-wrap: wrap;
    margin-top: 1rem;
  }

  .profile-user-name {
    font-size: 3rem;
  }

  .profile-edit-btn {
    order: 1;
    padding: 0;
    text-align: center;
    margin-top: 1rem;
  }

  .profile-edit-btn {
    margin-left: 0;
  }

  .profile-bio {
    font-size: 1.4rem;
    margin-top: 1.5rem;
  }

  .profile-edit-btn,
  .profile-bio,
  .profile-stats {
    flex-basis: 100%;
  }

  .profile-stats {
    order: 1;
    margin-top: 1.5rem;
  }

  .profile-stats ul {
    display: flex;
    text-align: center;
  }

  .profile-stats li {
    font-size: 1.4rem;
    flex: 1;
    margin: 0;
  }

  .profile-stat-count {
    display: block;
  }
}

/* Spinner Animation */

@keyframes loader {
  to {
    transform: rotate(360deg);
  }
}

/*

The following code will only run if your browser supports CSS grid.

Remove or comment-out the code block below to see how the browser will fall-back to flexbox & floated styling.

*/

@supports (display: grid) {
  .profile {
    display: grid;
    grid-template-columns: 1fr 2fr;
    grid-template-rows: repeat(3, auto);
    grid-column-gap: 3rem;
    align-items: center;
  }

  .profile-image {
    grid-row: 1 / -1;
  }

  .gallery {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(22rem, 1fr));
    grid-gap: 2rem;
  }

  .profile-image,
  .profile-user-settings,
  .profile-stats,
  .profile-bio,
  .gallery-item,
  .gallery {
    width: auto;
    margin: 0;
  }

  @media (max-width: 40rem) {
    .profile {
      grid-template-columns: auto 1fr;
      grid-row-gap: 1.5rem;
    }

    .profile-image {
      grid-row: 1 / 2;
    }

    .profile-user-settings {
      display: grid;
      grid-template-columns: auto 1fr;
      grid-gap: 1rem;
    }

    .profile-edit-btn,
    .profile-stats,
    .profile-bio {
      grid-column: 1 / -1;
    }

    .profile-user-settings,
    .profile-edit-btn,
    .profile-settings-btn,
    .profile-bio,
    .profile-stats {
      margin: 0;
    }
  }
}
</style>
