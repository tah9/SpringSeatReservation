<template>
  <div>
    <van-cell title="起止时间">
      <div style="display: flex;justify-content: end">
        <van-button size="mini" plain type="primary">{{ startText }}</van-button>
        <div>--</div>
        <van-button size="mini" plain type="info">{{ endText }}</van-button>
      </div>
    </van-cell>
    <div class="block" style="margin: 3px 0 1em 0;">
      <el-slider @change="updateTime" :disabled="disable"
                 v-model="value" :marks="marks"
                 range :min="min" :max="max" :format-tooltip="formatToolTip"
                 show-stops>
      </el-slider>
    </div>
  </div>
</template>

<script>
export default {
  name: "timeSlider",
  props: {
    disable: false,
    startTime: null,
    endTime: null
  },
  data() {
    return {
      value: [0, 1],
      showLabelSize: 5,
      min: null,
      max: 22 * 2 + 1,
      marks: {},
      startText: '',
      endText: ''
    }
  },
  methods: {
    updateTime() {
      this.$nextTick(() => {
        let date = new Date(this.getStartTime());
        this.startText = date.getHours() + ':' + (date.getMinutes() === 0 ? date.getMinutes() + '0' : date.getMinutes())
        let date2 = new Date(this.getEndTime());
        this.endText = date2.getHours() + ':' + (date2.getMinutes() === 0 ? date2.getMinutes() + '0' : date2.getMinutes())
      })
    },
    getStartTime() {
      let time = new Date(new Date().setHours(0, 0, 0, 0)).getTime();
      return time + this.value[0] * 30 * 60 * 1000
    },
    getEndTime() {
      let time = new Date(new Date().setHours(0, 0, 0, 0)).getTime();
      return time + this.value[1] * 30 * 60 * 1000
    },
    formatToolTip(v) {
      let pre = Math.trunc(v / 2)
      let suf = v % 2 === 0 ? ':00' : ':30'
      return pre + suf
    },
    getMarks() {
      //slider底部marks:{value:label,value:label}
      let labels = []
      let values = []
      for (let i = this.min; i <= this.max; i++) {
        values.push(i)
        labels.push((Math.trunc(i / 2)) + (i % 2 === 0 ? ':00' : ':30'))
      }

      let div = Math.ceil(labels.length / this.showLabelSize)
      for (let i = 0; i < this.showLabelSize; i++) {
        if (i * div < labels.length) {
          let value = values[i * div] + ''
          this.marks[value] = labels[i * div]
        }
      }
    }
  },
  created() {

    //只显示时间，禁止拖动状态下(必须提供开始、结束时间)
    if (this.disable) {
      this.min = 8 * 2
      this.max = 22 * 2 + 1
      let date1 = new Date(this.startTime);
      let hours1 = date1.getHours();
      let date2 = new Date(this.endTime);
      let hours2 = date2.getHours();
      this.value[0] = date1.getMinutes() > 0 ? hours1 * 2 + 1 : hours1 * 2
      this.value[1] = date2.getMinutes() > 0 ? hours2 * 2 + 1 : hours2 * 2
    }
    //拖动状态
    else {

      //min和max代表slider起止范围，以半小时为间隔
      //8：00就是8*2个半小时;22:30是22*2+1=45个
      let date = new Date();


      let hour = date.getHours()
      // console.log(hour)


      if (hour < 8) {
        //最早可以预约八点
        this.min = 8 * 2
      } else {
        //开始时间要比当前时间晚半小时内
        this.min = date.getMinutes() > 30 ? hour * 2 + 2 : hour * 2 + 1
      }

      this.value[0] = this.min
      this.value[1] = this.max

    }
    this.getMarks()
    this.updateTime()
  }
}
</script>

<style scoped>

</style>
