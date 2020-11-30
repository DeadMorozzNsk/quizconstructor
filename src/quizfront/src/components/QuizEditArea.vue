<template :model="quizObj">
  <div class="quiz-area">
    <el-form class="quiz-edit-form" ref="form" label-width="150px">
      <h1>{{ quizObj.name }}</h1>
      <div class="params-block">
        <el-form-item label="Название">
          <el-input v-model="quizObj.name" v-if="editMode"></el-input>
          <label v-else>{{ quizObj.name }}</label>
        </el-form-item>
        <el-form-item label="Автор" v-if="!editMode">
          <label>{{ quizObj.author.username }}</label>
        </el-form-item>

      </div>
      <div class="questions-container">
        <quiz-question v-for="(question, index) in quizObj.questions"
                       :key="index"
                       :question-obj="question"
                       :editable="editMode"
                       :view-only-mode="viewOnlyMode"
                       :index="question_fk"
                       @delete-question="deleteQuestion"
                       @move-up-question="moveUpQuestion"
                       @move-down-question="moveDownQuestion"/>
      </div>
      <div v-if="editMode" class="add-question-panel">
        <el-button type="primary"
                   icon="el-icon-plus"
                   title="Добавить вопрос"
                   style="display: block; margin: 0 auto"
                   @click="addQuestion">Добавить вопрос
        </el-button>
      </div>
      <div class="form-buttons-block" v-if="editMode">
        <div v-if="!isNew" style="display: table; margin: 0 auto">
          <el-button type="primary"
                     @click="saveQuiz">Сохранить
          </el-button>
          <el-button @click="cancelQuiz">Отмена</el-button>
        </div>
        <el-button v-else type="primary"
                   @click="saveQuiz">Создать
        </el-button>
      </div>
      <div class="form-buttons-block" v-else>
        <el-row>
          <el-col :span="20">
            <el-button type="primary"
                       @click="answerQuiz">Завершить
            </el-button>
            <el-button @click="cancelQuiz">Отмена</el-button>
          </el-col>
          <el-col :span="4">
            <el-button @click="saveSolveProgress">Сохранить</el-button>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
import Question from "../components/Question";
import {mapActions} from 'vuex'

export default {
  name: "QuizEditArea",
  props: {
    editMode: {
      type: Boolean,
      default: false
    },
    quizObj: {
      type: Object
    },
    viewOnlyMode: {
      type: Boolean,
      default: false
    },
    isNew: {
      type: Boolean,
      default: false
    }
  },
  components: {
    'quiz-question': Question
  },
  data() {
    return {
      question_fk: 0,
      localQuiz: {
        name: 'Новая анкета',
        author: 'SomeAdmin',
        questions: []
      }
    }
  },
  created: function () {
    if (this.quizObj === undefined) {
      this.$emit('create-new-quiz')
    }
  },
  methods: {
    ...mapActions([
      'updateQuizAction',
      'addQuestionAction'
    ]),
    addQuestion() {
      // console.log('quiz id=' + this.quizObj.id)
      //this.addQuestionAction(this.quizObj.id)
      const newQuestion = {
        text: 'Новый вопрос',
        quiz: this.quizObj.id,
        position: this.quizObj.questions.length,
        questionType: 'MULTIPLE',
        answers: [],
        foreignKey: 0 /* внешний ключ для удобства идентификации */
      }
      this.question_fk++
      this.quizObj.questions.push(newQuestion)
    },
    deleteQuestion(questionId) {
      const deleteIndex = this.getQuestionArrayIndex(questionId)
      if (deleteIndex > -1) {
        this.quizObj.questions.splice(deleteIndex, 1,)
        this.refreshPositions()
      } else
        console.error('error while deleting question, wrong index.')
    },
    moveUpQuestion(questionId) {
      const questions = this.quizObj.questions
      const index = this.getQuestionArrayIndex(questionId)
      if (index > 0 && index < questions.length) {
        const temp = questions[index - 1]
        questions[index - 1] = questions[index]
        questions[index] = temp
        this.refreshPositions()
      }
    },
    moveDownQuestion(questionId) {
      const questions = this.quizObj.questions
      const index = this.getQuestionArrayIndex(questionId)
      if (index >= 0 && index < questions.length - 1) {
        const temp = questions[index + 1]
        questions[index + 1] = questions[index]
        questions[index] = temp
        this.refreshPositions()
      }
    },
    getQuestionArrayIndex(questionId) {
      return this.quizObj.questions.findIndex(item => item.foreignKey === questionId)
    },
    refreshPositions() {
      const questions = this.quizObj.questions
      for (let i = 0; i < questions.length; i++) {
        questions[i].position = i
      }
    },
    saveQuiz(quiz) {
      this.updateQuizAction(this.quizObj)
      this.$emit('save-quiz', quiz)
    },
    saveSolveProgress(quiz) {
      this.$emit('save-progress', quiz)
    },
    answerQuiz(quiz) {
      this.$emit('end-quiz', quiz);
    },
    cancelQuiz() {
      let dialogVisibility = !confirm('При отмене весь несохраненный прогресс будет утерян. Отменить?');
      if (!dialogVisibility) this.$emit('cancel-quiz', dialogVisibility)
    }
  }
}
</script>

<style scoped>
.quiz-edit-form {
  line-height: 20px;
}

.add-question-panel {
  border: 1px solid #d0d0d0;
  border-radius: 5px;
  margin-top: 10px;
  padding: 10px;
}

.params-block {
  border: 1px solid #d0d0d0;
  border-radius: 5px;
  margin-top: 10px;
  padding: 10px;
}

.form-buttons-block {
  border: 1px solid #d0d0d0;
  border-radius: 5px;
  margin-top: 10px;
  padding: 10px;
}

.quiz-area {
  max-width: 700px;
  margin: 0 auto;
}
</style>