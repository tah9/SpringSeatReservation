<template>
  <div>
    <UserCounterListCharts v-if="userList" :list="userList"></UserCounterListCharts>
    <TimeListCharts v-if="timeList" :list="timeList"></TimeListCharts>
  </div>
</template>

<script>
import UserCounterListCharts from "@/components/UserCounterListCharts";
import request from "@/req";
import TimeListCharts from "@/components/TimeListCharts";

export default {
  name: "Statistics",
  components: {TimeListCharts, UserCounterListCharts},
  props: {},
  data() {
    return {
      userList: null,
      timeList:null
    }
  },
  methods: {},
  created() {
    request.get('/admin/getStatistics').then(res => {
      this.userList=res.userCounter
      this.timeList=res.timeList
    })
  }
}
</script>

<style scoped>

</style>
