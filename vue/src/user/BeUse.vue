<template>
  <div>
    <navbar title="使用中" :left="false">
      <Score slot="right"></Score>
    </navbar>
    <el-divider></el-divider>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span style="margin-left: 1em">座位信息</span>
      </div>
      <van-cell title="座位号" :value="item.subName + item.row + item.column"/>
      <van-cell title="区域名" :value="item.areaName"/>
      <TimeSlider :disable="true" :start-time="item.startTime" :end-time="item.endTime"></TimeSlider>

    </el-card>
    <div class="btn" style="background: #67c23a" @click="submit">暂离</div>

  </div>
</template>

<script>
import Navbar from "@/components/navbar";
import TimeSlider from "@/components/TimeSlider";
import request from "@/req";
import {Toast} from "vant";
import Score from "@/components/Score";

export default {
  name: "BeUse",
  components: {Score, TimeSlider, Navbar},
  props: {},
  data() {
    return {
      item: null,
      number: null
    }
  },
  methods: {
    submit() {
      if (this.item===null){
        Toast.fail('请完整输入')
        return
      }
      request.post('/user/toLeave', {
        sid: this.item.sid,
        rid: this.item.rid
      }).then(res => {
        this.item.state = 3
        localStorage.setItem('reservation', JSON.stringify(this.item))
        Toast.success('操作成功，请回馆后及时签到')
        this.$router.replace('/student/seat/toSigned')
      })
    }
  },
  created() {
    this.item = JSON.parse(localStorage.getItem('reservation'));
    console.log(this.item)
  }
}
</script>

<style scoped>

</style>
