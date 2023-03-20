<template>
  <div style="width: 100%;height: 50vh" id="timeChart"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "TimeListCharts",
  props: {
    list: null
  },
  mounted() {
    this.$nextTick(() => {
      let times = this.list.map(item => {
        return item.time
      })
      let values = this.list.map(item => {
        return item.sum
      })
      let chart = document.getElementById('timeChart')
      let myChart = echarts.init(chart, 'dark');
      let option;
      option = {
        title: {
          text: '时间段使用统计',
          subtext: '*统计各个时间段用户的使用情况'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        yAxis: [
          {
            type: 'value',
            // interval:0.2*item.full
          }
        ],
        xAxis: [
          {
            type: 'category',
            data: times,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: '20',
            data: values
          }
        ]
      };
      option && myChart.setOption(option);
    })


  }
}
</script>

<style scoped>
</style>
