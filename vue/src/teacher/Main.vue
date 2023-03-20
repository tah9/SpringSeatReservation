<template>
  <div>
    <router-view style="margin-bottom: 50px;overflow: scroll"></router-view>
    <TabLayout>
      <div @click="()=>{active=index;if($route.path!==tab.url)$router.replace(tab.url)}"
           :style="index===active?'color:#409eff;font-weight:bold;':''" v-for="(tab,index) in tabs">{{ tab.name }}
      </div>
    </TabLayout>
  </div>
</template>

<script>
import TabLayout from "@/components/TabLayout";

export default {
  name: "Main",
  components: {TabLayout},
  props: {},
  data() {
    return {
      active: 0,
      tabs: [
        {name: '座位信息', url: '/Teacher/seat'},
        {name: '预约信息', url: '/Teacher/reservation'},
        {name: '信用分管理', url: '/Teacher/score'}]
    }
  },
  methods: {
    getCurIndex() {
      for (let i = 0; i < this.tabs.length; i++) {
        if (this.$route.path === this.tabs[i].url) {
          return i
        }
      }
    }
  },
  created() {
    this.active = this.getCurIndex()
  }
}
</script>

<style scoped>

</style>
