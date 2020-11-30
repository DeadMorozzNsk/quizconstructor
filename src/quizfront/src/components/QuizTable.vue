<template>
  <div class="quiz-tab" v-if="visible" :style="'width:' + width">
    <el-table :data="quizList" style="width: 100%" :height="height" :border="true">
      <el-table-column prop="date" label="Дата создания" width="170" header-align="center"></el-table-column>
      <el-table-column prop="name" label="Название" width="250" header-align="center"></el-table-column>
      <el-table-column prop="authorName" label="Автор" width="150" header-align="center"></el-table-column>
      <el-table-column prop="questionsCount" label="Количество вопросов" width="110" align="center"></el-table-column>
      <el-table-column fixed="right" label="Действия" width="170" header-align="center">
        <template slot-scope="scope">
          <div v-if="isAdmin">
            <el-button @click="editClick(scope.$index)" type="text" size="small">Редактировать</el-button>
            <el-button @click="deleteClick(scope.$index)" type="text" size="small">Удалить</el-button>
            <el-button @click="saveClick(scope.$index)" type="text" size="small">Копировать</el-button>
          </div>
          <el-button v-else @click="answerClick(scope.$index)" type="text" size="small">Ответить</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {mapActions} from 'vuex'

export default {
  name: "QuizTable",
  props: {
    // tableData: {
    //   type: Array,
    //   default: function () {
    //    return []
    //   }
    // },
    visible: {
      type: Boolean,
      default: false
    },
    isAdmin: {
      type: Boolean,
      default: false
    },
    height: {
      type: String,
      default: "350px"
    },
    width: {
      type: String,
      default: "700px"
    }
  },
  computed: {
    ...mapState([
      'quizList'
    ])
  },
  methods: {
    ...mapActions([
      'downloadQuizData'
    ]),
    editClick: async function (index) {
      if (this.quizList[index].questions === undefined)
        await this.downloadQuizData(this.quizList[index].id);
      this.$emit('edit-click', index)
    },
    deleteClick: function (index) {
      console.log('delete click!!!')
      this.$emit('delete-click', index)
    },
    answerClick(index) {
      this.$emit('answer-click', index)
    },
    saveClick(index) {
      this.$emit('save-click', index)
    }
  }
}
</script>

<style scoped>
.quiz-tab {
  margin: 0 auto;
  border: 1px solid #d0d0d0;
  border-radius: 5px;
}
</style>