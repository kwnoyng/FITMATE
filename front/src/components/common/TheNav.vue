<template>
  <nav id="nav">
    <router-link :to="{ name: 'VideoSearchView' }">VIDEO</router-link>
    <router-link :to="{ name: 'MateView' }">MATE</router-link>
    <router-link
      :to="{
        name: 'UserViewMain',
        params: {
          id: myUser.u_id ? myUser.u_id : 0,
        },
      }"
      >PROFILE</router-link
    >
  </nav>
</template>

<script>
export default {
  name: "TheNav",
  computed: {
    myUser() {
      return this.$store.state.myUser;
    },
  },
};
</script>

<style scoped>
nav {
  /* z-index: 9999; */
  display: flex;
  justify-content: space-around;
}
</style>
