<template>
  <div>
    <navbar title="签到打卡" :left="false">
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
      <van-cell title="签到码" style="margin-top: 20%">
        <van-field v-model="number" type="digit" placeholder="请询问管理员"/>
      </van-cell>
    </el-card>
    <div class="btn" @click="submit">{{ stateName }}签到</div>

  </div>
</template>

<script>
import Navbar from "@/components/navbar";
import TimeSlider from "@/components/TimeSlider";
import request from "@/req";
import {Toast} from "vant";
import Score from "@/components/Score";

export default {
  name: "ToSigned",
  components: {Score, TimeSlider, Navbar},
  props: {},
  data() {
    return {
      item: null,
      number: null,
      stateName: '预约'
    }
  },
  methods: {
    submit() {
      if (this.number === null) {
        Toast.fail('请完整输入')
        return
      }
      request.post('/user/toSigned', {
        number: this.number,
        rid: this.item.rid
      }).then(res => {
        if (res.code === 200) {
          if (res.verify) {
            Toast.success('签到成功')
            this.$router.replace('/student/seat/BeUse')
          } else {
            Toast.fail('验证码错误')
          }
        } else {
          Toast.fail('时间超时，签到失败')
        }
      })
    }
  },
  created() {
    this.item = JSON.parse(localStorage.getItem('reservation'));
    if (this.item.state === 3) {
      this.stateName = '暂离'
    }
    console.log(this.item)
  }
}
</script>

<style scoped>

</style>
