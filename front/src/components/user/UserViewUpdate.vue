<template>
  <div class="editinformation">
    <h1>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="50"
        height="50"
        fill="currentColor"
        class="bi bi-pen-fill"
        viewBox="0 0 16 16"
      >
        <path
          d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001z"
        />
      </svg>
      &nbsp; Edit Information
    </h1>
    <form @submit.prevent="updateUser">
      <table>
        <tr>
          <th>ID</th>
          <td><input type="text" v-model="tmpUser.u_id" readonly /></td>
        </tr>
        <tr>
          <th>PW</th>
          <td><input type="password" v-model="tmpUser.pw" required /></td>
        </tr>
        <tr>
          <th>PW CHECK</th>
          <td>
            <input type="password" v-model="passwordCheck" required />
            <p>{{ passwordMessage }}</p>
          </td>
        </tr>
        <tr>
          <th>NAME</th>
          <td><input type="text" v-model="tmpUser.name" required /></td>
        </tr>
        <tr>
          <th>NICKNAME</th>
          <td><input type="text" v-model="tmpUser.nickname" /></td>
        </tr>
        <tr>
          <th>EMAIL</th>
          <td><input type="email" v-model="tmpUser.email" required /></td>
        </tr>
        <tr>
          <th>GENDER</th>
          <td>
            <div>
              <input
                type="radio"
                v-model="tmpUser.gender"
                name="gender"
                value="M"
                id="M"
              />
              <label for="M" value="남">Male</label>

              <input
                type="radio"
                v-model="tmpUser.gender"
                name="gender"
                value="F"
                id="F"
              />
              <label for="F">Female</label>
            </div>
          </td>
        </tr>
        <tr>
          <th>연락처</th>
          <td>
            <input
              class="telinput"
              type="tel"
              pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}"
              v-model="tmpUser.phone_no"
              placeholder="예 : 010-123(4)-5678"
            />
          </td>
        </tr>
        <tr>
          <th>상태메시지</th>
          <td><input type="text" v-model="tmpUser.state_message" /></td>
        </tr>
      </table>
      <button>회원정보수정</button>
    </form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {
      tmpUser: {},
      passwordCheck: "",
    };
  },
  computed: {
    ...mapState(["myUser"]),
    passwordMessage() {
      if (!this.passwordCheck) return "______";
      if (this.myUser.pw === this.passwordCheck) {
        console.log(this.myUser.pw.length);
        if (4 <= this.myUser.pw.length && this.myUser.pw.length <= 20) {
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
        this.myUser.pw === this.passwordCheck &&
        4 <= this.myUser.pw.length &&
        this.myUser.pw.length <= 20
      );
    },
  },
  created() {
    this.tmpUser = { ...this.myUser };
    this.tmpUser.phone_no = this.parsePhoneNo(this.tmpUser.phone_no);
    this.tmpUser.pw = "";
    console.log(this.tmpUser);
  },
  watch: {
    myUser(newVal) {
      // console.log(newVal);
      this.tmpUser = { ...newVal };
      this.tmpUser.phone_no = this.parsePhoneNo(this.tmpUser.phone_no);
      this.tmpUser.pw = "";
    },
  },
  methods: {
    updateUser() {
      // console.log(this.tmpUser, "FEWFawefwfwaffw");
      this.tmpUser.phone_no = this.tmpUser.phone_no.split("-").join("");
      axios
        .put(`userApi/update`, null, { params: this.tmpUser })
        .then(() => {
          this.$store.commit("SET_MY_USER", this.tmpUser);
          this.$store.commit("SET_CUR_USER", this.tmpUser);
        })
        .then(() => this.$router.push({ name: "UserViewMain" }));
    },
    parsePhoneNo(phone_no) {
      // console.log(this.myUser.phone_no);
      if (!phone_no) {
        return;
      }
      if (phone_no.length == 10) {
        return (
          phone_no.slice(0, 3) +
          "-" +
          phone_no.slice(3, 6) +
          "-" +
          phone_no.slice(6)
        );
      } else if (phone_no.length == 11) {
        return (
          phone_no.slice(0, 3) +
          "-" +
          phone_no.slice(3, 7) +
          "-" +
          phone_no.slice(7)
        );
      }
    },
  },
};
</script>

<style scoped>
.editinformation {
  width: 900px;
  margin: auto;
  max-width: inherit;
}

h1 {
  margin-top: 40px;
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
</style>
