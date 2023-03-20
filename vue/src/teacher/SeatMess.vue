<template>
    <el-card style="margin-top: calc(50% - 100px);overflow: scroll">
      <div slot="header" class="clearfix">
        <ToggleArea @changeArea="getSeatRows" ref="toggleArea" :area-rows="areaRows" v-if="areaRows"></ToggleArea>
        <HeadTip></HeadTip>
      </div>
      <Area ref="room" v-if="seatRows" :seat-rows="seatRows" @seatClick="seatClick">
        <div slot="seatMenu" class="blankMenu">
          <div>签到码：{{number}} </div>
        </div>
      </Area>
    </el-card>
</template>

<script>
import Area from "@/components/Area";
import request from "@/req";
import HeadTip from "@/components/HeadTip";
import ToggleArea from "@/components/ToggleArea";

export default {
  name: "SeatMess",
  components: {ToggleArea, HeadTip, Area},
  data() {
    return {
      areaRows: null,
      seatRows: null,
      number:'000000'
    }
  },
  methods: {
    seatClick(index) {
      //正在使用的座位无法操作
      if (this.seatRows[index].state !== 1) {
        this.seatRows[index].show = false
        return
      }
      request.post('/public/getSignedNumber',{
        sid:this.seatRows[index].sid
      }).then(res=>{
        this.number=res.number
      })
    },
    getSeatRows() {
      this.$nextTick(() => {
        request.post('/public/getAreaSeats', {
          area: this.$refs.toggleArea.getArea().aid
        }).then(res => {
          this.seatRows = res.rows
        })
      })

    }
  },
  created() {
    request.get('/public/getArea').then(res => {
      this.areaRows = res.rows
      this.getSeatRows()
    })
  }
}
</script>

<style scoped>


</style>
