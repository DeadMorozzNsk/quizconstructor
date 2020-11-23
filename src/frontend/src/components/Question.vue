<template>
  <div class="question-card">
    <el-row>
      <el-col :span="24" v-model="questionObj">
        <div v-if="editable" class="command-panel">
          <div style="display: table; margin: 0 auto">
            <el-button type="primary" icon="el-icon-arrow-up" title="передвинуть вверх"/>
            <el-button type="primary" icon="el-icon-arrow-down" title="передвинуть вниз"/>
            <el-button type="danger" icon="el-icon-delete" title="Удалить вопрос"/>
          </div>
        </div>
        <div class="q-label">
          <el-row>
            <el-col :span="23">
              <div>
                <label>Вопрос:</label>
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
        <div class="q-type" >
          <label v-if="editable">Количество ответов:
            <el-select v-model="questionObj.type"
                     placeholder="Выберите">
            <el-option :value=0 label="Один вариант"></el-option>
            <el-option :value=1 label="Несколько вариантов"></el-option>
          </el-select>
          </label>
          <label v-else>Количество ответов: {{questionObj.questionType}}</label>
        </div>
        <quiz-answers v-for="answer in questionObj.answers"
                      :answer-obj="answer" :key="answer.id"
                      :question-type="questionObj.type"
                      :editable="editable"
                      :radio-item-id="radioItemId"
                      :view-only-mode="viewOnlyMode"
                      @checkbox-click="editCheckBoxList"
                      @radio-click="setItemId"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Answer from "@/components/Answer";

export default {
  name: "Question",
  props: {
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
      question: {
        text: String,
        answers: []
      }
    }
  },
  created() {
    this.question = this.questionObj;
  },
  methods: {
    toggleInput(event) {
      if (this.isMouseOverButton && event.type === 'click' ||
          !this.isMouseOverButton && event.type === 'blur') {
        this.questionEditMode = !this.questionEditMode;
        if (this.$refs.questionInput === undefined && this.questionEditMode) {
          this.$nextTick(() => {
            this.$refs.questionInput.focus()
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

.question-text {
  word-wrap: break-word
}
</style>