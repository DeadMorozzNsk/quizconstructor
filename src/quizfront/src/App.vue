<template>
  <div id="app">
    <admin-view v-if="isAdmin"></admin-view>
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
        id: 1,
        username: 'admin',
        roles: [
          {
            id: 3,
            name: 'ROLE_ADMIN'
          }
        ],
      },
      isAdmin: true,
      tabVisible: true,
    }
  },
  methods: {
    ...mapActions([
      'receiveAllQuiz',
      'getUser'
    ])
  },
  mounted: function () {
    this.receiveAllQuiz()
    this.getUser()
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
