<template>
  <div class="question-card">
    <el-row>
      <el-col :span="24" v-model="questionObj">
        <div v-if="editable" class="command-panel">
          <div style="display: table; margin: 0 auto">
            <el-button type="primary" icon="el-icon-arrow-up" title="передвинуть вверх" @click="moveUp"/>
            <el-button type="primary" icon="el-icon-arrow-down" title="передвинуть вниз" @click="moveDown"/>
            <el-button type="danger" icon="el-icon-delete" title="Удалить вопрос" @click="deleteQuestion"/>
          </div>
        </div>
        <div class="q-label">
          <el-row>
            <el-col :span="23">
              <div>
                <label>Вопрос №{{ questionObj.position + 1 }}:</label>
              </div>
            </el-col>
            <el-col :span="1" v-if="editable && (!viewOnlyMode)">
              <i class="el-icon-edit"
                 @mouseenter="changeMouseOverStatus"
                 @mouseleave="changeMouseOverStatus"
                 @click="toggleInput($event)"></i>
            </el-col>
          </el-row>
          <div v-if="questionEditMode">
            <el-input ref="questionInput"
                      type="textarea" :rows="3"
                      v-model="questionObj.text"
                      @blur="toggleInput($event)">
            </el-input>
          </div>
          <div v-else class="question-text">
            {{ questionObj.text }}
          </div>
        </div>
        <div class="q-type">
          <label v-if="editable">Количество ответов:
            <el-select v-model="questionObj.questionType"
                       placeholder="Выберите">
              <el-option value="SINGLE" label="Один вариант"></el-option>
              <el-option value="MULTIPLE" label="Несколько вариантов"></el-option>
            </el-select>
          </label>
          <label v-else>Количество ответов: {{ questionObj.questionType }}</label>
        </div>
        <div class="command-panel answers-buttons">
          <div style="display: table; margin: 0 auto">
            <el-button type="primary"
                       icon="el-icon-plus"
                       title="Добавить ответ"
                       @click="addAnswer">Добавить ответ
            </el-button>
          </div>
        </div>
        <div class="answers-container">
          <quiz-answers v-for="(answer, index) in questionObj.answers"
                        :answer-obj="answer" :key="index"
                        :question-type="questionObj.questionType"
                        :editable="editable"
                        :radio-item-id="radioItemId"
                        :view-only-mode="viewOnlyMode"
                        :index="answer_fk"
                        @checkbox-click="editCheckBoxList"
                        @radio-click="setItemId"
                        @delete-answer="deleteAnswer"/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Answer from "../components/Answer";

export default {
  name: "Question",
  props: {
    index: {
      type: Number,
      default: 0
    },
    editable: {
      type: Boolean,
      default: false
    },
    questionObj: {
      type: Object,
      default: function () {
        return undefined
      }
    },
    viewOnlyMode: {
      type: Boolean,
      default: false
    }
  },
  components: {
    'quiz-answers': Answer
  },
  data: function () {
    return {
      isMouseOverButton: false,
      questionEditMode: false,
      radioItemId: 0,
      checkBoxList: [],
      answer_fk: 0
    }
  },
  mounted: function () {
    this.questionObj.foreignKey = this.index
  },
  methods: {
    toggleInput(event) {
      if (this.isMouseOverButton && event.type === 'click' ||
          !this.isMouseOverButton && event.type === 'blur') {
        this.questionEditMode = !this.questionEditMode;
        if (this.$refs.questionInput === undefined && this.questionEditMode) {
          this.$nextTick(() => {
            this.$refs.questionInput.focus()
            this.$refs.questionInput.select()
          })
        }
      }
    },
    changeMouseOverStatus() {
      this.isMouseOverButton = !this.isMouseOverButton;
    },
    editCheckBoxList(cbId, checked) {
      if (checked) {
        this.checkBoxList.push(cbId)
      } else {
        let index = this.checkBoxList.indexOf(cbId, 0);
        if (index >= 0) this.checkBoxList.splice(index, 1)
        else console.log('item id not found in answers array!!!')
      }
    },
    setItemId(itemId) {
      this.radioItemId = itemId
    },
    deleteQuestion() {
      this.$emit('delete-question', this.questionObj.foreignKey)
    },
    addAnswer() {
      const newAnswer = {
        correct: false,
        isCorrect: false,
        question: this.questionObj.id,
        text: "Новый ответ",
        foreignKey: 0
      }
      this.answer_fk++
      this.questionObj.answers.push(newAnswer)
    },
    deleteAnswer(answerId) {
      const answers = this.questionObj.answers
      const deleteIndex = answers.findIndex(item => item.foreignKey === answerId)
      if (deleteIndex > -1 && deleteIndex < answers.length) {
        answers.splice(deleteIndex, 1)
      } else
        console.error('error while deleting answer, wrong index.')
    },
    moveUp() {
      const previous = this.$el.previousSibling
      if (previous !== null)
        this.$el.parentNode.insertBefore(this.$el, previous);
      this.$emit('move-up-question', this.questionObj.foreignKey)
    },
    moveDown() {
      const next = this.$el.nextSibling
      if (next !== null)
        this.$el.parentNode.insertBefore(next, this.$el);
      this.$emit('move-down-question', this.questionObj.foreignKey)
    }
  }
}
</script>

<style scoped>
.q-label {
  border-radius: 5px;
  border: 1px solid #89a0b8;
  text-align: left;
  padding: 10px;
}

.q-type {
  margin-top: 10px;
}

.question-card {
  border-radius: 5px;
  border: 1px solid #d0d0d0;
  padding: 10px;
  margin-top: 5px;
}

.command-panel {
  border-radius: 5px;
  border: 1px solid #d0d0d0;
  padding: 10px;
  margin-bottom: 5px;
}

.answers-buttons {
  margin-top: 10px;
}

.question-text {
  word-wrap: break-word
}
</style>