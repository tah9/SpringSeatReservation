<template>
  <div>
    <navbar title="预约座位" :left="false">
      <Score slot="right"></Score>
    </navbar>
    <el-card style="overflow: scroll;">
      <div slot="header" class="clearfix">
        <ToggleArea @changeArea="getSeatRows" ref="toggleArea" :area-rows="areaRows" v-if="areaRows"></ToggleArea>
        <HeadTip></HeadTip>
      </div>
      <Area ref="room" v-if="seatRows" :seat-rows="seatRows" @seatClick="seatClick">
        <div slot="seatMenu" class="blankMenu">
          <div @click="clickPop">
            <i class="el-icon-s-flag"></i>预约
          </div>
        </div>
      </Area>
    </el-card>
    <el-card>
      <van-cell title="座位" :value="seatName"/>
      <TimeSlider ref="timeSlider"></TimeSlider>
    </el-card>
    <div class="btn" @click="submit">预约</div>
  </div>
</template>

<script>

import Area from "@/components/Area";
import Navbar from "@/components/navbar";
import request from "@/req";
import HeadTip from "@/components/HeadTip";
import ToggleArea from "@/components/ToggleArea";
import TimeSlider from "@/components/TimeSlider";
import {Toast} from "vant";
import Score from "@/components/Score";

export default {
  name: "Reservation",
  components: {Score, TimeSlider, ToggleArea, HeadTip, Navbar, Area},
  data() {
    return {
      areaRows: null,
      seatRows: null,
      seatCurIndex: 0,
      seatName: '请选择座位',
    }
  },
  methods: {
    submit() {
      if (this.seatName === '请选择座位') {
        Toast.fail('请选择座位')
        return
      }
      if (new Date().getHours()>=22) {
        Toast.fail('22点之后无法预约')
        return
      }
      this.$nextTick(() => {
        let body={
          startTime: this.$refs.timeSlider.getStartTime(),
          endTime: this.$refs.timeSlider.getEndTime(),
          uid: this.$getUser().uid,
          sid: this.seatRows[this.seatCurIndex].sid
        }
        if (body.startTime===body.endTime){
          Toast.fail('时间非法！')
          return
        }
        request.post('/user/addReservation',body ).then(res => {
          if (res.code === 200) {
            Toast.success('预约成功')
            this.togglePage()
          } else {
            Toast.fail('预约失败，当前已有预约')
          }
        })
      })
    },
    togglePage() {
      //当前有待操作的预约则跳转到操作页
      request.post('/user/getReservationByUid', {
        uid: this.$getUser().uid
      }).then(res => {
        console.log(res)
        for (let i = 0; i < res.rows.length; i++) {
          let item = res.rows[i]
          switch (item.state) {
            case 0:
            case 3:
              localStorage.setItem('reservation', JSON.stringify(item))
              if (this.$route.path !== '/student/seat/toSigned') {
                this.$router.replace('/student/seat/toSigned')
              }
              break
            case 1:
              localStorage.setItem('reservation', JSON.stringify(item))
              if (this.$route.path !== '/student/seat/beUse') {
                this.$router.replace('/student/seat/beUse')
              }
              break
          }
        }

      })
    },
    clickPop() {
      this.$nextTick(() => {
        let area = this.$refs.toggleArea.getArea();
        this.seatName = area.subName +
            this.seatRows[this.seatCurIndex].row + this.seatRows[this.seatCurIndex].column
      })
    },
    getSeatRows() {
      this.$nextTick(() => {
        if (this.$refs)
          request.post('/public/getAreaSeats', {
            area: this.$refs.toggleArea.getArea().aid
          }).then(res => {
            this.seatRows = res.rows
          })
      })
    },
    seatClick(index) {
      if (this.seatRows[index].type === 1 || this.seatRows[index].state === 2 || this.seatRows[index].state === 1) {
        this.seatRows[index].show = false;
        return
      }
      this.seatCurIndex = index
    },
    changeArea(index) {
      this.getSeatRows()
    },
  },
  created() {
    request.get('/public/getArea').then(res => {
      this.areaRows = res.rows
      this.getSeatRows()
    })
    this.togglePage()
  }
}
</script>

<style scoped>

</style>
