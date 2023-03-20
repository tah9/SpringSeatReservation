<template>

  <van-popup v-model="show" @close="clickCancel" v-if="rows" position="bottom" :style="{ height: '40%' }" round>
    <van-picker
        title="选择区域"
        show-toolbar :default-index="rows.length/2"
        :columns="rows"
        @cancel="clickCancel"
        @confirm="clickSubmit"
    />
  </van-popup>
</template>

<script>
export default {
  name:'ChooseArea',
  data() {
    return {
      show: true
    }
  },
  props: {
    rows: {
      type: Array
    }
  },
  methods: {
    clickSubmit(value, index) {
      this.$emit('confirm', index)
    }, clickCancel() {
      this.$emit('cancel')
    }
  },
}
</script>

<style scoped>

</style>
