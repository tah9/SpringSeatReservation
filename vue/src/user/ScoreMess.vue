<template>
  <div>
    <navbar title="信用分"></navbar>
    <div v-for="item in rows">
      <el-card class="box-card" style="margin: 3px 0">
        <div slot="header" class="clearfix">
          <span style="margin-left: 1em">{{ item.username }}</span>
          <el-button style="float: right; padding: 3px 0;color: #757575" type="text">{{ getTime(item.startTime) }}
          </el-button>
        </div>
        <TimeSlider :disable="true" :start-time="item.startTime" :end-time="item.endTime"></TimeSlider>
        <van-cell title="状态">
          <div style="color: #e6a23c">{{ getState(item.state) }}</div>
        </van-cell>
        <van-cell title="详情">
          <van-tag type="danger" size="large" color="gray">已扣{{ item.score }}分</van-tag>
        </van-cell>
      </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/req";
import TimeSlider from "@/components/TimeSlider";
import {Toast} from "vant";
import Navbar from "@/components/navbar";

export default {
  name: "ScoreMess",
  components: {Navbar, TimeSlider},
  props: {},
  data() {
    return {
      rows: null
    }
  },
  methods: {
    getState(state) {
      switch (state) {
        case 2:
          return '预约未签到'
        case 4:
          return '暂离超时未回'
      }
    },
    getTime(d) {
      let date = new Date(d);
      return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + (date.getMinutes() === 0 ? "00" : "30")
    },
    update() {
      request.post('/user/getReservation',{
        uid:this.$getUser().uid
      }).then(res => {
        this.rows = res.rows
        console.log(this.rows);
      })
    }
  },
  created() {
    this.update()
  }
}
</script>

<style scoped>

</style>
