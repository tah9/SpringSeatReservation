<template>
  <div>
    <navbar title="学生基本信息"></navbar>
    <el-card class="box-card" style="margin-top: 25%">
      <div slot="header" class="clearfix">
        <span style="margin-left: 1em">{{item.username}}</span>
      </div>
      <van-cell title="学工号" :value="item.number"/>
      <van-cell title="信用分" :value="item.uScore"/>
    </el-card>
  </div>
</template>

<script>
import Navbar from "@/components/navbar";
export default {
  name: "StudentMess",
  components: {Navbar},
  props: {},
  data() {
    return {
      item:null
    }
  },
  methods: {},
  created() {
    this.item=JSON.parse(localStorage.getItem('student'))
    console.log(this.item)
  }
}
</script>

<style scoped>

</style>
