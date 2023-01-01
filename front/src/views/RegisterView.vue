<template>
  <div>
    <the-header-vue thisIsRegister="true"></the-header-vue>
    <div class="registerform">
      <h2>회원가입</h2>
      <div class="profile-pic" ref="profilePic">
        <img v-if="!imgNum" />
        <img
          v-else
          :src="require('../assets/profileImgs/img_' + imgNum + '.png')"
          alt="Profile Image"
          class="selected-profile-pic"
        />
      </div>
      <button class="photobtn" @click="showModal = true">사진 등록</button>
      <hr />
      <form @submit.prevent="registUser" class="registinput">
        <table>
          <tr>
            <th>아이디</th>
            <td><input type="text" v-model="user.u_id" required /></td>
            <td>
              <button
                type="button"
                @click="checkDuplicate"
                :class="{ ok: isOk }"
                id="Duplicate"
              >
                {{ duplicateMsg }}
              </button>
            </td>
          </tr>
          <tr>
            <th>비밀번호</th>
            <td>
              <input
                type="password"
                v-model="user.pw"
                placeholder="비밀번호 길이: 4 ~ 20자"
                required
              />
            </td>
          </tr>
          <tr>
            <th>비밀번호 확인</th>
            <td>
              <input type="password" v-model="passwordCheck" required />
              <p>{{ passwordMessage }}</p>
            </td>
          </tr>
          <tr></tr>
          <tr>
            <th>성명</th>
            <td><input type="text" v-model="user.name" required /></td>
          </tr>
          <tr>
            <th>성별</th>
            <td>
              <div>
                <input
                  type="radio"
                  v-model="user.gender"
                  name="gender"
                  value="M"
                  id="M"
                />
                <label for="M">남</label>

                <input
                  type="radio"
                  v-model="user.gender"
                  name="gender"
                  value="F"
                  id="F"
                />
                <label for="F">여</label>
              </div>
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td><input type="email" v-model="user.email" required /></td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>
              <input
                class="telinput"
                type="tel"
                pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}"
                v-model="user.phone_no"
                placeholder="예 : 010-123(4)-5678"
                required
              />
            </td>
          </tr>
          <tr>
            <th>우편번호</th>
            <td>
              <input type="text" v-model="zipcode" disabled required />
            </td>
            <td>
              <input type="button" value="우편번호 찾기" @click="kakaopost" />
            </td>
          </tr>
          <tr>
            <th>주소</th>
            <td>
              <input type="text" v-model="broadAddress" disabled required />
            </td>
          </tr>
          <tr>
            <th>상세주소</th>
            <td>
              <input
                type="text"
                v-model="detailAddress"
                placeholder="상세주소를 입력하세요."
              />
            </td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td><input type="text" v-model="user.nickname" required /></td>
          </tr>
          <tr>
            <th>상태메시지</th>
            <td>
              <textarea
                v-model="user.state_message"
                placeholder="관심있는 운동과 함께 인사말을 작성해 보세요."
              />
            </td>
          </tr>
        </table>
        <div class="btns">
          <button class="registBtn">회원가입</button>
          <input type="button" @click="toLogin" value="돌아가기" />
        </div>
      </form>

      <register-view-modal v-if="showModal" @close="showModal = false">
        <h3 slot="header">Select your profile image</h3>
        <div slot="body" class="photo-container">
          <img
            v-for="n in 10"
            :key="n"
            :src="require('../assets/profileImgs/img_' + n + '.png')"
            alt="Profile pic options"
            width="60px"
            height="60px"
            @click="
              imgNum = n;
              showModal = false;
            "
          />
        </div>
        <!-- <button slot="footer">등록</button> -->
      </register-view-modal>
    </div>
  </div>
</template>

<script>
import axios from "@/util/http-common.js";
import axiosRaw from "axios";
import RegisterViewModal from "./RegisterViewModal.vue";
import TheHeaderVue from "@/components/common/TheHeader.vue";

export default {
  name: "RegisterView",
  components: {
    RegisterViewModal,
    TheHeaderVue,
  },
  data() {
    return {
      user: {
        u_id: "",
        pw: "",
        name: "",
        email: "",
        gender: "M",
        phone_no: "",
        nickname: "",
        address: "",
        x: "",
        y: "",
        img: "",
        state_message: "",
      },
      isOk: false,
      zipcode: "",
      broadAddress: "",
      detailAddress: "",
      showModal: false,
      imgNum: "",
      passwordCheck: "",
    };
  },
  methods: {
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
    registUser() {
      if (!this.isOk) {
        alert("아이디 중복확인을 완료해 주세요.");
        return;
      }

      if (!this.isPasswordSame) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }

      this.user.phone_no = this.user.phone_no.split("-").join("");
      this.user.address = `${this.broadAddress} ${this.detailAddress}`;
      if (this.user.img === "") {
        this.user.img = 1;
      }

      const KAKAO_KEY = process.env.VUE_APP_KAKAO_REST_API_KEY;
      console.log(KAKAO_KEY);
      console.log(this.user.address);
      axiosRaw({
        url: "https://dapi.kakao.com/v2/local/search/address",
        method: "GET",
        params: {
          query: this.broadAddress,
        },
        headers: {
          Authorization: `KakaoAK ${KAKAO_KEY}`,
        },
      })
        .then((res) => {
          const coords = res.data.documents[0];
          console.log(coords);
          this.user.x = coords.x;
          this.user.y = coords.y;
          this.user.img = "img_" + this.imgNum;
        })
        .then(() => {
          axios({
            url: "userApi/regist",
            method: "POST",
            params: this.user,
          })
            // .post(`userApi/regist`, null, { params: this.user })
            .then(() => this.$router.push({ name: "LoginView" }))
            .catch((err) => {
              console.log(err.response.data);
              switch (err.response.data) {
                case "dup_email":
                  alert("이미 등록된 이메일 입니다.");
                  break;
                case "dup_nickname":
                  alert("이미 등록된 닉네임 입니다.");
                  break;
                case "dup_phone":
                  alert("이미 등록된 전화번호 입니다.");
                  break;
              }
              this.user.phone_no = this.parsePhoneNo(this.user.phone_no);
            });
        })
        .catch((err) => {
          console.log(err.message);
          this.user.phone_no = this.parsePhoneNo(this.user.phone_no);
        });
    },
    checkDuplicate() {
      if (!this.user.u_id) {
        this.isOk = false;
        return;
      }
      axios({
        url: "userApi/check",
        method: "GET",
        params: { id: this.user.u_id },
      }).then((res) => {
        console.log(res);
        if (res.data === "fail") {
          alert("이미 사용중인 아이디입니다.");
          this.isOk = false;
        } else {
          alert("사용 가능한 아이디입니다.");
          this.isOk = true;
        }
      });
    },
    toLogin() {
      this.$router.push({ name: "LoginView" });
    },
    kakaopost() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.zipcode = data.zonecode;
          this.broadAddress = data.address;
        },
      }).open();
    },
  },
  created() {
    if (sessionStorage.getItem("access-token")) {
      this.$router.push({ name: "HomeView" });
    }
  },
  computed: {
    duplicateMsg() {
      return this.isOk ? "중복확인 완료" : "중복확인";
    },
    passwordMessage() {
      if (!this.passwordCheck) return "______";
      if (this.user.pw === this.passwordCheck) {
        console.log(this.user.pw.length);
        if (4 <= this.user.pw.length && this.user.pw.length <= 20) {
          return "비밀번호가 일치합니다.";
        } else {
          return "비밀번호 길이는 4 ~ 20자 이어야 합니다.";
        }
      } else {
        return "비밀번호가 일치하지 않습니다.";
      }
    },
    isPasswordSame() {
      return (
        this.user.pw === this.passwordCheck &&
        4 <= this.user.pw.length &&
        this.user.pw.length <= 20
      );
    },
  },
  watch: {
    imgNum() {
      if (this.imgNum) {
        console.log(this.$refs.profilePic);
        this.$refs.profilePic.style.border = "none";
      }
    },
  },
};
</script>

<style scoped>
.selected-profile-pic {
  border: none;
}

.ok {
  background-color: rgba(175, 182, 245, 0.507);
}

table {
  margin: 0 auto;
}

.profile-pic {
  border: 1px solid rgb(255, 255, 255);
  width: 150px;
  height: 150px;
  margin: 0 auto;
  border-radius: 50%;
  overflow: hidden;
}

.profile-pic img {
  max-width: 100%;
  max-height: 100%;
}

.telinput {
  background-color: rgba(255, 255, 255, 0.075);
  border-color: rgba(255, 255, 255, 0.35);
  font-family: "Source Sans Pro", Helvetica, sans-serif;
  font-weight: 300;
  line-height: 1.65;
  appearance: none;
  border-radius: 8px;
  border: solid 1px;
  color: inherit;
  display: block;
  outline: 0;
  padding: 0 1em;
  text-decoration: none;
  width: 100%;
  height: 2.75em;
}

.registerform {
  text-align: center;
}

.photobtn {
  margin: 3%;
}

.registinput {
  padding: 0 20% 0 20%;
}

.registBtn,
#Duplicate {
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

.btns {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
