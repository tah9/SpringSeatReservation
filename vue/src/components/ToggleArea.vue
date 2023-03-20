<template>
  <el-select v-if="areaRows" size="mini" v-model="areaName" @change="changeArea">
    <el-option
        v-for="item in areaRows"
        :label="item.areaName"
        :value="item.aid">
    </el-option>
  </el-select>
</template>

<script>
export default {
  name: "ToggleArea",
  props: {
    areaRows: {
      type: Array
    }
  },
  data() {
    return {
      areaName: null,
      curIndex: 0
    }
  },
  methods: {
    getArea() {
      return this.areaRows[this.curIndex]
    },
    changeArea(index) {
      this.curIndex = index - 1
      this.$emit('changeArea', this.areaRows[index - 1])
    },
  },
  created() {
    this.areaName = this.areaRows[0].areaName
  }
}
</script>

<style scoped>

</style>
