<template>
  <div class="navbar" @click="$emit('click')">
    <div class="left" @click="leftClick" :style="left?'':'display:none'">
      <van-icon name="arrow-left" size="1.2em"/>
      <!--<span>返回</span>-->
    </div>
    <div class="left">
      <slot name="left"></slot>
    </div>
    <div class="center">{{ title }}</div>
    <div class="right">
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "navbar",
  data() {
    return {};
  },
  props: {
    left: {
      default: true
    },
    title: ''
  },
  components: {},
  methods: {
    leftClick() {
      this.$router.go(-1)
    }
  }
};
</script>

<style scoped>

.navbar {
  position: sticky;
  background: #ffffff;
  color: black;
  z-index: 100;
  top: 0;
  height: 46px;
  line-height: 46px;
  width: 100%;
  box-sizing: border-box;
  padding: 16px;
}

.right {
  right: 16px;
  top: 0;
  bottom: 0;
  position: absolute;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.center {
  font-size: 16px;
  top: 0;
  bottom: 0;
  position: absolute;
  left: 0;
  right: 0;
  font-weight: bold;
  text-align: center;
}

.left {
  left: 16px;
  top: 0;
  bottom: 0;
  z-index: 101;
  position: absolute;
  display: flex;
  align-items: center;
}

img {
  width: 1.3em;
  height: 1.3em;
}
</style>
