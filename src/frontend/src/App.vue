<template>
  <div id="app">
    <admin-view :quiz-data="quizData" v-if="user.isAdmin" :user-obj="user"></admin-view>
    <user-view :quiz-data="quizData" :user-obj="user" v-else></user-view>
  </div>
</template>

<script>
import AdminView from "./components/AdminView";
import UserView from "./components/UserView";

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
      quizData: [
        //     {
        //   date: '2016-05-03',
        //   name: 'test #1',
        //   author: 'author #1',
        //   length: '10',
        //   isTimeLimited: false,
        //   timeLimit: 'неограничено',
        //   questions: [
        //     {
        //       id: 0,
        //       qText: 'быть или не быть?',
        //       type: 0,
        //       answers: [{
        //         id: 0,
        //         questionId: 0,
        //         aText: 'быть',
        //         correct: true
        //       }, {
        //         id: 1,
        //         questionId: 0,
        //         aText: 'не быть',
        //         correct: false
        //       }, {
        //         id: 2,
        //         questionId: 0,
        //         aText: 'где я?',
        //         correct: false
        //       }
        //       ]
        //     }, {
        //       id: 1,
        //       qText: 'Кто там?',
        //       type: 1,
        //       answers: [{
        //         id: 3,
        //         questionId: 1,
        //         aText: 'почтальон печкин',
        //         correct: true
        //       }, {
        //         id: 4,
        //         questionId: 1,
        //         aText: 'конь в пальто',
        //         correct: false
        //       }
        //       ]
        //     }
        //   ]
        // }, {
        //   date: '2016-05-02',
        //   name: 'test #2',
        //   author: 'author #2',
        //   length: '4',
        //   questions: [{
        //     id: 0,
        //     qText: 'быть или не быть?',
        //     type: 0,
        //     answers: [{
        //       id: 0,
        //       questionId: 0,
        //       aText: 'быть',
        //       correct: true
        //     }, {
        //       id: 1,
        //       questionId: 0,
        //       aText: 'не быть',
        //       correct: false
        //     }, {
        //       id: 2,
        //       questionId: 0,
        //       aText: 'где я?',
        //       correct: false
        //     }
        //     ]
        //   }, {
        //     id: 1,
        //     qText: 'Кто там?',
        //     type: 1,
        //     answers: [{
        //       id: 3,
        //       questionId: 1,
        //       aText: 'почтальон печкин',
        //       correct: true
        //     }, {
        //       id: 4,
        //       questionId: 1,
        //       aText: 'конь в пальто',
        //       correct: false
        //     }
        //     ]
        //   }]
        // }, {
        //   date: '2016-05-04',
        //   name: 'test #3',
        //   author: 'author #3',
        //   length: '6',
        //   questions: [{
        //     id: 0,
        //     qText: 'быть или не быть?',
        //     type: 0,
        //     answers: [{
        //       id: 0,
        //       questionId: 0,
        //       aText: 'быть',
        //       correct: true
        //     }, {
        //       id: 1,
        //       questionId: 0,
        //       aText: 'не быть',
        //       correct: false
        //     }, {
        //       id: 2,
        //       questionId: 0,
        //       aText: 'где я?',
        //       correct: false
        //     }
        //     ]
        //   }, {
        //     id: 1,
        //     qText: 'Кто там?',
        //     type: 1,
        //     answers: [{
        //       id: 3,
        //       questionId: 1,
        //       aText: 'почтальон печкин',
        //       correct: true
        //     }, {
        //       id: 4,
        //       questionId: 1,
        //       aText: 'конь в пальто',
        //       correct: false
        //     }
        //     ]
        //   }]
        // }, {
        //   date: '2016-05-01',
        //   name: 'test #4',
        //   author: 'author #4',
        //   length: '9',
        //   questions: []
        // }, {
        //   date: '2016-05-02',
        //   name: 'test #2',
        //   author: 'author #2',
        //   length: '4',
        //   questions: []
        // }, {
        //   date: '2016-05-04',
        //   name: 'test #3',
        //   author: 'author #3',
        //   length: '6',
        //   questions: []
        // }
      ]
    }
  },
  methods: {
    getQuizes() {

    }
  },
  mounted: function () {

    this.$api.quiz.getAllQuiz().then(result => {
      if (result.status === 200 || result.status === 201) {
        console.log(result.data);
        this.quizData = result.data;
      } else {
        this.errors = result;
        console.error(result);
      }
    });

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
