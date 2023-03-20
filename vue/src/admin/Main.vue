<template>
  <div>
    <div>
      <div style="height: 15vw;position: fixed;width: 15vw;background: #102438">
      </div>
      <LeftLayout :tabs="tabs" @changeTab="(cur)=>{active=cur}" style="background: #102438;color: white">
        <div v-for="(tab,index) in tabs"
             :style="index===active?'background:rgba(255,255,255,0.2);font-weight:bold;':''">{{ tab.name }}
        </div>
      </LeftLayout>
    </div>

    <router-view style="margin-left: 15vw"></router-view>
  </div>
</template>

<script>
import LeftLayout from "@/components/LeftLayout";

export default {
  name: "Main",
  components: {LeftLayout},
  data() {
    return {
      active: 0,
      tabs: [
        {name: '座位管理', url: '/admin/seat/number'},
        {name: '论坛管理', url: '/admin/forum'},
        {name: '公告管理', url: '/admin/announce'},
        {name: '学生管理', url: '/admin/student'},
        {name: '老师管理', url: '/admin/teacher'},
        {name: '统计分析', url: '/admin/statistics'},
      ]
    }
  }
}
</script>

<style scoped>

</style>
