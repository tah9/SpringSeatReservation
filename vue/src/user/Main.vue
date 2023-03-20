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
        {name: '论坛', url: '/student/forum'},
        {name: '座位', url: '/student/seat/reservation'}]
    }
  },
  methods: {
    getCurIndex() {
      for (let i = 0; i < this.tabs.length; i++) {
        return this.$route.path.indexOf('seat')===-1?0:1
      }
    }
  },
  created() {
    this.active = this.getCurIndex();
    if (this.$route.path.indexOf('/student/seat')) {
      this.$router.replace('/student/seat/reservation')
    }
  }
}
</script>

<style scoped>

</style>
