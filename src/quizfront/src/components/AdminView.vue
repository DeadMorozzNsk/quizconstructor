<template>
  <div>
    <menu-bar @select-item="handleAdminMenuSelect"></menu-bar>
    <div class="quiz-table-container">
      <quiz-table
          :visible="tabVisible"
          :is-admin="true"
          height="500px"
          width="900px"
          @edit-click="editQuiz"
          @delete-click="deleteQuiz"
          @save-click="saveQuizCopy"
      ></quiz-table>
    </div>
    <div class="solve-table-container">
      <solve-table :tableData="solveData"
                   :visible="visible = !tabVisible"
                   height="500px"
                   width="900px"
                   @check-click="checkSolve"
                   @delete-click="deleteSolve"></solve-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="40%"
        :modal="true"
        center>
      <quiz-edit-area
          :quiz-obj="currentQuiz"
          :edit-mode="true"
          :is-new="isNew"
          @create-new-quiz="setCurrentQuizNew"
          @save-quiz="saveQuiz"
          @cancel-quiz="cancelQuiz"></quiz-edit-area>
    </el-dialog>
  </div>
</template>

<script>
import QuizTable from "../components/QuizTable";
import SolvedTable from "../components/SolvedTable";
import AdminMenuBar from "../components/AdminMenuBar";
import QuizEditArea from "../components/QuizEditArea";
import {mapState} from 'vuex'

export default {
  name: "AdminView",
  props: {
    // quizData: {
    //   type: Array,
    //   default: function () {
    //     return []
    //   }
    // },
    // userObj: {
    //   type: Object,
    //   default: function () {
    //     return {name: '%username%', isAdmin: false}
    //   }
    // }
  },
  components: {
    'quiz-table': QuizTable,
    'solve-table': SolvedTable,
    'menu-bar': AdminMenuBar,
    'quiz-edit-area': QuizEditArea
  },
  data() {
    return {
      dialogTitle: 'Редактирование анкеты',
      isNew: false,
      currentQuiz: undefined,
      dialogVisible: false,
      tabVisible: true,
      solveData: []
      //     [{
      //   date: '2016-05-03',
      //   name: 'test #1',
      //   user: 'user #1',
      //   author: 'author #1',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #2',
      //   user: 'user #2',
      //   author: 'author #2',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #3',
      //   user: 'user #3',
      //   author: 'author #3',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #4',
      //   user: 'user #4',
      //   author: 'author #4',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #1',
      //   user: 'user #1',
      //   author: 'author #1',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #2',
      //   user: 'user #2',
      //   author: 'author #2',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #3',
      //   user: 'user #3',
      //   author: 'author #3',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #4',
      //   user: 'user #4',
      //   author: 'author #4',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #1',
      //   user: 'user #1',
      //   author: 'author #1',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #2',
      //   user: 'user #2',
      //   author: 'author #2',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #3',
      //   user: 'user #3',
      //   author: 'author #3',
      //   length: 10,
      //   mistakes: 3
      // }, {
      //   date: '2016-05-03',
      //   name: 'test #4',
      //   user: 'user #4',
      //   author: 'author #4',
      //   length: 10,
      //   mistakes: 3
      // }]
    }
  },
  watch: {
    dialogVisible: function () {
      if (!this.dialogVisible) this.isNew = false;
    }
  },
  computed: {
    ...mapState([
      'quizList',
      'user'
    ])
  },
  methods: {
    handleAdminMenuSelect(key) {
      if (key === "1-1") {
        this.tabVisible = true;
      } else if (key === "1-2") {
        this.createNewQuiz();
      } else if (key === "2") {
        this.tabVisible = false;
      } else if (key === "3") {
        this.logout()
      }
    },
    logout() {
      console.log('logout click!!!')
    },
    async editQuiz(arrayIndex) {
      this.dialogTitle = 'Редактирование анкеты';
      /* создаем копию объекта, чтобы не менять state существующего объекта */
      this.currentQuiz = JSON.parse(JSON.stringify(this.quizList[arrayIndex]))
      const questions = this.currentQuiz.questions
      /* Добавляем объектам вопросов и ответов внешний ключ для удобства идентификации */
      for (let i = 0; i < questions.length; i++) {
        Object.keys(questions[i]).push('foreignKey')
        for (let j = 0; j < questions[i].answers.length; j++) {
          Object.keys(questions[i].answers[j]).push('foreignKey')
        }
      }
      this.dialogVisible = true;
    },
    deleteQuiz(index) {
      if (confirm('Удалить эту анкету?')) this.quizData.splice(index, 1);
    },
    checkSolve(index) {
      console.log('check solve!index = ' + index)
      this.solveData.splice(index, 1);
    },
    deleteSolve(index) {
      if (confirm('Удалить это решение?')) this.solveData.splice(index, 1);
    },
    saveQuizCopy(index) {
      let newQuiz = this.quizData[index];
      console.log(newQuiz)
      newQuiz.id = undefined;
      this.saveQuiz(newQuiz)
    },
    saveQuiz() {
      console.log('save & quit quiz edit')
      this.dialogVisible = false;
      // this.$api.quiz.setQuiz(quiz).then(result => {
      //   if (result.status === 200 || result.status === 201) {
      //     console.log(result.data);
      //     this.quizData = result.data;
      //   } else {
      //     this.errors = result;
      //     console.error(result);
      //   }
      // })
    },
    cancelQuiz() {
      this.dialogVisible = false;
    },
    setCurrentQuizNew() {
      this.currentQuiz = {
        name: 'Новая анкета',
        author: this.user,
        questions: []
      }
    },
    createNewQuiz() {
      this.isNew = true;
      this.dialogTitle = 'Создание новой анкеты';
      this.setCurrentQuizNew();
      this.dialogVisible = true;
    }
  }
}
</script>

<style scoped>

</style>