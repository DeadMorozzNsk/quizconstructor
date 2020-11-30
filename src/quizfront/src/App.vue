<template>
  <div id="app">
    <admin-view v-if="user.isAdmin" :user-obj="user"></admin-view>
    <user-view :user-obj="user" v-else></user-view>
  </div>
</template>

<script>
import AdminView from "./components/AdminView";
import UserView from "./components/UserView";
import {mapActions} from 'vuex'

export default {
  name: 'App',
  components: {
    'admin-view': AdminView,
    'user-view': UserView
  },
  data() {
    return {
      user: {
        name: 'MegaUser',
        isAdmin: true
      },
      tabVisible: true,
    }
  },
  methods: {
    ...mapActions([
      'receiveAllQuiz'
    ])
  },
  mounted: function () {
    this.receiveAllQuiz();
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2f5b98;
}

body {
  height: 100%;
}
</style>
