<template>
  <div class="solve-table" v-if="visible" :style="'width:' + width">
    <el-table :data="tableData"
              style="width: 100%"
              :height="height"
              highlight-current-row
              current-row-key="index"
              :border="true">
      <el-table-column type="index" label="№" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="Название" width="250" header-align="center"></el-table-column>
      <el-table-column prop="user" label="Пользователь" width="150" header-align="center"></el-table-column>
      <el-table-column prop="author" label="Автор" width="120" header-align="center"></el-table-column>
      <el-table-column prop="length" label="Количество вопросов" width="110" align="center"></el-table-column>
      <el-table-column fixed="right" label="Действия" width="170" header-align="center">
        <template slot-scope="scope">
          <el-button @click.native.prevent="checkClick(scope.$index)" type="text" size="small">Проверить</el-button>
          <el-button @click.native.prevent="deleteClick(scope.$index)" type="text" size="small">Удалить</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "SolvedTable",
  props: {
    tableData: Array,
    visible: {
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
  methods: {
    checkClick(index) {
      console.log('check click!!! index = ' + index)
      this.$emit('check-click', index)
    },
    deleteClick(index) {
      console.log('delete click!!!index = ' + index)
      this.$emit('delete-click', index)
    }
  }
}
</script>

<style scoped>
.solve-table {
  width: 700px;
  margin: 0 auto;
}
</style>