<template>
  <div style="display: flex;transform: scale(0.8);margin-right: -6px" @click="$router.push('/ScoreMess')">
    <div>信用分:</div>
    <div style="margin-left: 3px;color: #409eff">{{score}}</div>
  </div>
</template>

<script>
import request from "@/req";

export default {
  name: "Score",
  props: {},
  data() {
    return {
      score:100
    }
  },
  methods: {},
  created() {
    request.post('/user/getScore',{
      uid:this.$getUser().uid
    }).then(res=>{
      this.score=res.score
    })
  }
}
</script>

<style scoped>

</style>
