<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>

export default {
  data() {
    return {}
  }
}
</script>
<style>
body {
  margin: 0;
  padding: 0;
  overflow-x: scroll;
}

body::-webkit-scrollbar {
  display: none
}

.seat, .seatSelect {
  border: 1px solid #e8e8e8;
  width: 28px;
  box-sizing: border-box;
  height: 28px;
  margin: 2px;
  position: relative;
}

.btn {
  position: fixed;
  bottom: 54px;
  border-radius: 40px;
  line-height: 40px;
  height: 40px;
  margin: 1em 30px;
  width: calc(100% - 60px);
  background: #409eff;
  color: white;
  text-align: center;
}

.seatSelect::before {
  content: '';
  width: 14px;
  height: 2px;
  background-color: #d4d7d7;
  position: absolute;
  top: 12px;
  left: 6px;
}

.van-cell__title {
  display: flex;
  align-items: center;
}

.seatSelect::after {
  content: '';
  width: 2px;
  height: 14px;
  background-color: #d4d7d7;
  position: absolute;
  top: 6px;
  left: 12px;
}

.el-radio-button__orig-radio:checked + .el-radio-button__inner {
  background-color: #67c23a !important;
}

.blankMenu > * {
  padding: 0 0.5em;
  height: 35px;
  line-height: 35px;
  text-align: center;
}

.blankMenu > *:nth-child(2) {
  border-top: 1px white solid
}

.table {
  width: 18px;
  margin: 4px;
  height: 18px;
  border-radius: 2px;
  background: tan;
}
</style>
