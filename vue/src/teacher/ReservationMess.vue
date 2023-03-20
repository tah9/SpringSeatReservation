<template>
  <div>
    <div v-for="item in rows" @click="clickItem(item)">
      <el-card class="box-card" style="margin: 3px 0">
        <div slot="header" class="clearfix">
          <span style="margin-left: 1em">{{ item.username }}</span>
          <el-button style="float: right; padding: 3px 0;color: #757575" type="text">{{ getTime(item.startTime) }}
          </el-button>
        </div>
        <van-cell title="座位号" :value="item.subName + item.row + item.column"/>
        <van-cell title="状态">
          <div style="color: #e6a23c">{{ getState(item.state) }}</div>
        </van-cell>
        <TimeSlider :disable="true" :start-time="item.startTime" :end-time="item.endTime"></TimeSlider>
      </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/req";
import TimeSlider from "@/components/TimeSlider";

export default {
  name: "ReservationMess",
  components: {TimeSlider},
  props: {},
  data() {
    return {
      rows: null
    }
  },
  methods: {
    clickItem(item) {
      localStorage.setItem("student", JSON.stringify(item))
      this.$router.push('/LookStudentMess')
    },
    getState(state) {
      switch (state) {
        case 0:
          return '待签到'
        case 1:
          return '使用中'
        case 2:
          return '预约未签到'
        case 3:
          return '暂离'
        case 4:
          return '暂离超时未回'
        case 5:
          return '使用完成'
      }
    },
    getTime(d) {
      let date = new Date(d);
      return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate()
    }
  },
  created() {
    request.get('/teacher/getReservation').then(res => {
      this.rows = res.rows
    })
  }
}
</script>

<style scoped>

</style>
