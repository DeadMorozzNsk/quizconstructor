<template :answerObj="answerObj"
          :questionType="questionType"
          :editable="editable"
          :radioItemId="radioItemId">
  <div>
    <!-- Если тип ответа - только один вариант -->
    <div v-if="questionType === 'SINGLE'">
      <div class="a-label" @click.prevent="divRadioClick">
        <el-row>
          <el-col :span="22">
            <el-input ref="answerInput" v-model="answerObj.text"
                      v-if="answerEditMode"
                      @blur="toggleInput($event)">
            </el-input>
            <el-radio v-else v-model="localItemId"
                      :label="answerObj.id"
                      :disabled="viewOnlyMode">
              {{ answerObj.text }}
            </el-radio>
          </el-col>
          <el-col :span="2" v-if="editable && (!viewOnlyMode)">
            <i class="el-icon-edit"
               @mouseenter="changeMouseOverStatus"
               @mouseleave="changeMouseOverStatus"
               @click="toggleInput($event)"/>
            <i class="el-icon-delete"
               @click="deleteItem" style="margin-left: 10px"/>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- Если тип ответа - несколько вариантов -->
    <div v-else-if="questionType === 'MULTIPLE'">
      <div class="a-label" @click.prevent="divCheckboxClick">
        <el-row>
          <el-col :span="22">
            <el-input ref="answerInput" v-model="answerObj.text"
                      v-if="answerEditMode"
                      @blur="toggleInput($event)">
            </el-input>
            <el-checkbox v-else v-model="answerChecked"
                         :label="answerObj.id"
                         :disabled="viewOnlyMode">
              {{ answerObj.text }}
            </el-checkbox>
          </el-col>
          <el-col :span="2" v-if="editable && (!viewOnlyMode)">
            <i class="el-icon-edit"
               @mouseenter="changeMouseOverStatus"
               @mouseleave="changeMouseOverStatus"
               @click="toggleInput($event)"/>
            <i class="el-icon-delete"
               @click="deleteItem" style="margin-left: 10px"/>
          </el-col>
        </el-row>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Answer",
  props: {
    viewOnlyMode: {
      type: Boolean,
      default: false
    },
    answerObj: Object,
    questionType: {
      type: String,
      default: 'SINGLE'
    },
    editable: {
      type: Boolean,
      default: false
    },
    radioItemId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      localItemId: 0,
      answerChecked: false,
      answerEditMode: false,
      isMouseOverButton: false
    }
  },
  watch: {
    radioItemId: function () {
      this.localItemId = this.radioItemId
    },
    localItemId: function () {
      this.$emit('radio-click', this.localItemId)
    },
    answerChecked: function () {
      this.sendCheckBoxClick(this.answerObj.id)
    }
  },
  methods: {
    divRadioClick() {
      if (!this.answerEditMode) {
        if (!this.viewOnlyMode)
          this.localItemId = this.answerObj.id
      }
    },
    divCheckboxClick() {
      if (!this.viewOnlyMode)
        this.answerChecked = !this.answerChecked
    },
    deleteItem() {
      // console.log(this)
      this.$emit('delete-answer', this.answerObj.foreignKey)
    },
    toggleInput(event) {
      if (this.isMouseOverButton && event.type === 'click' ||
          !this.isMouseOverButton && event.type === 'blur') {
        this.answerEditMode = !this.answerEditMode;
        if (this.$refs.answerInput === undefined && this.answerEditMode) {
          this.$nextTick(() => {
            this.$refs.answerInput.focus()
            this.$refs.answerInput.select()
          })
        }
      }
    },
    changeMouseOverStatus() {
      this.isMouseOverButton = !this.isMouseOverButton;
    },
    sendCheckBoxClick(cb_id = {type: Number, default: 0}) {
      let checked = this.answerChecked;
      this.$emit('checkbox-click', cb_id, checked);
    }
  }
}
</script>

<style scoped>
.a-label {
  text-align: left;
  border-radius: 5px;
  border: 1px solid #eaeaea;
  margin-top: 10px;
  padding: 10px;
  word-wrap: break-word;
  cursor: pointer;
}

.a-label:hover {
  background: #e4e5f8;
}
</style>