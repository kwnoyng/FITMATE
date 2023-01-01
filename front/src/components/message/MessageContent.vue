<template>
  <div @click="showModal = true" class="message-content">
    <div class="message-content-left">
      <span
        ><svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-envelope-fill"
          viewBox="0 0 16 16"
        >
          <path
            d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"
          /></svg
      ></span>
      <span class="displayed-msg">{{ displayedMsg }}</span>
    </div>
    <span>&nbsp;</span>
    <span v-if="type === 'received'"
      >From {{ getKeyByValue(nicknames, msg.from_user) }}</span
    >
    <span v-else>To {{ getKeyByValue(nicknames, msg.to_user) }}</span>
    <message-modal v-if="showModal" @close="showModal = false">
      <h3 v-if="type === 'received'" slot="header">
        From {{ getKeyByValue(nicknames, msg.from_user) }}
      </h3>
      <h3 v-else slot="header">
        To {{ getKeyByValue(nicknames, msg.to_user) }}
      </h3>
      <p class="message-content-body" slot="body">{{ msg.content }}</p>

      <div slot="footer" class="profileAndMessage">
        <a
          @click.stop="goToProfile"
          id="toprofile"
          class="btn btn-secondary go-to-profile"
          >Profile</a
        >
        <router-link
          v-if="type === 'received'"
          :to="{ name: 'MessageSend', params: { id: msg.from_user } }"
          class="send-message"
        >
          Send Message
        </router-link>
        <router-link
          v-else
          :to="{ name: 'MessageSend', params: { id: msg.to_user } }"
          class="send-message"
        >
          Send Message
        </router-link>
      </div>
    </message-modal>
  </div>
</template>

<script>
import MessageModal from "./MessageModal.vue";
const MAX_DISPLAY_LENGTH = 30;

export default {
  name: "MessageContent",
  components: {
    MessageModal,
  },
  props: {
    msg: Object,
    type: String,
  },
  data() {
    return {
      showModal: false,
    };
  },
  computed: {
    displayedMsg() {
      let dispMsg = this.msg.content;
      const len = dispMsg.length;
      if (len > MAX_DISPLAY_LENGTH) {
        dispMsg = dispMsg.slice(0, MAX_DISPLAY_LENGTH) + "...";
      }
      return dispMsg;
    },
    nicknames() {
      return this.$store.state.nicknames;
    },
  },
  methods: {
    getKeyByValue(object, value) {
      const res = Object.keys(object).find((key) => object[key] === value);
      return res;
    },
    goToProfile() {
      if (this.type === "received") {
        this.$router.push({
          name: "UserViewMain",
          params: {
            id: this.msg.from_user,
          },
        });
      } else {
        this.$router.push({
          name: "UserViewMain",
          params: {
            id: this.msg.to_user,
          },
        });
      }
    },
  },
};
</script>

<style scoped>
.modalheader {
  color: black !important;
}

/* #toprofile {
  color: black;
} */

.modal {
  color: black;
}

svg {
  margin-right: 10px;
}
.message-content {
  display: flex;
  border: 1px;
  padding: 12px;
  cursor: pointer;
  align-items: center;
  justify-content: space-between;
}

.message-content-left {
  display: flex;
  align-items: center;
  justify-content: center;
}

.displayed-msg {
  margin-left: 50px;
}

.profileAndMessage {
  margin-top: 20px;
}
</style>
