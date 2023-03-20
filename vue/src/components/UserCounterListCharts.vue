<template>
  <div style="width: 100%;height: 50vh" id="userChart"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "UserCounterListCharts",
  props: {
    list: null
  },
  mounted() {
    this.$nextTick(() => {
      let names = this.list.map(item => {
        return item.username + '(' + item.number + ')'
      })
      let values = this.list.map(item => {
        return item.counter
      })
      let chart = document.getElementById( 'userChart')
      let myChart = echarts.init(chart, 'dark');
      let option;
      option = {
        title: {
          text: '打卡次数排行榜',
          subtext: '*用户入馆签到次数总计'
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
        xAxis: [
          {
            type: 'value',
            // interval:0.2*item.full
          }
        ],
        yAxis: [
          {
            type: 'category',
            data: names,
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
