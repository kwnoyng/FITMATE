<template>
  <div>
    <h1>Send Message</h1>
    <div class="autosuggest-container">
      <h2 class="to">To</h2>
      <vue-autosuggest
        id="search-user"
        v-model="query"
        :suggestions="filteredOptions"
        @click="clickHandler"
        @input="onInputChange"
        @selected="onSelected"
        :get-suggestion-value="getSuggestionValue"
        :input-props="{
          id: 'autosuggest__input',
          placeholder: '닉네임을 검색하세요',
        }"
        :limit="10"
      >
        <div
          slot-scope="{ suggestion }"
          style="display: flex; align-items: center"
        >
          <div style="{ display: 'flex', color: 'navyblue'}">
            {{ suggestion.item }}
          </div>
        </div>
      </vue-autosuggest>
    </div>
    <div class="message-content-container">
      <!-- <label for="msg-content">Content</label> -->
      <h2 class="contentext">Content</h2>
      <textarea
        v-model="message.content"
        id="msg-content"
        cols="40"
        rows="10"
      ></textarea>
    </div>
    <button class="sendbtn" @click="sendMessage">SEND</button>
  </div>
</template>

<script>
import { VueAutosuggest } from "vue-autosuggest";
import axios from "@/util/http-common.js";

export default {
  name: "MessageSend",
  components: {
    VueAutosuggest,
  },

  data() {
    return {
      message: {
        from_user: "",
        to_user: "",
        content: "",
      },
      query: "",
      selected: "",
      suggestions: [
        {
          data: [],
        },
      ],
    };
  },
  computed: {
    filteredOptions() {
      if (!this.query) return [{ data: [] }];
      return [
        {
          data: this.suggestions[0].data
            .filter((option) => {
              return (
                option.toLowerCase().indexOf(this.query.toLowerCase()) > -1
              );
            })
            .slice(0, this.limit),
        },
      ];
    },
    nicknames() {
      return this.$store.state.nicknames;
    },
  },
  methods: {
    clickHandler(item) {
      console.log(item);
    },
    onSelected(item = null) {
      console.log(item);

      if (!item) {
        this.selected = this.query;
      } else {
        this.selected = item.item;
      }
      this.message.to_user = this.nicknames[this.selected];
      console.log(this.nicknames);
      console.log(this.message.to_user);
    },
    onInputChange(text) {
      console.log(text);
    },
    getSuggestionValue(suggestion) {
      return suggestion.item;
    },
    sendMessage() {
      if (!this.message.to_user) {
        alert("등록된 유저가 아닙니다.");
        return;
      }

      this.message.from_user = this.$store.state.myUser.u_id;
      console.log(this.message);
      axios({
        url: "messageApi/",
        method: "POST",
        data: null,
        params: this.message,
      }).then((res) => {
        console.log(res.data);
        // this.$store.commit("SET_SENT_MSGS", res.data);
        this.$router.push({ name: "MessageSent" });
      });
    },
  },
  created() {
    const id = this.$route.params.id;
    console.log(id);
    this.suggestions[0].data = Object.keys(this.nicknames);

    if (id == 0) return;
    axios.get(`userApi/detail`, { params: { id: id } }).then((res) => {
      this.query = res.data.nickname;
      this.selected = res.data.nickname;
      this.message.to_user = this.nicknames[this.selected];
    });
  },
};
</script>

<style>
h1 {
  font-weight: 400;
  margin-top: 20px;
}

.to {
  padding-top: 12px;
}

.sendbtn {
  margin-top: 20px;
  width: 5rem;
}

.contentext {
  margin: 15px 0 10px 0;
}
.autosuggest-container {
  position: relative;
}

.autosuggest-container input {
  width: 260px;
  padding: 0.5rem;
  font-weight: 600;
}

.autosuggest-container input::-webkit-input-placeholder {
  font-weight: 400;
  color: #afb2b3;
}
.autosuggest-container input::-moz-placeholder {
  font-weight: 400;
  color: #afb2b3;
}
.autosuggest-container input::-ms-input-placeholder {
  font-weight: 400;
  color: #afb2b3;
}

.autosuggest-container ul {
  position: absolute;
  width: 93%;
  color: rgba(30, 39, 46, 1);
  list-style: none;
  margin: 0;
  padding: 0.5rem 0 0.5rem 0;
  border: 2px solid rgba(255, 255, 255, 0.35);
  border-radius: 0 0 9px 9px;
  color: white;
  backdrop-filter: blur(10px);
  font-weight: 600;
}

.autosuggest-container li {
  margin: 0 0 0 0;
  border-radius: 5px;
  padding: 0.75rem 0 0.75rem 0.75rem;
  display: flex;
  align-items: center;
}

.autosuggest-container li:hover {
  cursor: pointer;
}

.autosuggest-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  width: 280px;
  margin: 0 auto;
}

#autosuggest {
  width: 100%;
  display: block;
}

.autosuggest__results-item--highlighted {
  background-color: rgba(51, 217, 178, 0.2);
}

.message-content-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#msg-content {
  width: 98%;
  resize: none;
}
</style>
